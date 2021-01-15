package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class SystemOperation implements Serializable {
    private static final long serialVersionUID = 1L;
    private final LocalDateTime moment;
    private final String message;


    public SystemOperation(String message) {
        if (message == null || message.isEmpty() || message.length() < 2)
            throw new IllegalArgumentException();
        this.message = message;
        this.moment = LocalDateTime.now();
    }


    /**
     * Method to obtain the instant of the occurrence.
     * @return The instant of occurrence.
     */
    public LocalDateTime getMoment() {
        return moment;
    }


    /**
     * Method to get the message of the occurrence.
     * @return The occurrence message.
     */
    public String getMessage() {
        return message;
    }

}
