package kr.rvs.coinview.abstraction;

import android.app.PendingIntent;
import android.content.Intent;
import android.text.format.DateFormat;
import android.widget.RemoteViews;

import java.util.Date;

import kr.rvs.coinview.R;
import kr.rvs.coinview.activity.MainActivity;
import kr.rvs.coinview.storage.CoinType;
import kr.rvs.coinview.storage.PriceStorage;
import kr.rvs.coinview.util.Static;

/**
 * Created by Junhyeong Lim on 2017-05-29.
 */

public class NotifiableObserver implements Observable<PriceStorage> {
    @Override
    public void update(PriceStorage obj) {
        PriceStorage btc = Static.priceList.getStorage(CoinType.BITCOIN);
        PriceStorage eth = Static.priceList.getStorage(CoinType.ETHEREUM);
        PriceStorage etc = Static.priceList.getStorage(CoinType.ETHEREUM_CLASSIC);
        PriceStorage xrp = Static.priceList.getStorage(CoinType.RIPPLE);

        RemoteViews contentView = new RemoteViews(Static.activity.getPackageName(), R.layout.price_notification_new);
        contentView.setTextViewText(R.id.btcPrice, btc.getCurrPriceStr());
        contentView.setTextViewText(R.id.ethPrice, eth.getCurrPriceStr());
        contentView.setTextViewText(R.id.etcPrice, etc.getCurrPriceStr());
        contentView.setTextViewText(R.id.xrpPrice, xrp.getCurrPriceStr());
        contentView.setTextViewText(R.id.updateTime,
                DateFormat.format("a hh:mm:ss", new Date()));

        Intent intent = new Intent(Static.activity, MainActivity.class);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        PendingIntent pendingIntent = PendingIntent.getActivity(Static.activity, 0, intent, 0);

        Static.doNotify(
                Static.getNotificationCompatBuilder()
                        .setContent(contentView)
                        .setSmallIcon(R.drawable.ic_bitcoin_surrounded)
                        .setOngoing(true)
                        .setContentIntent(pendingIntent)
                        .build()
        );
    }

    @Override
    public boolean shouldRunOnMainThread() {
        return true;
    }
}
