package DatabaseFolder;

//Exception for if somethig is called and it ends up not existing
public class DoesNotExistException extends Exception {
    
    public DoesNotExistException(String message) {
        super(message);
    }
}
