package grid.onlineshop.sweetland.service;

import grid.onlineshop.sweetland.dto.request.AddressDto;
import grid.onlineshop.sweetland.exceptions.addressexc.AddressNotFoundException;
import grid.onlineshop.sweetland.exceptions.cartexc.CartException;
import grid.onlineshop.sweetland.exceptions.ordersexc.OrdersException;
import grid.onlineshop.sweetland.exceptions.userexc.UserNotFoundException;
import grid.onlineshop.sweetland.model.Address;
import grid.onlineshop.sweetland.model.CartItem;
import grid.onlineshop.sweetland.model.Orders;
import grid.onlineshop.sweetland.model.User;
import grid.onlineshop.sweetland.repository.AddressRepository;
import grid.onlineshop.sweetland.repository.CartItemRepository;
import grid.onlineshop.sweetland.repository.OrdersRepository;
import grid.onlineshop.sweetland.repository.UserRepository;
import grid.onlineshop.sweetland.util.enums.CartItemStatus;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;

    private final UserRepository userRepository;

    private final AddressRepository addressRepository;

    private final CartItemRepository cartItemRepository;

    private final CartService cartService;


    @PreAuthorize("hasRole('USER')")
    public Orders addOrderWithExistingAddress(Long customerId, Long addressId)
            throws AddressNotFoundException, OrdersException, UserNotFoundException {

        Optional<User> customerOpt = userRepository.findById(customerId);
        if (customerOpt.isPresent()) {
            User existingCustomer = customerOpt.get();
            List<CartItem> allProductsInCart = existingCustomer.getCart().getCartItemList();
            List<CartItem> unorderedCartProducts = new ArrayList<>();
            for (CartItem cp : allProductsInCart) {
                if (cp.getCmpStatus().toString().equals("UNORDERED")) {
                    unorderedCartProducts.add(cp);
                }
            }
            int unorderedCartSize = unorderedCartProducts.size();
            if (unorderedCartSize != 0) {
                Optional<Address> addOpt = addressRepository.findById(addressId);
                if (addOpt.isPresent()) {
//						Address existingAddress = addOpt.get();
                    List<Address> customerAddresses = existingCustomer.getAddressList();
                    Address addressForOrder = new Address();
                    for (Address add : customerAddresses) {
                        if (Objects.equals(add.getId(), addressId)) {
                            addressForOrder = add;
                        }
                    }
                    if (addressForOrder != null) {
                        Orders orders = new Orders();
                        orders.setOrderDate(LocalDate.now().atStartOfDay());
                        orders.setOrderStatus("PENDING");
                        orders.setCartProductList(unorderedCartProducts);

                        for (CartItem cp : unorderedCartProducts) {
                            cp.setCmpStatus(CartItemStatus.ORDERED);
                        }

                        orders.setAddress(addressForOrder);
                        if (addressForOrder.getOrderList() == null) {
                            List<Orders> orderListForAddress = new ArrayList<>();
                            orderListForAddress.add(orders);
                            addressForOrder.setOrderList(orderListForAddress);
                        } else {
                            addressForOrder.getOrderList().add(orders);
                        }

                        orders.setUser(existingCustomer);
                        if (existingCustomer.getOrderList() == null) {
                            List<Orders> orderListForCustomer = new ArrayList<>();
                            orderListForCustomer.add(orders);
                            existingCustomer.setOrderList(orderListForCustomer);
                        } else {
                            existingCustomer.getOrderList().add(orders);
                        }

                        User savedCustomer = userRepository.save(existingCustomer);
                        List<Orders> orderList = savedCustomer.getOrderList();
                        return orderList.get(orderList.size() - 1);

                    }
                    throw new AddressNotFoundException("This address is not registered with this customer. Please add this address to this customer.");
                }
                throw new AddressNotFoundException("Invalid address id: " + addressId);
            }
            throw new OrdersException("No unordered cart product found in cart.");
        }
        throw new UserNotFoundException("Invalid customer id: " + customerId);
    }


//    @PreAuthorize("hasRole('USER')")
    public Orders addOrderWithNewAddress(Long customerId, AddressDto dto)
            throws UserNotFoundException, OrdersException, CartException {
            Optional<User> customerOpt = userRepository.findById(customerId);
            if (customerOpt.isPresent()) {
                User existingCustomer = customerOpt.get();
                List<CartItem> allProductsInCart = existingCustomer.getCart().getCartItemList();
                List<CartItem> unorderedCartProducts = new ArrayList<>();
                for (CartItem cp : allProductsInCart) {
                    if (cp.getCmpStatus().toString().equals("UNORDERED")) {
                        unorderedCartProducts.add(cp);
                    }
                }
                int unorderedCartSize = unorderedCartProducts.size();
                if (unorderedCartSize != 0) {
                    Address address = new Address();
                    address.setAddressLine(dto.getAddressLine());
                    address.setCity(dto.getCity());
                    address.setCountry(dto.getCountry());
                    address.setPostalCode(dto.getPostalCode());
                    address.setProvince(dto.getProvince());

                    Orders orders = new Orders();
                    orders.setOrderDate(LocalDate.now().atStartOfDay());
                    orders.setOrderStatus("PENDING");
                    orders.setCartProductList(unorderedCartProducts);
                    orders.setTotalAmount(cartService.findTotalCartPriceByCustomerId(customerId));

                    for (CartItem cp : unorderedCartProducts) {
                        cp.setCmpStatus(CartItemStatus.ORDERED);
                    }

                    orders.setAddress(address);
                    if (address.getOrderList() == null) {
                        List<Orders> orderListForAddress = new ArrayList<>();
                        orderListForAddress.add(orders);
                        address.setOrderList(orderListForAddress);
                    } else {
                        address.getOrderList().add(orders);
                    }

                    orders.setUser(existingCustomer);
                    if (existingCustomer.getOrderList() == null) {
                        List<Orders> orderListForCustomer = new ArrayList<>();
                        orderListForCustomer.add(orders);
                        existingCustomer.setOrderList(orderListForCustomer);
                    } else {
                        existingCustomer.getOrderList().add(orders);
                    }

                    if (address.getUserList() == null) {
                        List<User> customerList = new ArrayList<>();
                        customerList.add(existingCustomer);
                        address.setUserList(customerList);
                    } else {
                        address.getUserList().add(existingCustomer);
                    }

                    if (existingCustomer.getAddressList() == null) {
                        List<Address> addressList = new ArrayList<>();
                        addressList.add(address);
                        existingCustomer.setAddressList(addressList);
                    } else {
                        existingCustomer.getAddressList().add(address);
                    }

                    User savedCustomer = userRepository.save(existingCustomer);
                    List<Orders> orderList = savedCustomer.getOrderList();
                    return orderList.get(orderList.size() - 1);
                }
                throw new OrdersException("No unordered cart product found in cart.");
            }
            throw new UserNotFoundException("No customer found with id: " + customerId);
        }



    @PreAuthorize("hasRole('USER')")
    public Orders updateOrder(Orders order) throws OrdersException {

		Orders existingOrder = ordersRepository.findById(order.getId()).get();

		if(existingOrder == null) {
			throw new OrdersException("Order does not exists...");
		}
		if(existingOrder.getOrderStatus().equalsIgnoreCase("open")) {
			existingOrder.setOrderStatus("cancelled");
		}
		else if(existingOrder.getOrderStatus().equalsIgnoreCase("placed")) {
			throw new OrdersException("Order is already placed...");
		}
		else if(existingOrder.getOrderStatus().equalsIgnoreCase("cancelled")) {
			throw new OrdersException("This order is cancelled...");
		}

		Orders updatedOrders = ordersRepository.save(existingOrder);

		return updatedOrders;

    }

    public List<Orders> getAllOrders(){
        return ordersRepository.findAll();
    }

    @PreAuthorize("hasRole('USER')")
    public Orders removeOrder(Long orderId) throws OrdersException {
            Optional<Orders> orderOpt = ordersRepository.findById(orderId);

            if (orderOpt.isEmpty()) {
                throw new OrdersException("Order not found...");
            } else {

                Orders orders = orderOpt.get();

                ordersRepository.delete(orders);

                return orders;

            }
        }


    public Orders getOrderByOrderId(Long orderId) throws OrdersException {
        Optional<Orders> orderOpt = ordersRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            return orderOpt.get();
        }
        throw new OrdersException("No order found with this id: " + orderId);
    }

    public List<Orders> getAllOrdersByDate(String date) throws OrdersException {
        LocalDate localDate = LocalDate.parse(date);
        List<Orders> orderList = ordersRepository.findByOrderDate(localDate);
        if (orderList.size() != 0) {
            return orderList;
        }
        throw new OrdersException("No order found on this date: " + date);
    }

    public List<Orders> getAllOrderByCityName(String city) throws OrdersException {

        List<Address> addressList = addressRepository.findByCity(city);
        if (addressList.size() == 0) {
            throw new OrdersException("No address found with this city name: " + city);
        }

        List<Orders> orderList = new ArrayList<>();
        for (Address address : addressList) {
            List<Orders> subOrderList = address.getOrderList();
            if (subOrderList.size() != 0) {
                for (Orders order : subOrderList) {
                    orderList.add(order);
                }
            }
        }
        if (orderList.size() != 0) {
            return orderList;
        }
        throw new OrdersException("No order found for this city: " + city);
    }

    public List<Orders> getAllOrdersByCustomerId(Long customerId) throws OrdersException,UserNotFoundException {
        Optional<User> customerOpt = userRepository.findById(customerId);
        if (customerOpt.isPresent()) {
            List<Orders> orderList = customerOpt.get().getOrderList();
            if (orderList.size() != 0) {
                return orderList;
            }
            throw new OrdersException("No order found for customer with id: " + customerId);
        }
        throw new UserNotFoundException("No customer found with id: " + customerId);
    }


    @PreAuthorize("hasRole('ADMIN')")
    public Orders updateOrderStatus(Long orderId, String newStatus)
            throws OrdersException {
            Optional<Orders> orderOpt = ordersRepository.findById(orderId);
            if (orderOpt.isPresent()) {
                Orders existingOrder = orderOpt.get();
                existingOrder.setOrderStatus(newStatus);
                List<CartItem> linkedCartProducts = existingOrder.getCartProductList();
                while (linkedCartProducts.size() > 0) {
                    Optional<CartItem> cpOpt = cartItemRepository.findById(linkedCartProducts.get(0).getId());
                    CartItem existingCp = cpOpt.get();
                    linkedCartProducts.remove(0);
                    cartItemRepository.delete(existingCp);
                }
                return existingOrder;
            }
            throw new OrdersException("Invlid order id..!");
        }




    public List<Orders> viewOrdersWithSortingDesc(String sortBy) throws OrdersException {
        List<Orders> orderList = ordersRepository.findAll(Sort.by(Sort.Direction.DESC, sortBy));
        if (orderList.size() != 0) {
            return orderList;
        }
        throw new OrdersException("No details found. Either the property is invalid or no records available.");
    }


    public List<Orders> viewOrdersWithPaginationAndSortingDesc(Integer pageNo, Integer pageSize, String sortBy)
            throws OrdersException {
        Pageable pObj = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, sortBy));

        if (pObj.isPaged()) {
            Page<Orders> ordersPage = ordersRepository.findAll(pObj);

            return ordersPage.getContent();
        }
        throw new OrdersException(
                "No result found for this request...! Please, try again with another set of instructions! :)");

    }

}
