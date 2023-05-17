package grid.onlineshop.sweetland.controller_thyme;

import grid.onlineshop.sweetland.config.JwtService;
import grid.onlineshop.sweetland.dto.request.AddProductToCartDto;
import grid.onlineshop.sweetland.exceptions.cartexc.CartException;
import grid.onlineshop.sweetland.exceptions.userexc.UserNotFoundException;
import grid.onlineshop.sweetland.model.Cart;
import grid.onlineshop.sweetland.model.CartItem;
import grid.onlineshop.sweetland.model.User;
import grid.onlineshop.sweetland.repository.CartRepository;
import grid.onlineshop.sweetland.service.CartService;
import grid.onlineshop.sweetland.service.ProductService;
import grid.onlineshop.sweetland.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@AllArgsConstructor
public class CartController {

    private final CartService cartService;

    private final ProductService productService;

    private final UserService userService;

    private JwtService jwtService;

    private final CartRepository cartRepository;

    private static final Logger logger = Logger.getLogger(CartController.class.getName());


    @PostMapping("/add-cart")
    public String addProductToCart(
            @ModelAttribute AddProductToCartDto addProductToCartDto,
            HttpServletRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String jwtToken = getJwtCookie(request);


        String username = jwtService.extractUsername(jwtToken);
        try {

            Cart cart = cartService.findByUsername(username);
            addProductToCartDto.setCartId(cart.getId());
            System.out.println(addProductToCartDto.getProductId());

            CartItem cartItem = cartService.addProductToCart(addProductToCartDto);

            cart.getCartItemList().add(cartItem);

            cartRepository.save(cart);

            return "redirect:/cart";
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Exception caught", ex);
        }
        return "redirect:/view-product";
    }

    @GetMapping("/cart")
    public String showCart(Model model, HttpServletRequest request) throws UserNotFoundException, CartException {

        String jwtToken = getJwtCookie(request);
        String username = jwtService.extractUsername(jwtToken);

        User user = userService.getByEmail(username);
        Cart cart = cartService.findByUsername(username);

        if (cart == null) {
            model.addAttribute("error", "cart not available");
            return "redirect:/";
        }


        List<CartItem> cartItems = cartService.viewAllProductsInCart(user.getId());
        int totalPrice = 0;

        if (cartItems != null) {
            for (CartItem cartItem : cartItems) {
                totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
            }
        }

        // Pass the cart items and total price to the Thymeleaf template
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);

        return "cart";
    }

    @PostMapping("/cart/remove/{productId}")
    public String removeCartItems(@PathVariable Long productId, Model model, HttpServletRequest request) throws UserNotFoundException, CartException {
        String jwtToken = getJwtCookie(request);
        String username = jwtService.extractUsername(jwtToken);

        User user = userService.getByEmail(username);
        Cart cart = cartService.findByUsername(username);

        if (cart == null) {
            model.addAttribute("error", "cart not available");
            return "redirect:/";
        }

        CartItem toDelete = cartService.removeProductFromCart(productId);
        return "cart";

    }


    private String getJwtCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String jwtToken = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JWT")) {
                    jwtToken = cookie.getValue();
                    break;
                }
            }
        }
        return jwtToken;
    }

    public String getProductCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        System.out.println(cookies.length);
        String productToken = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("prId")) {
                    productToken = cookie.getValue();
                    break;
                }
            }
        }
        return productToken;
    }


}
