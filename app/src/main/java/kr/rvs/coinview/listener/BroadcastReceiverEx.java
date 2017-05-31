package kr.rvs.coinview.listener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import kr.rvs.coinview.util.Static;

/**
 * Created by Junhyeong Lim on 2017-06-01.
 */

public class BroadcastReceiverEx extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case "PRICE_REFRESH":
                priceRefresh();
        }
        
    }

    private void priceRefresh() {
        Static.producerThread.forceGet();
    }
}
