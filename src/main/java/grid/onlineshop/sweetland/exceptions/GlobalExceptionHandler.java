package grid.onlineshop.sweetland.exceptions;

import grid.onlineshop.sweetland.exceptions.addressexc.AddressNotFoundException;
import grid.onlineshop.sweetland.exceptions.adminexc.AdminAlreadyExistsException;
import grid.onlineshop.sweetland.exceptions.cartexc.CartException;
import grid.onlineshop.sweetland.exceptions.categoryexc.CategoryAlreadyExistsException;
import grid.onlineshop.sweetland.exceptions.categoryexc.CategoryNotFoundException;
import grid.onlineshop.sweetland.exceptions.ordersexc.OrdersException;
import grid.onlineshop.sweetland.exceptions.productexc.ProductAlreadyExistsException;
import grid.onlineshop.sweetland.exceptions.productexc.ProductNotFoundException;
import grid.onlineshop.sweetland.exceptions.userexc.UserAlreadyExistsException;
import grid.onlineshop.sweetland.exceptions.userexc.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> anyExceptionHandler(Exception e, WebRequest w){
        ErrorDetails err=new ErrorDetails(LocalDateTime.now(),e.getMessage(),w.getDescription(false));
        return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorDetails> noHandlerFoundExceptionHandler(NoHandlerFoundException e,WebRequest w){
        ErrorDetails err=new ErrorDetails(LocalDateTime.now(),e.getMessage(),w.getDescription(false));
        return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){
        ErrorDetails err=new ErrorDetails(LocalDateTime.now(),"Validation Error..!",e.getBindingResult().getFieldError().getDefaultMessage());
        return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<ErrorDetails> addressExceptionHandler(AddressNotFoundException e,WebRequest w) {
        ErrorDetails err = new ErrorDetails(LocalDateTime.now(), e.getMessage(), w.getDescription(false));
        return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CartException.class)
    public ResponseEntity<ErrorDetails> cartExceptionHandler(CartException e,WebRequest w){
        ErrorDetails err=new ErrorDetails(LocalDateTime.now(),e.getMessage(),w.getDescription(false));
        return new ResponseEntity<ErrorDetails>(err,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorDetails> categoryExceptionHandler(CategoryNotFoundException e,WebRequest w){
        ErrorDetails err=new ErrorDetails(LocalDateTime.now(),e.getMessage(),w.getDescription(false));
        return new ResponseEntity<ErrorDetails>(err,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<ErrorDetails> categoryExceptionHandler(CategoryAlreadyExistsException e,WebRequest w){
        ErrorDetails err=new ErrorDetails(LocalDateTime.now(),e.getMessage(),w.getDescription(false));
        return new ResponseEntity<ErrorDetails>(err,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDetails> customerExceptionHandler(UserNotFoundException e,WebRequest w){
        ErrorDetails err=new ErrorDetails(LocalDateTime.now(),e.getMessage(),w.getDescription(false));
        return new ResponseEntity<ErrorDetails>(err,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorDetails> customerExceptionHandler(UserAlreadyExistsException e,WebRequest w){
        ErrorDetails err=new ErrorDetails(LocalDateTime.now(),e.getMessage(),w.getDescription(false));
        return new ResponseEntity<ErrorDetails>(err,HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(OrdersException.class)
    public ResponseEntity<ErrorDetails> ordersExceptionHandler(OrdersException e,WebRequest w){
        ErrorDetails err=new ErrorDetails(LocalDateTime.now(),e.getMessage(),w.getDescription(false));
        return new ResponseEntity<ErrorDetails>(err,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<ErrorDetails> productExceptionHandler(ProductAlreadyExistsException e,WebRequest w){
        ErrorDetails err=new ErrorDetails(LocalDateTime.now(),e.getMessage(),w.getDescription(false));
        return new ResponseEntity<ErrorDetails>(err,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDetails> productExceptionHandler(ProductNotFoundException e,WebRequest w){
        ErrorDetails err=new ErrorDetails(LocalDateTime.now(),e.getMessage(),w.getDescription(false));
        return new ResponseEntity<ErrorDetails>(err,HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(CartIy.class)
//    public ResponseEntity<ErrorDetails> cartProductExceptionHandler(CartProductException e,WebRequest w){
//        ErrorDetails err=new ErrorDetails(LocalDateTime.now(),e.getMessage(),w.getDescription(false));
//        return new ResponseEntity<ErrorDetails>(err,HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(AdminAlreadyExistsException.class)
    public ResponseEntity<ErrorDetails> adminExceptionHandler(AdminAlreadyExistsException e, WebRequest w){
        ErrorDetails err=new ErrorDetails(LocalDateTime.now(),e.getMessage(),w.getDescription(false));
        return new ResponseEntity<ErrorDetails>(err,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoginLogoutException.class)
    public ResponseEntity<ErrorDetails> loginLogoutException(LoginLogoutException e,WebRequest w){
        ErrorDetails err=new ErrorDetails(LocalDateTime.now(),e.getMessage(),w.getDescription(false));
        return new ResponseEntity<ErrorDetails>(err,HttpStatus.BAD_REQUEST);
    }




}
