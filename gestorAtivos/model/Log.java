package model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "Log")
@Access(AccessType.PROPERTY)
public class Log implements Serializable {

    private Long id;
    private LocalDateTime moment;
    private Operation operation;

    /**
     * Log builder for exclusive use of ORM.
     */
    private Log() {
    }

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
     * Method for obtaining the log (database) ID.
     *
     * @return The log (database) ID.
     */
    @Id
    @GeneratedValue
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    /**
     * Method for obtaining the record date and time.
     *
     * @return The date and time of registration.
     */
    @Column(name = "Momento", nullable = false)
    public LocalDateTime getMoment() {
        return moment;
    }

    /**
     * Method for obtaining the registration operation.
     *
     * @return The registration operation.
     */
    @Column(name = "Operacao", nullable = false)
    @Enumerated(EnumType.STRING)
    public Operation getOperation() {
        return operation;
    }

    /**
     * Method for setting the ID (of the database). Exclusive use of ORM.
     *
     * @param id The ID (of the database).
     */
    private void setId(Long id) {
        this.id = id;
    }

    /**
     * Method to define the operation of the log. Exclusive use of ORM.
     *
     * @param operation The operation of the log.
     */
    private void setOperation(Operation operation) {
        this.operation = operation;
    }

    /**
     * Method for setting the log record date and time. Exclusive use of ORM.
     *
     * @param moment The log record date and time.
     */
    private void setMoment(LocalDateTime moment) {
        this.moment = moment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Log)) return false;
        Log log = (Log) o;
        return Objects.equals(getId(), log.getId()) && getMoment().equals(log.getMoment()) && getOperation() == log.getOperation();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMoment(), getOperation());
    }
}
