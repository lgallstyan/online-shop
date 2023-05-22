package grid.onlineshop.sweetland.controller;

import grid.onlineshop.sweetland.dto.request.AddAdminDto;
import grid.onlineshop.sweetland.dto.request.UpdateAdminDto;
import grid.onlineshop.sweetland.dto.request.UpdatePasswordDto;
import grid.onlineshop.sweetland.dto.response.GetAdminDto;
import grid.onlineshop.sweetland.exceptions.BadRequestException;
import grid.onlineshop.sweetland.exceptions.adminexc.AdminAlreadyExistsException;
import grid.onlineshop.sweetland.exceptions.adminexc.AdminNotFoundException;
import grid.onlineshop.sweetland.model.Admin;
import grid.onlineshop.sweetland.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
public class AdminController {


    private final AdminService adminService;

    @PostMapping("/admin")
    public ResponseEntity<Admin> addAdmin(@RequestBody AddAdminDto adminRegDto) throws AdminAlreadyExistsException {

        Admin savedAdmin = adminService.addAdmin(adminRegDto);

        return new ResponseEntity<Admin>(savedAdmin, HttpStatus.CREATED);
    }

    @PutMapping("/adminUpdate")
    public ResponseEntity<Admin> updateAdmin(@RequestBody UpdateAdminDto adminUpdtDto,@RequestParam String email) throws AdminNotFoundException {

        Admin updateAdmin = adminService.updateAdmin(adminUpdtDto,email);

        return new ResponseEntity<Admin>(updateAdmin, HttpStatus.CREATED);
    }

    @GetMapping("/getAdminById/{adminId}")
    public ResponseEntity<Admin> getByAdminId(@PathVariable Long adminId) throws AdminNotFoundException {

        Admin getByAdminId = adminService.getSupplierById(adminId);

        return new ResponseEntity<Admin>(getByAdminId, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteAdminById/{adminId}")
    public ResponseEntity<Admin> deleteByAdminId(@PathVariable Long adminId) throws AdminNotFoundException {

        Admin deleteByAdminId = adminService.deleteAdminById(adminId);

        return new ResponseEntity<Admin>(deleteByAdminId, HttpStatus.CREATED);
    }

    @GetMapping("/getAdminList")
    public ResponseEntity<List<Admin>> getListAdmin() throws AdminNotFoundException {

        List<Admin> getListAdmin = adminService.allSuppliers();

        return new ResponseEntity<List<Admin>>(getListAdmin, HttpStatus.CREATED);
    }
    
    @PutMapping("/updateAdminPassword")
    public ResponseEntity<GetAdminDto> updatePassword(@RequestBody UpdatePasswordDto dto) throws AdminNotFoundException, BadRequestException {
        GetAdminDto updatePass= adminService.updatePassword(dto);
        return new ResponseEntity<GetAdminDto>(updatePass, HttpStatus.ACCEPTED);
    }

}
