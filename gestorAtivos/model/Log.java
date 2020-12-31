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

    protected Log() {
    }

    public Log(Operation operation) {
        this.operation = operation;
        this.moment = LocalDateTime.now();
    }

    public Log(Long id, LocalDateTime moment, Operation operation) {
        this.id = id;
        this.moment = moment;
        this.operation = operation;
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    @Column(name = "Momento", nullable = false)
    public LocalDateTime getMoment() {
        return moment;
    }

    @Column(name = "Operacao", nullable = false)
    @Enumerated(EnumType.STRING)
    public Operation getOperation() {
        return operation;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public void setMoment(LocalDateTime moment) {
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
