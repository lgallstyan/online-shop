package grid.onlineshop.sweetland.exceptions.userexc;

public class UserNotFoundException extends Exception{

    public UserNotFoundException(){
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
