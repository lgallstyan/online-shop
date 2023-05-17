package grid.onlineshop.sweetland.service;

import grid.onlineshop.sweetland.dto.request.AddressDto;
import grid.onlineshop.sweetland.exceptions.addressexc.AddressNotFoundException;
import grid.onlineshop.sweetland.exceptions.userexc.UserNotFoundException;
import grid.onlineshop.sweetland.model.Address;
import grid.onlineshop.sweetland.model.User;
import grid.onlineshop.sweetland.repository.AddressRepository;
import grid.onlineshop.sweetland.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    private final UserRepository userRepository;

    public Address addAnAddress(AddressDto addressDto) {

        Address address = new Address();

        address.setAddressLine(addressDto.getAddressLine());
        address.setCity(addressDto.getCity());
        address.setCountry(addressDto.getCountry());
        address.setProvince(addressDto.getProvince());
        address.setPostalCode(addressDto.getPostalCode());

        addressRepository.save(address);

        return address;
    }

    public Address updateAddress(Address updateAddressDto) throws AddressNotFoundException {

        Optional<Address> optAddress = addressRepository.findById(updateAddressDto.getId());

        if (optAddress.isPresent()) {
            return addressRepository.save(updateAddressDto);
        }
        throw new AddressNotFoundException("No address exists with the Id: " + updateAddressDto.getId());

    }

    public Address removeAddress(Address address) throws AddressNotFoundException {
        Optional<Address> optAddress = addressRepository.findById(address.getId());

        if (optAddress.isPresent()) {
            Address existingAddress = optAddress.get();
            addressRepository.delete(existingAddress);

            return existingAddress;
        }
        throw new AddressNotFoundException("No address exists with the Id: " + address.getId());
    }

    public List<Address> viewAllAddresses() throws AddressNotFoundException {
        List<Address> addressList = addressRepository.findAll();

        if (addressList.size() != 0) {
            return addressList;
        }
        throw new AddressNotFoundException("No address details found...!");
    }

    public Address viewAddressByAddressId(Long addressId) throws AddressNotFoundException {
        Optional<Address> optAddress = addressRepository.findById(addressId);

        if (optAddress.isPresent()) {

            return optAddress.get();
        }
        throw new AddressNotFoundException("No address exists with the Id: " + addressId);
    }

    public List<Address> viewAddressesByAddressLine(String addressLine) throws AddressNotFoundException {
        List<Address> addressList = addressRepository.findByAddressLine(addressLine);

        if (addressList.size() != 0) {
            return addressList;
        }
        throw new AddressNotFoundException("No address found with this address line.");
    }

    public List<Address> viewAddressesWithSortingAsc(String sortBy) throws AddressNotFoundException {
        List<Address> addressList = addressRepository.findAll(Sort.by(Sort.Direction.ASC, sortBy));
        if (addressList.size() != 0) {
            return addressList;
        }
        throw new AddressNotFoundException("No details found. Either the property is invalid or no records available.");
    }

    public List<Address> viewAddressesByPostalCode(String postalCode) throws AddressNotFoundException {
        List<Address> addressList = addressRepository.findByPostalCode(postalCode);

        if (addressList.size() != 0) {
            return addressList;
        }
        throw new AddressNotFoundException("No address found with this postal code.");
    }

//    public List<Address> addressesOfOrdersByPin(String pincode) throws AddressNotFoundException {
//        List<Address> allAddressesWithPin = addressRepository.findByPincode(pincode);
//
//        if (allAddressesWithPin.size() != 0) {
//            List<Address> onlyOrderAddresses = new ArrayList<>();
//            for (Address address : allAddressesWithPin) {
//                if (address.getOrderList().size() != 0) {
//                    onlyOrderAddresses.add(address);
//                }
//            }
//            return onlyOrderAddresses;
//        }
//        throw new AddressNotFoundException("No order address found with this pincode.");
//    }

    public List<Address> addressesOfCustomersByAddressLine(String addressLine) throws AddressNotFoundException {
        List<Address> allAddressesWithPin = addressRepository.findByAddressLine(addressLine);

        List<Address> onlyCustomerAddresses = getAddresses(allAddressesWithPin);
        if (onlyCustomerAddresses != null) return onlyCustomerAddresses;
        throw new AddressNotFoundException("No customer address found with this address line.");
    }

    public List<Address> addressesOfOrdersByCountryCityProvince(
            String country, String city,String province) throws AddressNotFoundException {
        List<Address> allAddressesWithCountryState = addressRepository
                .findByCityAndCountryAndProvince(country,city,province);

        if (allAddressesWithCountryState.size() != 0) {
            List<Address> onlyOrderAddresses = new ArrayList<>();
            for (Address address : allAddressesWithCountryState) {
                if (address.getOrderList().size() != 0) {
                    onlyOrderAddresses.add(address);
                }
            }
            return onlyOrderAddresses;
        }
        throw new AddressNotFoundException("No order address found with this country and state and province.");

    }

    public List<Address> addressesOfCustomersByCountryCityProvince(String country, String city,String province) throws AddressNotFoundException {
        List<Address> allCustomersWithCountryState = addressRepository
                .findByCityAndCountryAndProvince(country, city,province);

        List<Address> onlyCustomerAddresses = getAddresses(allCustomersWithCountryState);
        if (onlyCustomerAddresses != null) return onlyCustomerAddresses;
        throw new AddressNotFoundException("No customer address found with this country and state and provnice.");
    }

    private List<Address> getAddresses(List<Address> allCustomersWithCountryState) {
        if (allCustomersWithCountryState.size() != 0) {
            List<Address> onlyCustomerAddresses = new ArrayList<>();
            for (Address address : allCustomersWithCountryState) {
                if (address.getUserList().size() != 0) {
                    onlyCustomerAddresses.add(address);
                }
            }
            return onlyCustomerAddresses;
        }
        return null;
    }

    public List<Address> viewAddressesWithSortingDsc(String sortBy) throws AddressNotFoundException {
        List<Address> addressList = addressRepository.findAll(Sort.by(Sort.Direction.DESC, sortBy));
        if (addressList.size() != 0) {
            return addressList;
        }
        throw new AddressNotFoundException("No details found. Either the property is invalid or no records available.");
    }

    public List<Address> viewAddressesWithPaginationAndSortingAsc(Integer pageNo, Integer pageSize, String sortBy)
            throws AddressNotFoundException {
        Pageable pObj = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, sortBy));

        if (pObj.isPaged()) {
            Page<Address> addressPage = addressRepository.findAll(pObj);

            List<Address> allAddresses = addressPage.getContent();

            return allAddresses;
        }
        throw new AddressNotFoundException(
                "No result found for this request...! Please, try again with another set of instructions! :)");

    }

    public List<Address> viewAddressesWithPaginationAndSortingDsc(Integer pageNo, Integer pageSize, String sortBy)
            throws AddressNotFoundException {
        Pageable pObj = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, sortBy));

        if (pObj.isPaged()) {
            Page<Address> addressPage = addressRepository.findAll(pObj);

            List<Address> allAddresses = addressPage.getContent();

            return allAddresses;
        }
        throw new AddressNotFoundException(
                "No result found for this request...! Please, try again with another set of instructions! :)");
    }

    public User addAnAddressToCustomer(Long addressId, Long customerId)
            throws AddressNotFoundException, UserNotFoundException {
        Optional<Address> addOpt = addressRepository.findById(addressId);
        if(addOpt.isPresent()) {
            Address existingAddress = addOpt.get();
            Optional<User> customerOpt = userRepository.findById(customerId);
            if(customerOpt.isPresent()) {
                User existingUser = customerOpt.get();
                existingUser.getAddressList().add(existingAddress);
                existingAddress.getUserList().add(existingUser);
                return userRepository.save(existingUser);
            }
            throw new UserNotFoundException("Invalid customer id: "+customerId);
        }
        throw new AddressNotFoundException("Invalid address id.: "+addressId);
    }




}
