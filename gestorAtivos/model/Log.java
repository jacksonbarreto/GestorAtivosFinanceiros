package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Log implements Serializable {
    private static final long serialVersionUID = 1L;
    private final LocalDateTime moment;
    private final Operation operation;

    /**
     * Log Builder.
     *
     * @param operation Operation to be registered.
     */
    public Log(Operation operation) {
        if (operation == null)
            throw new IllegalArgumentException();
        this.operation = operation;
        this.moment = LocalDateTime.now();
    }


    /**
     * Method for obtaining the record date and time.
     *
     * @return The date and time of registration.
     */
    public LocalDateTime getMoment() {
        return moment;
    }

    /**
     * Method for obtaining the registration operation.
     *
     * @return The registration operation.
     */
    public Operation getOperation() {
        return operation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Log)) return false;
        Log log = (Log) o;
        return getMoment().equals(log.getMoment()) && getOperation() == log.getOperation();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMoment(), getOperation());
    }

    @Override
    public String toString() {
        return "Log{" +
                ", moment=" + moment +
                ", operation=" + operation +
                '}';
    }
}
