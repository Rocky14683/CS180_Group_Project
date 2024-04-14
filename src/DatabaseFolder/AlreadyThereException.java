package DatabaseFolder;

//Exception for if you try to add soemthing to a list and its already there
public class AlreadyThereException extends Exception {
    
    public AlreadyThereException(String message) {
        super(message);
    }
}
