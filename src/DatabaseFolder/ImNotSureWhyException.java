package DatabaseFolder;

//Exception to throw if anything breaks in an unexpected way, Prevents the program from crashing
public class ImNotSureWhyException extends Exception {
    
    public ImNotSureWhyException(String message) {
        super(message);
    }
}
