package grid.onlineshop.sweetland.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import grid.onlineshop.sweetland.config.JwtService;
import grid.onlineshop.sweetland.dto.request.AddUserDto;
import grid.onlineshop.sweetland.dto.request.LoginRequest;
import grid.onlineshop.sweetland.dto.request.UpdatePasswordDto;
import grid.onlineshop.sweetland.dto.request.UpdateUserDto;
import grid.onlineshop.sweetland.dto.response.AuthenticationResponse;
import grid.onlineshop.sweetland.dto.response.GetUserDto;
import grid.onlineshop.sweetland.exceptions.BadRequestException;
import grid.onlineshop.sweetland.exceptions.userexc.UserAlreadyExistsException;
import grid.onlineshop.sweetland.exceptions.userexc.UserNotFoundException;
import grid.onlineshop.sweetland.model.Cart;
import grid.onlineshop.sweetland.model.User;
import grid.onlineshop.sweetland.repository.CartRepository;
import grid.onlineshop.sweetland.repository.UserRepository;
import grid.onlineshop.sweetland.token.TokenRepository;
import grid.onlineshop.sweetland.token.TokenType;
import grid.onlineshop.sweetland.util.enums.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import grid.onlineshop.sweetland.token.Token;

import java.io.IOException;
import java.util.*;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final CartRepository cartRepository;



    public User getByEmail(String email) throws UserNotFoundException {

        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty())
            throw new UserNotFoundException("Customer Not Found!");

        return user.get();
    }



    public GetUserDto updateCustomer(UpdateUserDto updateUserDto) throws UserNotFoundException {

        User customer = getByEmail(updateUserDto.getEmail());

        customer.setFirstName(updateUserDto.getFirstName());
        customer.setLastName(updateUserDto.getLastName());
        customer.setPhone(updateUserDto.getPhone());

        userRepository.save(customer);

        return new GetUserDto(
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPhone());

    }

    public User deleteCustomerById(Long customerId) throws UserNotFoundException {
        Optional<User> optionalCustomer = Optional.ofNullable(userRepository.findCustomerById(customerId));
        if (optionalCustomer.isEmpty()) {
            throw new UserNotFoundException("Customer Not Found!");
        } else {
            User extUser = optionalCustomer.get();
            userRepository.deleteCustomerById(extUser.getId());
        }

        return optionalCustomer.get();

    }

    public User viewCustomerById(Long customerId) throws UserNotFoundException {
        Optional<User> optionalCustomer = Optional.ofNullable(userRepository.findCustomerById(customerId));

        if (optionalCustomer.isEmpty()) {
            throw new UserNotFoundException("Customer Not Found");
        }
        return optionalCustomer.get();

    }

    public List<User> viewAllConsumer() throws UserNotFoundException {
        List<User> userList = userRepository.findAll();
        if (userList.size() == 0) {
            throw new UserNotFoundException("Customers are Not Registered");
        }
        return userList;
    }

    public GetUserDto updatePassword(UpdatePasswordDto updatePasswordDto) throws UserNotFoundException, BadRequestException {

        if (!Objects.equals(updatePasswordDto.getNewPassword(), updatePasswordDto.getConfirmPassword()))
            throw new BadRequestException("New Password and Confirm Password Do Not Match!");

        Optional<User> user = userRepository.findByEmail(updatePasswordDto.getEmail());
        if (user.isEmpty())
            throw new UserNotFoundException("Customer Not Found!");

        user.get().setPassword(passwordEncoder.encode(updatePasswordDto.getNewPassword()));

        return new GetUserDto(
                user.get().getFirstName(),
                user.get().getLastName(),
                user.get().getEmail(),
                user.get().getPhone());

    }

    public String findNameByEmail(String email) {
        User user = userRepository.findByEmail(email).get();
        return user.getFirstName();
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    //  private Set<GrantedAuthority> getAuthorities(User user) {
//    Set<GrantedAuthority> authorities = new HashSet<>();
//    for (Role role : user.getRole()) {
//      authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
//    }
//    return authorities;
//  }
}
