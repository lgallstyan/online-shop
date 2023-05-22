package grid.onlineshop.sweetland.controller;

import grid.onlineshop.sweetland.dto.request.AddressDto;
import grid.onlineshop.sweetland.exceptions.addressexc.AddressNotFoundException;
import grid.onlineshop.sweetland.exceptions.cartexc.CartException;
import grid.onlineshop.sweetland.exceptions.ordersexc.OrdersException;
import grid.onlineshop.sweetland.exceptions.userexc.UserNotFoundException;
import grid.onlineshop.sweetland.model.Orders;
import grid.onlineshop.sweetland.service.OrdersService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrdersController {

    private final OrdersService ordersService;

    @PostMapping("/addOrderWithExstngAdd/{customerId}/{addressId}")
    public ResponseEntity<Orders> addOrderWithExstngAddress(@PathVariable("customerId") Long customerId, @PathVariable("addressId") Long addressId)
            throws UserNotFoundException, AddressNotFoundException, OrdersException {
        return new ResponseEntity<>(ordersService.addOrderWithExistingAddress(customerId, addressId), HttpStatus.CREATED);
    }

    @PostMapping("/addOrderWithNewAdd/{customerId}")
    public ResponseEntity<Orders> addOrderWithNewAddress(@PathVariable("customerId") Long customerId, @RequestBody AddressDto dto)
            throws UserNotFoundException, OrdersException, CartException {
        return new ResponseEntity<>(ordersService.addOrderWithNewAddress(customerId, dto), HttpStatus.CREATED);
    }

    @GetMapping("/getByCustomerId/{cId}")
    public ResponseEntity<List<Orders>> getAllOrdersByCustomerId(@PathVariable("cId") Long customerId)
			throws UserNotFoundException, OrdersException {
        return new ResponseEntity<>(ordersService.getAllOrdersByCustomerId(customerId), HttpStatus.ACCEPTED);
    }

    @GetMapping("/getByOrderDate/{date}")
    public ResponseEntity<List<Orders>> getAllOrdersByDate(@PathVariable("date") String date)
			throws OrdersException {
        return new ResponseEntity<>(ordersService.getAllOrdersByDate(date), HttpStatus.ACCEPTED);
    }

    @GetMapping("/getByOrderId/{id}")
    public ResponseEntity<Orders> getOrderByOrderId(@PathVariable("id") Long id) throws OrdersException {
        return new ResponseEntity<>(ordersService.getOrderByOrderId(id), HttpStatus.ACCEPTED);
    }

    @GetMapping("/getByOrderCity/{cityName}")
    public ResponseEntity<List<Orders>> getAllOrderByCityName(@PathVariable("cityName") String city) throws OrdersException {
        return new ResponseEntity<>(ordersService.getAllOrderByCityName(city), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/removeOrderById/{orderId}")
    public ResponseEntity<Orders> removeOrder(@PathVariable("orderId") Long orderId) throws OrdersException {
        return new ResponseEntity<>(ordersService.removeOrder(orderId), HttpStatus.ACCEPTED);
    }

    @PutMapping("/updateOrderStat/{orderId}/{newStatus}")
    public ResponseEntity<Orders> updateOrderStatus(@PathVariable("orderId") Long orderId, @PathVariable("newStatus") String newStat)
			throws OrdersException {
        return new ResponseEntity<>(ordersService.updateOrderStatus(orderId, newStat), HttpStatus.ACCEPTED);
    }

    @GetMapping("/sortAsc")
    public ResponseEntity<List<Orders>> viewOrdersWithSortingAsc(@RequestParam("sortBy") String sortBy)
			throws OrdersException {
        return new ResponseEntity<>(ordersService.viewOrdersWithSortingDesc(sortBy), HttpStatus.ACCEPTED);
    }

    @GetMapping("/sortDesc")
    public ResponseEntity<List<Orders>> viewOrdersWithSortingDesc(@RequestParam("sortBy") String sortBy) throws OrdersException {
        return new ResponseEntity<>(ordersService.viewOrdersWithSortingDesc(sortBy), HttpStatus.ACCEPTED);
    }

    @GetMapping("/paginateSortAsc")
    public ResponseEntity<List<Orders>> viewOrdersWithPaginationAndSortingAsc(
            @RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize,
            @RequestParam("sortBy") String sortBy) throws OrdersException {
        return new ResponseEntity<>(ordersService.viewOrdersWithPaginationAndSortingDesc(pageNo, pageSize, sortBy),
                HttpStatus.ACCEPTED);
    }

    @GetMapping("/paginateSortDesc")
    public ResponseEntity<List<Orders>> viewOrdersWithPaginationAndSortingDesc(
            @RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize,
            @RequestParam("sortBy") String sortBy) throws OrdersException {
        return new ResponseEntity<>(ordersService.viewOrdersWithPaginationAndSortingDesc(pageNo, pageSize, sortBy),
                HttpStatus.ACCEPTED);
    }
}
