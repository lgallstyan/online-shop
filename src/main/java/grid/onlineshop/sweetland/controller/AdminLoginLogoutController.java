package grid.onlineshop.sweetland.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminLoginLogoutController {

//	// Injecting dependencies
//    @Autowired
//    private Admin adminLoginLogoutService;
//
//
//    // Creating APIs
//
//    // Admin login
//    @PostMapping("/login")
//    public ResponseEntity<String> logInAdmin(@RequestBody AdminLogInDTO adminLoginDto) throws LoginLogoutException {
//        String result = adminLoginLogoutService.logIntoAccount(adminLoginDto);
//        return new ResponseEntity<String>(result, HttpStatus.OK );
//    }
//
//    // Admin logout
//    @PostMapping("/logout")
//    public String logoutAdmin(@RequestParam("adminId") Integer adminId, @RequestParam("adminKey") String adminKey) throws LoginLogoutException {
//        return adminLoginLogoutService.logOutFromAccount(adminId, adminKey);
//    }
}
