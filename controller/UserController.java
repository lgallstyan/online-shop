//package grid.onlineshop.sweetland.controller;
//
//
//import grid.onlineshop.sweetland.dto.request.AddUserDto;
//import grid.onlineshop.sweetland.dto.request.UpdatePasswordDto;
//import grid.onlineshop.sweetland.dto.request.UpdateUserDto;
//import grid.onlineshop.sweetland.dto.response.GetUserDto;
//import grid.onlineshop.sweetland.exceptions.BadRequestException;
//import grid.onlineshop.sweetland.exceptions.userexc.UserAlreadyExistsException;
//import grid.onlineshop.sweetland.exceptions.userexc.UserNotFoundException;
//import grid.onlineshop.sweetland.model.User;
//import grid.onlineshop.sweetland.service.UserService;
//import lombok.AllArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@AllArgsConstructor
//@CrossOrigin
//public class UserController {
//
//    private final UserService userService;
//
//    @PostMapping(value = "/addCustomer",consumes = "application/json")
//    public ResponseEntity<User> addCustomer(@RequestBody AddUserDto customerRegisterDTO) throws UserAlreadyExistsException {
//        User add= userService.addCustomer(customerRegisterDTO);
//        return new ResponseEntity<User>(add, HttpStatus.ACCEPTED);
//    }
//
//    @GetMapping("/viewByCustomerId/{customerId}")
//    public ResponseEntity<User> viewCustomerById(@PathVariable("customerId") Long customerId) throws UserNotFoundException {
//        User view= userService.viewCustomerById(customerId);
//        return new ResponseEntity<User>(view, HttpStatus.ACCEPTED);
//    }
//
//    @PutMapping("/updateCustomer")
//    public ResponseEntity<GetUserDto> updateCustomer(@RequestBody UpdateUserDto customerUpdtDto) throws UserNotFoundException {
//        GetUserDto update= userService.updateCustomer(customerUpdtDto);
//        return new ResponseEntity<GetUserDto>(update, HttpStatus.ACCEPTED);
//    }
//
//    @DeleteMapping("/deleteCustomer/{customerId}")
//    public ResponseEntity<User> deleteCustomerById(@PathVariable("customerId") Long customerId) throws UserNotFoundException {
//        User delete= userService.deleteCustomerById(customerId);
//        return new ResponseEntity<User>(delete, HttpStatus.ACCEPTED);
//    }
//
//    @GetMapping("/viewAllCustomer")
//    public ResponseEntity<List<User>> viewAllCustomer() throws UserNotFoundException {
//        List<User> viewAll = userService.viewAllConsumer();
//        return new ResponseEntity<List<User>>(viewAll, HttpStatus.ACCEPTED);
//    }
//
//    @PutMapping("/updateCustomerPassword")
//    public ResponseEntity<GetUserDto> updatePassword(@RequestBody UpdatePasswordDto dto) throws UserNotFoundException, BadRequestException {
//        GetUserDto updatePass= userService.updatePassword(dto);
//        return new ResponseEntity<GetUserDto>(updatePass, HttpStatus.ACCEPTED);
//    }
//
//
//}
