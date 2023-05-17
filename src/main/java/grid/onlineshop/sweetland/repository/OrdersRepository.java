package grid.onlineshop.sweetland.repository;

import grid.onlineshop.sweetland.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders,Long> {

    List<Orders> findByOrderDate(LocalDate date);

}
