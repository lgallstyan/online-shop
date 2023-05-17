package grid.onlineshop.sweetland.service;

import grid.onlineshop.sweetland.dto.request.AddAdminDto;
import grid.onlineshop.sweetland.dto.request.UpdateAdminDto;
import grid.onlineshop.sweetland.dto.request.UpdatePasswordDto;
import grid.onlineshop.sweetland.dto.response.GetAdminDto;
import grid.onlineshop.sweetland.exceptions.BadRequestException;
import grid.onlineshop.sweetland.exceptions.adminexc.AdminAlreadyExistsException;
import grid.onlineshop.sweetland.exceptions.adminexc.AdminNotFoundException;
import grid.onlineshop.sweetland.model.Admin;
import grid.onlineshop.sweetland.repository.AdminRepository;
import grid.onlineshop.sweetland.repository.UserRepository;
import grid.onlineshop.sweetland.util.enums.Role;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public Admin addAdmin(AddAdminDto supplierDto) throws AdminAlreadyExistsException {
        Admin existAdmin = adminRepository.findByEmail(supplierDto.getEmail());
        if (existAdmin != null)
            throw new AdminAlreadyExistsException("Supplier Already Exists");

        Admin admin = new Admin();
        admin.setName(supplierDto.getName());
        admin.setEmail(supplierDto.getEmail());
        admin.setPassword(passwordEncoder.encode(supplierDto.getPassword()));
        admin.setPhone(supplierDto.getPhone());

//        Set<Role> roles = new HashSet<>();
//        roles.add(Role.ADMIN);
        admin.setRole(Role.ADMIN);

        adminRepository.save(admin);

        return admin;

    }

    public GetAdminDto updatePassword(UpdatePasswordDto updatePasswordDto) throws AdminNotFoundException, BadRequestException {

        if (!Objects.equals(updatePasswordDto.getNewPassword(), updatePasswordDto.getConfirmPassword()))
            throw new BadRequestException("New Password and Confirm Password Do Not Match!");

        Admin admin = adminRepository.findByEmailAndPassword(
                updatePasswordDto.getEmail(), updatePasswordDto.getOldPassword());
        if (admin == null)
            throw new AdminNotFoundException("Supplier Not Found!");

        admin.setPassword(passwordEncoder.encode(updatePasswordDto.getNewPassword()));

        return new GetAdminDto(
                admin.getName(),
                admin.getEmail(),
                admin.getPhone());

    }

    public Admin updateAdmin(UpdateAdminDto updateAdminDto,String email) throws AdminNotFoundException {
        Optional<Admin> opt = Optional.ofNullable(adminRepository.findByEmail(email));
        if (opt.isEmpty()) {
            throw new AdminNotFoundException("Supplier Not Found..!");
        }
         Admin admin = opt.get();
            admin.setName(updateAdminDto.getName());
            admin.setPhone(updateAdminDto.getPhone());

            return adminRepository.save(admin);
        }

    public Admin getSupplierById(Long supplierId) throws AdminNotFoundException {
        Optional<Admin> optAdmin = adminRepository.findById(supplierId);

        if (optAdmin.isPresent()) {
            return optAdmin.get();
        }
        throw new AdminNotFoundException("Supplier Not Found by Id");
    }

    public Admin deleteAdminById(Long supplierId) throws AdminNotFoundException {
        Optional<Admin> opt = adminRepository.findById(supplierId);
        if (opt.isPresent()) {
            Admin extAdmin = opt.get();
            adminRepository.delete(extAdmin);

            return extAdmin;
        } else {
            throw new AdminNotFoundException("Supplier Not Found by Id");
        }
    }

    public List<Admin> allSuppliers() throws AdminNotFoundException {
        List<Admin> adminList = adminRepository.findAll();
        if (adminList.size() == 0) {
            throw new AdminNotFoundException("Admin Not Register....");
        }
        return adminList;
    }


}
