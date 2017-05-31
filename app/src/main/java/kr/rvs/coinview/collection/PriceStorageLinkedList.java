package kr.rvs.coinview.collection;

import java.util.LinkedList;

import kr.rvs.coinview.storage.CoinType;
import kr.rvs.coinview.storage.PriceStorage;

/**
 * Created by Junhyeong Lim on 2017-05-28.
 */

public class PriceStorageLinkedList extends LinkedList<PriceStorage> {
    @Override
    public boolean add(PriceStorage storage) {
        for (int i = 0; i < size(); i++) {
            PriceStorage obj = get(i);
            if (obj == null)
                continue;
            if (obj.equals(storage)) {
                remove(i);
            }
        }
        add(size(), storage);
        return true;
    }

    public PriceStorage getStorage(CoinType type) {
        for (PriceStorage storage : this) {
            if (storage.getType().equals(type))
                return storage;
        }
        return new PriceStorage(type, 0, 0);
    }
}
