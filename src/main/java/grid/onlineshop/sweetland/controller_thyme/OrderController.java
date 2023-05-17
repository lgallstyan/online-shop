package grid.onlineshop.sweetland.controller_thyme;


import grid.onlineshop.sweetland.config.JwtService;
import grid.onlineshop.sweetland.dto.request.AddressDto;
import grid.onlineshop.sweetland.exceptions.cartexc.CartException;
import grid.onlineshop.sweetland.exceptions.ordersexc.OrdersException;
import grid.onlineshop.sweetland.exceptions.userexc.UserNotFoundException;
import grid.onlineshop.sweetland.model.Cart;
import grid.onlineshop.sweetland.model.Orders;
import grid.onlineshop.sweetland.model.User;
import grid.onlineshop.sweetland.service.CartService;
import grid.onlineshop.sweetland.service.OrdersService;
import grid.onlineshop.sweetland.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class OrderController {

    private final JwtService jwtService;

    private final OrdersService ordersService;

    private final UserService userService;

    private final CartService cartService;

    @GetMapping("/checkout")
    public String checkout(Model model){
        AddressDto addressDto = new AddressDto();

        model.addAttribute("addressDto",addressDto);
        return "card";
    }

    @PostMapping("/order")
    public String order(Model model, HttpServletRequest request, @ModelAttribute AddressDto addressDto) throws UserNotFoundException, OrdersException, CartException {

        String jwtToken = getCookie(request);
        String username = jwtService.extractUsername(jwtToken);
        User user = userService.getByEmail(username);
        Cart cart = cartService.findByUsername(username);
        Integer totalAmount = cartService.findTotalCartPriceByCustomerId(user.getId());

        System.out.println(totalAmount);

        Orders order = new Orders();
        order.setTotalAmount(totalAmount);
        ordersService.addOrderWithNewAddress(user.getId(), addressDto);

        cartService.removeAllProductsInCart(user.getId());


        return "success";

    }



    private String getCookie(HttpServletRequest request) {
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

}
