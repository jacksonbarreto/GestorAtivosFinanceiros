package model;

import java.time.LocalDateTime;

public class SystemOperation {
    private Long id;
    private LocalDateTime moment;
    private String message;

    /**
     * Log builder for exclusive use of ORM.
     */
    private SystemOperation(){}

    public SystemOperation(String message) {
        if (message == null || message.isEmpty() || message.length() < 2)
            throw new IllegalArgumentException();
        this.message = message;
        this.moment = LocalDateTime.now();
    }
}
