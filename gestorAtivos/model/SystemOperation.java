package model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "LogSistema")
@Access(AccessType.PROPERTY)
public class SystemOperation implements Serializable {
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
    /**
     * Method to obtain the ID (from the database) of the SystemOperation.
     *
     * @return The ID (of the database) of the SystemOperation.
     */
    @Id
    @GeneratedValue
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    /**
     * Method to define the ID (of the database) of the occurrence.
     * @param id The (database) ID of the issue.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Method to obtain the instant of the occurrence.
     * @return The instant of occurrence.
     */
    public LocalDateTime getMoment() {
        return moment;
    }

    /**
     * Method to define the instant of the occurrence. Exclusive use of ORM.
     * @param moment The instant of occurrence.
     */
    private void setMoment(LocalDateTime moment) {
        this.moment = moment;
    }

    /**
     * Method to get the message of the occurrence.
     * @return The occurrence message.
     */
    @Column(name = "Mensagem", nullable = false, length = 500)
    public String getMessage() {
        return message;
    }

    /**
     * Method to define the event message. Exclusive use of ORM.
     *
     * @param message The occurrence message.
     */
    private void setMessage(String message) {
        this.message = message;
    }
}
