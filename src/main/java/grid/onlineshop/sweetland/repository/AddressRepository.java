package grid.onlineshop.sweetland.repository;

import grid.onlineshop.sweetland.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address,Long> {

    Optional<Address> findById(Long id);

    List<Address> findAll();

    List<Address> findByCityAndCountryAndProvince(String city,String country,String province);

    List<Address> findByAddressLine(String addressLine);

    List<Address> findByPostalCode(String postcalCode);

    List<Address> findByCity(String city);



}
