package grid.onlineshop.sweetland.repository;

import grid.onlineshop.sweetland.model.Category;
import grid.onlineshop.sweetland.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findAll();

    Optional<Product> findById(Long id);

    List<Product> findByCategory(Category category);

    Product findByName(String name);

    Product findByManufacturer(String manufacturer);

    Product findByManufacturerAndAndName(String manufacturer,String name);

}
