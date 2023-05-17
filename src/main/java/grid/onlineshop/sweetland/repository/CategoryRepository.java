package grid.onlineshop.sweetland.repository;

import grid.onlineshop.sweetland.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    Category findByName(String name);

    Optional<Category> findById(Long id);

    List<Category> findAll();

}
