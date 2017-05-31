package kr.rvs.coinview.storage;

import com.google.gson.annotations.SerializedName;

import kr.rvs.coinview.util.Static;

/**
 * Created by Junhyeong Lim on 2017-05-19.
 */

public class PriceStorage {
    @SerializedName("currency")
    private String name;
    @SerializedName("last")
    private Integer currPrice;
    @SerializedName("high")
    private Integer highPrice;

    private CoinType type;

    public PriceStorage(String name, Integer currPrice, Integer highPrice) {
        this.name = name;
        this.currPrice = currPrice;
        this.highPrice = highPrice;
        this.type = CoinType.getTypeByName(name);
    }

    public PriceStorage(CoinType type, Integer currPrice, Integer highPrice) {
        this.type = type;
        this.currPrice = currPrice;
        this.highPrice = highPrice;
    }

    public CoinType getType() {
        if (type == null)
            type = CoinType.getTypeByName(name);
        return type;
    }

    public Integer getCurrPrice() {
        return currPrice != null ? currPrice : 0;
    }

    public Integer getHighPrice() {
        return highPrice;
    }

    public String getCurrPriceStr() {
        return Static.numberCommaFormat(getCurrPrice());
    }

    public String getHighPriceStr() {
        return Static.numberCommaFormat(getHighPrice());
    }

    @Override
    public String toString() {
        return "PriceStorage{" +
                "name='" + name + '\'' +
                ", getCurrPrice=" + currPrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PriceStorage that = (PriceStorage) o;

        return name != null ? name.equals(that.name) : that.name == null;

    }
}