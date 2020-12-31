package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Banco")
@Access(AccessType.PROPERTY)
public class Bank implements Serializable {

    private Long id;
    private String name;
    private List<TermDeposit> termDeposits;

    protected Bank() {

    }

    public Bank(String name, ArrayList<TermDeposit> termDeposits) {
        this.name = name;
        this.termDeposits = termDeposits;
    }

    public Bank(String name) {
        this.name = name;
        termDeposits = new ArrayList<>();
    }

    @OneToMany(mappedBy = "bank", fetch = FetchType.EAGER)
    public List<TermDeposit> getTermDeposits() {
        return termDeposits;
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    @Column(name = "Nome", nullable = false)
    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTermDeposits(ArrayList<TermDeposit> termDeposits) {
        this.termDeposits = termDeposits;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTermDeposits(List<TermDeposit> termDeposits) {
        this.termDeposits = termDeposits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bank)) return false;
        Bank bank = (Bank) o;
        return Objects.equals(getId(), bank.getId()) && getName().equals(bank.getName()) && getTermDeposits().equals(bank.getTermDeposits());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getTermDeposits());
    }
}
