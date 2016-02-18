package se.omegapoint.academy.opmarketplace.marketplace.domain.items.events;

import java.io.*;
import java.util.ArrayList;

public class TestEvents {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ArrayList<Event> events = new ArrayList<>();
        events.add(new Event<>(new TitleUpdated("hej")));
        events.add(new Event<>(new DescriptionUpdated("hej pa dig")));
        //Serialize
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("C:\\Users\\Jonas\\Documents\\events.txt"));
        os.writeObject(events);

        readEvents("C:\\Users\\Jonas\\Documents\\events.txt");
    }

    private static void readEvents(String filePath) throws IOException, ClassNotFoundException {
        ObjectInputStream is = new ObjectInputStream(new FileInputStream(filePath));
        ArrayList<Event> events = new ArrayList<>();
        events = (ArrayList<Event>) is.readObject();
        for (Event e : events) {
            Object a = e.getEvent();
            if (a instanceof TitleUpdated){
                System.out.println(((TitleUpdated) a).getNewTitle());
            }
            if (a instanceof DescriptionUpdated){
                System.out.println(((DescriptionUpdated) a).getNewDescription());
            }
        }
    }
}
