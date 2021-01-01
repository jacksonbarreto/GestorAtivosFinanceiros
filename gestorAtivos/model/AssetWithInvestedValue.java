package model;

import java.math.BigDecimal;

public interface AssetWithInvestedValue extends Comparable<FinancialAsset> {
    BigDecimal getAmountInvested();
}
