package Exceptions;
public class InvalidInputObject extends Exception {
    public InvalidInputObject(String message) {
        super(message);
        System.out.println("This exception is not being caught becuase it indicates a code error\n" +
                "Either you are setting the inputObject to the wrong object type or there is an error in DatabaseFolder.DataWriter run");
    }
}
