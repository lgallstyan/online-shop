package grid.onlineshop.sweetland.exceptions.productexc;

public class ProductAlreadyExistsException extends Exception{

    public ProductAlreadyExistsException() {
    }

    public ProductAlreadyExistsException(String message) {
        super(message);
    }
}
