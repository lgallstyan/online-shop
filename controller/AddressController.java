package grid.onlineshop.sweetland.controller;

import grid.onlineshop.sweetland.dto.request.AddressDto;
import grid.onlineshop.sweetland.exceptions.addressexc.AddressNotFoundException;
import grid.onlineshop.sweetland.exceptions.userexc.UserNotFoundException;
import grid.onlineshop.sweetland.model.Address;
import grid.onlineshop.sweetland.model.User;
import grid.onlineshop.sweetland.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/address")
@AllArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<Address> addAnAddress(@RequestBody AddressDto addressDto) {
        return new ResponseEntity<>(addressService.addAnAddress(addressDto), HttpStatus.CREATED);
    }

    // 2. Updating Address
    @PutMapping
    public ResponseEntity<Address> updateAddress(@RequestBody Address address) throws AddressNotFoundException {
        return new ResponseEntity<>(addressService.updateAddress(address), HttpStatus.OK);
    }

    // 3. Removing Address by address
    @DeleteMapping
    public ResponseEntity<Address> removeAddress(@RequestBody Address address) throws AddressNotFoundException {
        return new ResponseEntity<>(addressService.removeAddress(address), HttpStatus.OK);
    }

    // 4. Viewing all addresses
    @GetMapping
    public ResponseEntity<List<Address>> viewAllAddresses() throws AddressNotFoundException {
        return new ResponseEntity<>(addressService.viewAllAddresses(), HttpStatus.ACCEPTED);
    }

    // 5. Viewing address by address Id
    @GetMapping("/{addressId}")
    public ResponseEntity<Address> viewAddressByAddressId(@PathVariable("addressId") Long addressId) throws AddressNotFoundException {
        return new ResponseEntity<>(addressService.viewAddressByAddressId(addressId), HttpStatus.ACCEPTED);
    }

    // 6. Viewing addresses by address line
    @GetMapping("/viewByBuildingName/{bName}")
    public ResponseEntity<List<Address>> viewAddressesByAddressLine(@PathVariable("aName") String aName) throws AddressNotFoundException {
        return new ResponseEntity<>(addressService.viewAddressesByAddressLine(aName), HttpStatus.ACCEPTED);
    }

    @GetMapping("/customerAddressesByPin/{addressLine}")
    public ResponseEntity<List<Address>> addressesOfCustomersByAddressLine(@PathVariable("addressLine") String addressLine) throws AddressNotFoundException {
        return new ResponseEntity<>(addressService.addressesOfCustomersByAddressLine(addressLine), HttpStatus.ACCEPTED);
    }

    // 9. Viewing addresses of orders by country and state
    @GetMapping("/orderAddressesByCountrySt/{postal}")
    public ResponseEntity<List<Address>> addressesOfOrdersByPostalCode(@PathVariable("postal") String postal) throws AddressNotFoundException {
        return new ResponseEntity<>(addressService.viewAddressesByPostalCode(postal), HttpStatus.ACCEPTED);
    }

    // 10. Viewing addresses of customers by country and state
    @GetMapping("/customerAddressesByCountrySt/{cnt}/{st}/{prv}")
    public ResponseEntity<List<Address>> addressesOfCustomersByCountryState(@PathVariable("cnt") String cnt,
                                                                            @PathVariable("st") String st, @PathVariable("prv") String prv) throws AddressNotFoundException {
        return new ResponseEntity<>(addressService.addressesOfOrdersByCountryCityProvince(cnt, st, prv), HttpStatus.ACCEPTED);
    }

    // 6. Viewing addresses with sorting by a property in ascending order
    @GetMapping("/sortAsc")
    public ResponseEntity<List<Address>> viewAddressesWithSortingAsc(@RequestParam("sortBy") String sortBy) throws AddressNotFoundException {
        return new ResponseEntity<>(addressService.viewAddressesWithSortingAsc(sortBy), HttpStatus.ACCEPTED);
    }

    // 7. Viewing addresses with sorting by a property in descending order
    @GetMapping("/sortDsc")
    public ResponseEntity<List<Address>> viewAddressesWithSortingDsc(@RequestParam("sortBy") String sortBy) throws AddressNotFoundException {
        return new ResponseEntity<>(addressService.viewAddressesWithSortingDsc(sortBy), HttpStatus.ACCEPTED);
    }

    // 8. Viewing addresses with pagination and sorting by a property in ascending
    // order
    @GetMapping("/paginateSortAsc")
    public ResponseEntity<List<Address>> viewAddressesWithPaginationAndSortingAsc(
            @RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize,
            @RequestParam("sortBy") String sortBy) throws AddressNotFoundException {
        return new ResponseEntity<>(addressService.viewAddressesWithPaginationAndSortingAsc(pageNo, pageSize, sortBy),
                HttpStatus.ACCEPTED);
    }

    // 9. Viewing addresses with pagination and sorting by a property in descending
    // order
    @GetMapping("/paginateSortDsc")
    public ResponseEntity<List<Address>> viewAddressesWithPaginationAndSortingDsc(
            @RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize,
            @RequestParam("sortBy") String sortBy) throws AddressNotFoundException {
        return new ResponseEntity<>(addressService.viewAddressesWithPaginationAndSortingDsc(pageNo, pageSize, sortBy),
                HttpStatus.ACCEPTED);
    }

    @PutMapping("/addAddressToCustomer/{addressId}/{customerId}")
    public ResponseEntity<User> addAnAddressToCustomer(@PathVariable("addressId") Long addressId, @PathVariable("customerId") Long customerId)
			throws UserNotFoundException, AddressNotFoundException {
        return new ResponseEntity<>(addressService.addAnAddressToCustomer(addressId, customerId), HttpStatus.OK);
    }

}
