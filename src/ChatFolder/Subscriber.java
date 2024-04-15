package ChatFolder;

import java.util.EventListener;
import java.util.function.Function;

public class Subscriber {
    private Function<String, Object> eventListener;

    public Subscriber() {
        this.eventListener = (String) -> {
            return true;
        }; // set callback
    }

    public void receive(String message) {
        //client -> in
        eventListener.apply(message);
    }


}
