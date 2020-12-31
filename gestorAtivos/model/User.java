package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Utilizador")
@Access(AccessType.PROPERTY)
public class User implements Serializable {

    private Long id;
    private String username;
    private String password;
    private UserType userType;
    private List<FinancialAsset> financialAssets;
    private List<Log> logs;

    public User() {
    }

    public User(String username, String password, UserType userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.financialAssets = new ArrayList<>();
        this.logs = new ArrayList<>();
    }

    public User(Long id, String username, String password, UserType userType, ArrayList<FinancialAsset> financialAssets, ArrayList<Log> logs) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.financialAssets = financialAssets;
        this.logs = logs;
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    @Column(name = "Username", nullable = false)
    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "Password", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Column(name = "TipoUsuario", nullable = false)
    @Enumerated(EnumType.STRING)
    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "AtivoUtilizador", joinColumns = @JoinColumn(name = "Utilizador",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "AtivoFinanceiro", referencedColumnName = "id"))
    public List<FinancialAsset> getFinancialAssets() {
        return financialAssets;
    }

    @OneToMany
    @JoinColumn(name = "Utilizador", referencedColumnName = "id", nullable = false)
    public List<Log> getLogs() {
        return logs;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFinancialAssets(ArrayList<FinancialAsset> financialAssets) {
        this.financialAssets = financialAssets;
    }

    public void setLogs(ArrayList<Log> logs) {
        this.logs = logs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId()) && getUsername().equals(user.getUsername()) && getPassword().equals(user.getPassword()) && getUserType() == user.getUserType() && Objects.equals(getFinancialAssets(), user.getFinancialAssets()) && Objects.equals(getLogs(), user.getLogs());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getPassword(), getUserType(), getFinancialAssets(), getLogs());
    }
}
