package grid.onlineshop.sweetland.repository;

import grid.onlineshop.sweetland.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    User findCustomerById(Long id);

    Optional<User> findByEmail(String email);

    List<User> findByRole(String Role);

    User deleteCustomerById(Long id);

    List<User> findAll();




}
