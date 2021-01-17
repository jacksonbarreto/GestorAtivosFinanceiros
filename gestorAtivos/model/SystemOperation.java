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
     * builder for cloning.
     * @param systemOperation Instance to be cloned.
     */
    public SystemOperation(SystemOperation systemOperation){
        this.message = systemOperation.getMessage();
        this.moment = systemOperation.getMoment();
    }


    /**
     * Method to obtain the instant of the occurrence.
     * @return The instant of occurrence.
     */
    public LocalDateTime getMoment() {
        return LocalDateTime.parse(moment.toString());
    }


    /**
     * Method to get the message of the occurrence.
     * @return The occurrence message.
     */
    public String getMessage() {
        return new String(message.toString());
    }

    @Override
    public SystemOperation clone(){
        return new SystemOperation(this);
    }

}
