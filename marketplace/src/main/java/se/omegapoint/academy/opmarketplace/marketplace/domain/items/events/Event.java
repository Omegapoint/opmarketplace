package se.omegapoint.academy.opmarketplace.marketplace.domain.items.events;

import java.io.*;
import java.util.Base64;

public class Event<T extends Serializable> implements Serializable {

    private final String serializedEvent;

    public Event(T eventObject) throws IOException {
        this.serializedEvent = serialize(eventObject);
    }

    public T getEvent() throws IOException, ClassNotFoundException {
        return deSerialize(serializedEvent);
    }


    private T deSerialize( String s ) throws IOException,
            ClassNotFoundException {
        byte [] data = Base64.getDecoder().decode( s );
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(  data ) );
        Object o  = ois.readObject();
        ois.close();
        return (T)o;
    }

    /** Write the object to a Base64 string. */
    private String serialize( Serializable o ) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject( o );
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }
}
