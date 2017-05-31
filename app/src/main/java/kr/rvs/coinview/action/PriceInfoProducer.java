package kr.rvs.coinview.action;

import android.support.design.widget.Snackbar;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import kr.rvs.coinview.R;
import kr.rvs.coinview.storage.CoinoneStorage;
import kr.rvs.coinview.storage.PriceStorage;
import kr.rvs.coinview.util.Settings;
import kr.rvs.coinview.util.Static;

/**
 * Created by Junhyeong Lim on 2017-05-19.
 */

public class PriceInfoProducer extends Thread {
    private static final Gson GSON = new Gson();

    private Queue<PriceStorage> queue;
    private TimeUnit unit;
    private Integer period;
    private Set<URL> urls = new HashSet<>();
    private long tickCount;

    public PriceInfoProducer(Queue<PriceStorage> queue, TimeUnit unit, Integer period, URL... urls) {
        this.queue = queue;
        this.unit = unit;
        this.period = period;
        this.tickCount = getPeriodTick();

        for (URL url : urls) {
            this.urls.add(url);
        }
    }

    @Override
    public void run() {
        while (true) {
            delay();
            execute();
        }
    }

    private long getPeriodTick() {
        return unit.toMillis(period) / 50;
    }

    private void delay() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            // Ignore
        }
    }

    private void execute() {
        if (tickCount++ >= getPeriodTick()) {
            if (!Static.isMobileDataEnabled() || Settings.canRefreshIfMobileNetworkIsOn) {
                try {
                    get();
                } catch (Throwable ex) {
                    processException(ex);
                }
            } else {
                Static.makeSnackbar(Static.getString(R.string.mobile_network_detect));
            }
            tickCount = 0;
        }
    }

    private void get() throws IOException {
        for (URL url : urls) {
            final CoinoneStorage storage = GSON.fromJson(
                    Static.getContentByURL(url), CoinoneStorage.class);
            if (storage != null) {
                queue.add(storage.getBtc());
                queue.add(storage.getEth());
                queue.add(storage.getEtc());
                queue.add(storage.getXrp());
            }
        }
    }

    public void forceGet() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    get();
                } catch (Throwable e) {
                    processException(e);
                }
            }
        }).start();
    }

    private void processException(Throwable th) {
        if (th instanceof UnknownHostException) {
            Static.makeSnackbar("갱신 실패, 인터넷 연결을 확인해주세요.");
        } else {
            Static.ex(th);
        }
    }
}
