package kr.rvs.coinview.storage;

/**
 * Created by Junhyeong Lim on 2017-05-28.
 */

public class CoinoneStorage {
    private PriceStorage btc;
    private PriceStorage etc;
    private PriceStorage eth;
    private PriceStorage xrp;

    public PriceStorage getBtc() {
        return btc;
    }

    public PriceStorage getEtc() {
        return etc;
    }

    public PriceStorage getEth() {
        return eth;
    }

    public PriceStorage getXrp() {
        return xrp;
    }
}
