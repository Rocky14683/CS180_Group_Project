package DatabaseFolder;


//Exception thrown if you try to do something but some preexisting conditon stops you (Like adding someone who has you blocked)
public class BlockedException extends Exception {
    
    public BlockedException(String message) {
        super(message);
    }
}
