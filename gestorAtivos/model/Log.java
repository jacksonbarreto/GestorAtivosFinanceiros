package model;

import java.time.LocalDateTime;

public class Log {
    private final LocalDateTime moment;
    private Operation operation;

    public Log(Operation operation) {
        this.operation = operation;
        this.moment = LocalDateTime.now();
    }
}
