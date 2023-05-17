package grid.onlineshop.sweetland.repository;

import grid.onlineshop.sweetland.model.Cart;
import grid.onlineshop.sweetland.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {

    Cart findByUser(User user);

    Cart findByIdAndAndUser(Long id,User user);

}
