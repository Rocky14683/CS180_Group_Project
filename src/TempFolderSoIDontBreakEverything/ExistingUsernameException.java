package TempFolderSoIDontBreakEverything;

// package DatabaseFolder;

public class ExistingUsernameException extends Exception{

    public ExistingUsernameException (String message) {
        super(message);
        
    }
}