package grid.onlineshop.sweetland.service;

import grid.onlineshop.sweetland.dto.request.AddProductToCartDto;
import grid.onlineshop.sweetland.exceptions.cartexc.CartException;
import grid.onlineshop.sweetland.exceptions.productexc.ProductNotFoundException;
import grid.onlineshop.sweetland.exceptions.userexc.UserNotFoundException;
import grid.onlineshop.sweetland.model.Cart;
import grid.onlineshop.sweetland.model.CartItem;
import grid.onlineshop.sweetland.model.Product;
import grid.onlineshop.sweetland.model.User;
import grid.onlineshop.sweetland.repository.CartItemRepository;
import grid.onlineshop.sweetland.repository.CartRepository;
import grid.onlineshop.sweetland.repository.ProductRepository;
import grid.onlineshop.sweetland.repository.UserRepository;
import grid.onlineshop.sweetland.util.enums.CartItemStatus;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;


//    @PreAuthorize("hasRole('USER')")
    public CartItem addProductToCart(AddProductToCartDto dto) throws CartException, ProductNotFoundException {
        Optional<Cart> cartOpt = cartRepository.findById(dto.getCartId());
        if (cartOpt.isEmpty()) throw new CartException("No such cart exist");

        Optional<Product> prodOpt = productRepository.findById(dto.getProductId());
        if (prodOpt.isEmpty()) throw new ProductNotFoundException("Product Does not exist");

        Cart existingCart = cartOpt.get();
        System.out.println(existingCart.getId() + "addproducti mej cart id");

        CartItem cartProduct = new CartItem();
        cartProduct.setProduct(prodOpt.get());
        cartProduct.setQuantity(dto.getQuantity());
        cartProduct.setCart(existingCart);
        cartProduct.setCmpStatus(CartItemStatus.UNORDERED);

        System.out.println("\n" + cartProduct.getProduct().getName() + " product has been set!\n");

        cartItemRepository.save(cartProduct);
        System.out.println("\n" +cartProduct.getProduct().getName() + " - successfully saved!\n");

        var isAdded = existingCart.getCartItemList().add(cartProduct);

        System.out.println("\n" + isAdded + "\n");

//        System.out.println("\n" + existingCart.getProductList().get(2).getProduct().getName()
//                + " the product list from the existing!\n");

        Cart savedCart = cartRepository.save(existingCart);

//        System.out.println("\nExisting card is being saved - "
//                + savedCart.getProductList().get(1).getProduct().getName() + "\n");

        List<CartItem> updatedCpList = savedCart.getCartItemList();

        return updatedCpList.get(updatedCpList.size() - 1);
    }


//    @PreAuthorize("hasRole('USER')")
    public CartItem removeProductFromCart(Long cartItemId) throws CartException {
        Optional<CartItem> cpOpt = cartItemRepository.findById(cartItemId);
        if (cpOpt.isEmpty()) {
            throw new CartException("Invalid cart product id.");
        }
        CartItem existingCp = cpOpt.get();
        if (existingCp.getCmpStatus().toString().equals(CartItemStatus.ORDERED.toString())) {
            throw new CartException("This is an ordered cart product. This will be removed once your the order is delivered or canceled.");
        }
        cartItemRepository.delete(existingCp);
        return existingCp;
    }

    @PreAuthorize("hasRole('USER')")
    public CartItem updateProductQuantityInCart(Long cartProductId, Integer newQuantity) throws CartException {
        Optional<CartItem> cpOpt = cartItemRepository.findById(cartProductId);
        if (cpOpt.isPresent()) {
            CartItem existingCartProd = cpOpt.get();
            existingCartProd.setQuantity(newQuantity);
            return cartItemRepository.save(existingCartProd);
        }
        throw new CartException("No cart product found with this id: " + cartProductId);
    }


//    @PreAuthorize("hasRole('USER')")
    public List<CartItem> removeAllProductsInCart(Long customerId) throws CartException, UserNotFoundException {
        Optional<User> customerOpt = userRepository.findById(customerId);
        if (customerOpt.isPresent()) {
            User existingCustomer = customerOpt.get();
            List<CartItem> cartProducts = existingCustomer.getCart().getCartItemList();
            List<CartItem> deletedCartProducts = new ArrayList<>();
            if (cartProducts.size() != 0) {
                while (cartProducts.size() > 0) {
                    Optional<CartItem> cpOpt = cartItemRepository
                            .findById(cartProducts.get(0).getId());
                    CartItem existingCp = cpOpt.get();
                    deletedCartProducts.add(existingCp);
                    cartProducts.remove(0);
                    cartItemRepository.delete(existingCp);
                }
                return deletedCartProducts;
            }
            throw new CartException("No product present in the cart of customer with id: " + customerId);
        }
        throw new UserNotFoundException("Invalid customer id: " + customerId);
    }


    public List<CartItem> viewAllProductsInCart(Long customerId) throws CartException, UserNotFoundException {
        Optional<User> customerOpt = userRepository.findById(customerId);
        if (customerOpt.isPresent()) {
            List<CartItem> cartProducts = customerOpt.get().getCart().getCartItemList();
            if (cartProducts.size() != 0) {
                return cartProducts;
            }
//            throw new CartException("No product present in the cart of customer with id: " + customerId);
            return null;
        }
        throw new UserNotFoundException("Invalid customer id: " + customerId);
    }

    public Integer findTotalCartPriceByCustomerId(Long customerId) throws CartException {
        Optional<User> customerOpt = userRepository.findById(customerId);

        if (customerOpt.isPresent()) {

            User existingCustomer = customerOpt.get();
            List<CartItem> customerCartProducts = existingCustomer.getCart().getCartItemList();
            int totalPrice = 0;
            if (customerCartProducts.size() != 0) {
                for (CartItem cp : customerCartProducts) {
                    totalPrice = totalPrice + (cp.getQuantity() * cp.getProduct().getPrice());
                }
            }
            return totalPrice;
        }
        throw new CartException("Invalid inputs");
    }

    public Cart findByUsername(String username) throws CartException {
        Optional<User> optionalUseruser = userRepository.findByEmail(username);
        if (optionalUseruser.isPresent()){
            User user = optionalUseruser.get();
            return cartRepository.findByUser(user);
        }
        throw new CartException("User Not Found or Cart Does Not Exist");
    }


}