package grid.onlineshop.sweetland.controller;

import java.util.List;

import grid.onlineshop.sweetland.dto.request.AddProductToCartDto;
import grid.onlineshop.sweetland.exceptions.cartexc.CartException;
import grid.onlineshop.sweetland.exceptions.productexc.ProductNotFoundException;
import grid.onlineshop.sweetland.exceptions.userexc.UserNotFoundException;
import grid.onlineshop.sweetland.model.CartItem;
import grid.onlineshop.sweetland.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/cart")
@AllArgsConstructor
@CrossOrigin
public class CartController {

	private final CartService cartService;
	
	@PostMapping("/addProductToCart")
	public ResponseEntity<CartItem> addProductToCart(@RequestBody AddProductToCartDto dto)
			throws CartException, ProductNotFoundException {
		CartItem cartP = cartService.addProductToCart(dto);
		return new ResponseEntity<>(cartP, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/removeCartProduct/{cpId}")
	public ResponseEntity<CartItem> removeProductFromCart(@PathVariable("cpId") Long cartProductId) throws CartException {
		CartItem cp = cartService.removeProductFromCart(cartProductId);
		return new ResponseEntity<>(cp,HttpStatus.OK);
	}
	
	@DeleteMapping("/removeAllProductsInCart/{customerId}")
	public ResponseEntity<List<CartItem>> removeAllProductsInCart(@PathVariable("customerId") Long customerId) throws UserNotFoundException, CartException {
		List<CartItem> c = cartService.removeAllProductsInCart(customerId);
		return new ResponseEntity<>(c,HttpStatus.OK);
	}
	
	@PutMapping("/updateQnt/{cpId}/{qnt}")
	public ResponseEntity<CartItem> updateProductQuantityInCart(@PathVariable("cpId") Long cartProductId, @PathVariable("qnt") Integer newQuantity) throws CartException {
		CartItem cp = cartService.updateProductQuantityInCart(cartProductId, newQuantity);
		return new ResponseEntity<CartItem>(cp,HttpStatus.OK);
	}
	
	@GetMapping("/getAllProdsInCart/{customerId}")
	public ResponseEntity<List<CartItem>> viewAllProductsInCart(@PathVariable("customerId") Long customerId) throws UserNotFoundException, CartException {
		List<CartItem> cpList = cartService.viewAllProductsInCart(customerId);
		return new ResponseEntity<List<CartItem>>(cpList,HttpStatus.OK);
	}
	
	@GetMapping("/getTotCartPrcByCustomerId/{customerId}")
	public ResponseEntity<Integer> findTotalCartPriceByCustomerId(@PathVariable("customerId") Long customerId) throws CartException {
		Integer totPrice = cartService.findTotalCartPriceByCustomerId(customerId);
		return new ResponseEntity<>(totPrice,HttpStatus.OK);
	}
}
