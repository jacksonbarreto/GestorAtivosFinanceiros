package model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static model.Utilities.*;

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

    public User() {
    }

    public User(String username, String password, UserType userType) {
        this.username = username;
        this.salt = getSaltRandom();
        this.password = getHashedPassword(password,this.salt);
        this.userType = userType;
        this.financialAssets = new ArrayList<>();
        this.logs = new ArrayList<>();
    }

    public User(Long id, String username, String password, byte[] salt, UserType userType, ArrayList<FinancialAsset> financialAssets, ArrayList<Log> logs) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.userType = userType;
        this.financialAssets = financialAssets;
        this.logs = logs;
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
        List<FinancialAsset> filteredFinancialAssets = new ArrayList<>();
        for (FinancialAsset fa : financialAssets) {
            if (fa instanceof AssetWithInvestedValue) {
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

    //padroes

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

    @Column(name = "salt", nullable = false)
    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
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
    public List<FinancialAsset> getFinancialAssetsreverseOrderActive() {
        List<FinancialAsset> financialAssetsreverseOrderActive = new ArrayList<>();
        for (FinancialAsset fa : this.financialAssets) {
            if (fa.getStartDate().plusMonths(fa.getDuration()).isAfter(LocalDate.now()) ||
                    fa.getStartDate().plusMonths(fa.getDuration()).isEqual(LocalDate.now())) {
                financialAssetsreverseOrderActive.add(fa);
            }
        }
        financialAssetsreverseOrderActive.sort(Collections.reverseOrder());
        return financialAssetsreverseOrderActive;
    }

    /**
     * This method returns a collection of financial assets that are active over a two-date range.
     * Equal start and end date return the financial assets that end their duration, on the date informed.
     * @param initialDate Start date of consultation
     * @param finalDate End date of consultation
     * @return collection of financial assets active in the date range.
     */
    public List<FinancialAsset> getFinancialAssetsActive(LocalDate initialDate, LocalDate finalDate) {
        LocalDate temp;
        List<FinancialAsset> financialAssetsActive = new ArrayList<>();
        if (initialDate.isAfter(finalDate)) {
            temp = finalDate;
            finalDate = initialDate;
            initialDate = temp;
        }
        for (FinancialAsset fa : this.financialAssets) {
            if (dateIsInThePeriod(initialDate,finalDate,fa.getStartDate().plusMonths(fa.getDuration()))){
                financialAssetsActive.add(fa);
            }
        }
        return financialAssetsActive;
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
