package kr.rvs.coinview.storage;

/**
 * Created by Junhyeong Lim on 2017-05-28.
 */


public enum CoinType {
    BITCOIN,
    ETHEREUM,
    ETHEREUM_CLASSIC,
    RIPPLE;

    public static CoinType getTypeByName(String name) {
        switch (name) {
            case "btc":
                return BITCOIN;
            case "eth":
                return ETHEREUM;
            case "etc":
                return ETHEREUM_CLASSIC;
            case "xrp":
                return RIPPLE;
        }
        return null;
    }


    @Override
    public String toString() {
        switch (this) {
            case BITCOIN:
                return "BITCOIN";
        }
        return "NULL";
    }

    public String getSimpleName() {
        switch (this) {
            case BITCOIN:
                return "BTC";
            case ETHEREUM:
                return "ETH";
            case ETHEREUM_CLASSIC:
                return "ETC";
            case RIPPLE:
                return "XRP";
        }
        return "NULL";
    }
}