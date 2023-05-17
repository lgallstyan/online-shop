package grid.onlineshop.sweetland.repository;

import grid.onlineshop.sweetland.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin,Long> {


    Optional<Admin> findById(Long id);


    Admin findByEmailAndPassword(String email, String password);

    Admin findByEmail(String email);

    List<Admin> findAll();

}
