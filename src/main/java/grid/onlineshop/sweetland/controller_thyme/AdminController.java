package grid.onlineshop.sweetland.controller_thyme;

import grid.onlineshop.sweetland.service.AdminService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/admin")
    public String getAdminDashboard(Model model) {
        // Add admin-specific model attributes here
        return "admin";
    }

    @GetMapping("/admin/delete-user")
    public String deleteCustomer(Model model){
        return "delete-user";
    }




//    @PostMapping("/admin")
//    public ResponseEntity<GetAdminDto> addAdmin(@RequestBody AddAdminDto adminRegDto) throws AdminAlreadyExistsException {
//
//        Admin admin = adminService.addAdmin(adminRegDto);
//        GetAdminDto adminDto = new GetAdminDto(admin.getName(), admin.getEmail(), admin.getPhone());
//
//        return new ResponseEntity<GetAdminDto>(adminDto, HttpStatus.CREATED);
//    }


}
