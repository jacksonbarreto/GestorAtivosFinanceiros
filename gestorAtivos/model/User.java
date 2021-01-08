package model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static model.AssetType.DEPOSIT;
import static model.AssetType.FOUND;
import static model.Operation.*;
import static model.Utilities.*;
import static model.Utilities.dateIsAfterOrEqual;

@Entity
@Table(name = "Utilizador")
@Access(AccessType.PROPERTY)
public class User implements Serializable {

    private Long id;
    private String username;
    private String password;
    private byte[] salt;
    private UserType userType;
    private List<FinancialAsset> financialAssets;
    private List<Log> logs;

    /**
     * User builder. Exclusively for ORM.
     */
    private User() {
    }

    /**
     * User builder.
     *
     * @param username login name.
     * @param password access password.
     * @param userType user profile.
     */
    public User(String username, String password, UserType userType) {
        if (username == null || password == null || userType == null) {
            throw new IllegalArgumentException();
        } else if (username.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException();
        } else if (username.length() <= 3 || password.length() <= 3) {
            throw new IllegalArgumentException();
        }
        this.username = username;
        this.salt = getSaltRandom();
        this.password = getHashedPassword(password, this.salt);
        this.userType = userType;
        this.financialAssets = new ArrayList<>();
        this.logs = new ArrayList<>();
        this.id = null;
        addLog(CREATED_USER);
    }

    /**
     * Method to record a user log.
     *
     * @param operation Operation.
     */
    public void addLog(Operation operation) {
        if (operation == null)
            throw new IllegalArgumentException();
        this.logs.add(new Log(operation));
    }

    /**
     * Method for adding a financial asset to the user's asset collection.
     *
     * @param financialAsset A financial asset.
     */
    public void addAssetFinancial(FinancialAsset financialAsset) {
        if (financialAsset == null)
            throw new IllegalArgumentException();
        this.financialAssets.add(financialAsset);
        switch (financialAsset.getAssetType()) {
            case FOUND:
                addLog(ADDED_INVESTMENT_FUND);
                break;
            case DEPOSIT:
                addLog(ADDED_DEPOSIT);
                break;
            case PROPERTY:
                addLog(ADDED_PROPERTY);
        }
    }

    public void removeAssetFinancial(Long id) {
        if (id == null)
            throw new IllegalArgumentException();
        for (FinancialAsset fa : this.financialAssets) {
            if (fa.getId() == id) {
                this.financialAssets.remove(fa);
            }
        }
    }

    /**
     * find financial asset
     * <p>
     * This method queries the user's financial assets, returning a collection of assets that have an occurrence of the name sought in their name.
     * This method ignores case.
     * <p>
     * Example:
     * name to be searched for: "Yellow"
     * Returned assets:
     * 1) YELLOW house in Viseu
     * 2) Yellow investment fund
     *
     * @param designation name to be searched
     * @return asset collection
     */
    public List<FinancialAsset> findFinancialAsset(String designation) {
        if (designation == null) {
            throw new IllegalArgumentException();
        } else if (designation.isEmpty()) {
            throw new IllegalArgumentException();
        } else if (designation.length() <= 3) {
            throw new IllegalArgumentException();
        }

        List<FinancialAsset> financialAssets = new ArrayList<>();

        for (FinancialAsset fa : this.financialAssets) {
            if (fa.getDesignation().toLowerCase().matches("(.*)" + designation.toLowerCase() + "(.*)")) {
                financialAssets.add(fa);
            }
        }
        return financialAssets;
    }

    /**
     * find financial asset
     * <p>
     * This method queries the user's financial assets, returning a collection of assets that are the same type of asset sought, such as "investment fund" assets.
     *
     * @param assetType Financial asset type
     * @return asset collection
     */
    public List<FinancialAsset> findFinancialAsset(AssetType assetType) {
        if (assetType == null)
            throw new IllegalArgumentException();
        List<FinancialAsset> financialAssets = new ArrayList<>();
        for (FinancialAsset fa : this.financialAssets) {
            if (fa.getAssetType() == assetType) {
                financialAssets.add(fa);
            }
        }
        return financialAssets;
    }

    /**
     * find financial asset
     * <p>
     * This method queries the user's financial assets, returning a collection of assets that are of the same type of financial asset sought and that have in their name any occurrence of the designation sought.
     * This method ignores case.
     * <p>
     * Example:
     * name to be searched for: "Yellow"
     * type of financial asset sought: "Properties for rent"
     * Returned assets:
     * 1) YELLOW house in Viseu
     * 2) yellow building in Vienna
     *
     * @param designation name to be searched
     * @param assetType   Financial asset type
     * @return asset collection
     */
    public List<FinancialAsset> findFinancialAsset(String designation, AssetType assetType) {
        if (designation == null || assetType == null) {
            throw new IllegalArgumentException();
        } else if (designation.isEmpty()) {
            throw new IllegalArgumentException();
        } else if (designation.length() <= 3) {
            throw new IllegalArgumentException();
        }
        List<FinancialAsset> financialAssets = new ArrayList<>();
        for (FinancialAsset fa : this.financialAssets) {
            if (fa.getDesignation().toLowerCase().matches("(.*)" + designation.toLowerCase() + "(.*)") && fa.getAssetType() == assetType) {
                financialAssets.add(fa);
            }
        }
        return financialAssets;
    }

    /**
     * Filter by amount invested.
     * <p>
     * This method applies a filter on the amount invested in the financial asset and returns only those assets that meet this filter.
     * The filter only works on financial assets that implement the "AssetWithInvestedValue" interface.
     * <p>
     * Filters: BIGGER_THEN, LESS_THAN, LESS_OR_EQUAL, BIGGER_OR_EQUAL, EQUAL
     *
     * @param financialAssets collection of financial assets that implement the "AssetWithInvestedValue" interface
     * @param logicalOperator Filters: BIGGER_THEN, LESS_THAN, LESS_OR_EQUAL, BIGGER_OR_EQUAL, EQUAL
     * @param referenceValue  value to be applied when comparing the filter
     * @return collection of financial assets that meet the filter
     */
    public static List<FinancialAsset> filterByAmountInvested(List<FinancialAsset> financialAssets, LogicalOperator logicalOperator, BigDecimal referenceValue) {
        if (financialAssets == null || referenceValue == null || logicalOperator == null || referenceValue.compareTo(new BigDecimal("0")) <= 0) {
            throw new IllegalArgumentException();
        }
        List<FinancialAsset> filteredFinancialAssets = new ArrayList<>();
        for (FinancialAsset fa : financialAssets) {
            if (fa instanceof AssetWithInvestedValue && (fa.getAssetType() == FOUND || fa.getAssetType() == DEPOSIT)) {
                switch (logicalOperator) {
                    case EQUAL:
                        if (((AssetWithInvestedValue) fa).getAmountInvested().equals(referenceValue)) {
                            filteredFinancialAssets.add(fa);
                        }
                        break;
                    case LESS_THAN:
                        if (((AssetWithInvestedValue) fa).getAmountInvested().compareTo(referenceValue) < 0) {
                            filteredFinancialAssets.add(fa);
                        }
                        break;
                    case BIGGER_THEN:
                        if (((AssetWithInvestedValue) fa).getAmountInvested().compareTo(referenceValue) > 0) {
                            filteredFinancialAssets.add(fa);
                        }
                        break;
                    case LESS_OR_EQUAL:
                        if (((AssetWithInvestedValue) fa).getAmountInvested().compareTo(referenceValue) <= 0) {
                            filteredFinancialAssets.add(fa);
                        }
                        break;
                    case BIGGER_OR_EQUAL:
                        if (((AssetWithInvestedValue) fa).getAmountInvested().compareTo(referenceValue) >= 0) {
                            filteredFinancialAssets.add(fa);
                        }
                }
            }
        }
        return filteredFinancialAssets;
    }


    /**
     * Method to obtain the ID (from the database) of the user.
     *
     * @return The ID (of the database) of the user.
     */
    @Id
    @GeneratedValue
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    /**
     * Method to obtain the user's login name.
     *
     * @return The user's login name.
     */
    @Column(name = "Username", nullable = false, unique = true)
    public String getUsername() {
        return username;
    }

    /**
     * Method for changing the user's login name.
     *
     * @param username New user login name.
     */
    public void changeUsername(String username) {
        if (username == null || username.isEmpty() || username.length() <= 3)
            throw new IllegalArgumentException();
        this.username = username;
        addLog(CHANGED_USERNAME);
    }

    /**
     * Method for changing the user's login name. Exclusive use of ORM
     *
     * @param username New user login name.
     */
    private void setUsername(String username) {
        this.username = username;
    }

    /**
     * Method to obtain the user's password.
     *
     * @return The user's encrypted access password.
     */
    @Column(name = "Password", nullable = false)
    public String getPassword() {
        return password;
    }

    /**
     * Method for changing the user's password.
     *
     * @param password New user access password.
     */
    public void changePassword(String password) {
        if (password == null || password.isEmpty() || password.length() <= 3)
            throw new IllegalArgumentException();
        this.salt = getSaltRandom();
        this.password = getHashedPassword(password, this.salt);
        addLog(CHANGED_PASSWORD);
    }

    /**
     * Method for changing the user's password. Exclusive use of ORM
     *
     * @param password New user access password.
     */
    private void setPassword(String password) {
        this.password = password;
    }

    /**
     * Method to obtain salt from the user.
     *
     * @return The user's salt.
     */
    @Column(name = "salt", nullable = false)
    public byte[] getSalt() {
        return salt;
    }

    /**
     * Method to define the user's salt. Exclusive use of ORM
     *
     * @param salt the user's salt
     */
    private void setSalt(byte[] salt) {
        this.salt = salt;
    }

    /**
     * Method to obtain the type of user profile.
     *
     * @return The type of the user.
     */
    @Column(name = "TipoUsuario", nullable = false)
    @Enumerated(EnumType.STRING)
    public UserType getUserType() {
        return userType;
    }

    /**
     * Method for changing the type of user profile.
     *
     * @param userType new user type.
     */
    public void changeUserType(UserType userType) {
        if (userType == null)
            throw new IllegalArgumentException();
        this.userType = userType;
        addLog(CHANGED_USER_TYPE);
    }

    private void setUserType(UserType userType) {
        this.userType = userType;
    }

    /**
     * Method to obtain all the financial assets of the user.
     *
     * @return collection of financial assets.
     */
    @Fetch(FetchMode.SELECT)
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "AtivoUtilizador", joinColumns = @JoinColumn(name = "Utilizador", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "AtivoFinanceiro", referencedColumnName = "id"))
    public List<FinancialAsset> getFinancialAssets() {
        return financialAssets;
    }

    /**
     * This method returns a collection with the user's financial assets, which are active on the current date,
     * descendingly ordered by the amount invested initially.
     *
     * @return collection of financial assets in descending order.
     */
    @Transient
    public List<FinancialAsset> getFinancialAssetsreverseOrderActive() {
        List<FinancialAsset> financialAssetsreverseOrderActive = new ArrayList<>();
        for (FinancialAsset fa : this.financialAssets) {
            if (dateIsBeforeOrEqual(LocalDate.now(), fa.getStartDate()) &&
                    dateIsAfterOrEqual(LocalDate.now(), fa.getStartDate().plusMonths(fa.getDuration()))) {
                financialAssetsreverseOrderActive.add(fa);
            }
        }
        financialAssetsreverseOrderActive.sort(Collections.reverseOrder());
        return financialAssetsreverseOrderActive;
    }

    /**
     * This method returns a collection of financial assets that are active over a two-date range.
     * Being active means having your creation date equal to or before the start date and having your
     * last payment on or after the end date.
     *
     * @param initialDate Start date of consultation
     * @param finalDate   End date of consultation
     * @return collection of financial assets active in the date range.
     */
    @Transient
    public List<FinancialAsset> getFinancialAssetsActive(LocalDate initialDate, LocalDate finalDate) {
        if (initialDate == null | finalDate == null)
            throw new IllegalArgumentException();

        List<FinancialAsset> financialAssetsActive = new ArrayList<>();

        for (FinancialAsset fa : this.financialAssets) {
            if (
                    dateIsBeforeOrEqual(initialDate, fa.getStartDate()) &&
                            dateIsAfterOrEqual(finalDate, fa.getStartDate().plusMonths(fa.getDuration()))

            ) {
                financialAssetsActive.add(fa);
            }
        }
        return financialAssetsActive;
    }

    /**
     * Method to obtain the listing of user logs.
     *
     * @return Collection of user logs.
     */

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "Utilizador", referencedColumnName = "id", nullable = false)
    public List<Log> getLogs() {
        return logs;
    }

    /**
     * Method for setting the ID (of the database). Exclusive use of ORM.
     *
     * @param id The ID (of the database).
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Method for defining the user's list of financial assets. Exclusive use of ORM.
     *
     * @param financialAssets The user's list of financial assets.
     */
    private void setFinancialAssets(List<FinancialAsset> financialAssets) {
        this.financialAssets = financialAssets;
    }

    /**
     * Method to define the list of user logs. Exclusive use of ORM.
     *
     * @param logs The list of user logs.
     */
    private void setLogs(List<Log> logs) {
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", salt=" + Arrays.toString(salt) +
                ", userType=" + userType +
                ", financialAssets=" + financialAssets +
                ", logs=" + logs +
                '}';
    }
}
