package ChatFolder;

import java.util.ArrayList;

public class Topic {
    private ArrayList<Subscriber> subscribers;

    public Topic() {
        subscribers = new ArrayList<>();
    }

    void addSubscriber(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    void notifySubscribers(String message) {
        for (Subscriber subscriber : subscribers) {
            subscriber.receive(message);
        }
    }

}
