package grid.onlineshop.sweetland.controller_thyme;

import grid.onlineshop.sweetland.config.JwtService;
import grid.onlineshop.sweetland.service.AdminService;
import grid.onlineshop.sweetland.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class AdminController {

    private final AdminService adminService;

    private final JwtService jwtService;

    private final UserService userService;

    @GetMapping("/admin")
    public String getAdminDashboard(Model model,HttpServletRequest request) {

        String username = userService.findNameByEmail(jwtService.extractUsername(getCookie(request)));
        model.addAttribute("username",username);

        return "admin-dashboard";
    }

    @GetMapping("/admin/delete-user")
    public String deleteCustomer(Model model){
        return "delete-user";
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
