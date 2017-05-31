package kr.rvs.coinview.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import kr.rvs.coinview.abstraction.Observable;
import kr.rvs.coinview.storage.PriceStorage;
import kr.rvs.coinview.util.Static;

/**
 * Created by Junhyeong Lim on 2017-05-19.
 */

public class PriceInfoConsumer extends Thread {
    private BlockingQueue<PriceStorage> queue;
    private List<Observable<PriceStorage>> observables;

    @SafeVarargs
    public PriceInfoConsumer(BlockingQueue<PriceStorage> queue, Observable<PriceStorage>... observables) {
        this.queue = queue;
        this.observables = new ArrayList<>(Arrays.asList(observables));
    }

    @Override
    public void run() {
        while (true) {
            try {
                PriceStorage storage = queue.take();
                if (storage != null) {
                    receive(storage);
                }
            } catch (InterruptedException e) {
                // Ignore
            }
        }
    }

    private void receive(final PriceStorage storage) {
        for (final Observable<PriceStorage> observable : observables) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    observable.update(storage);
                }
            };
            try {
                if (observable.shouldRunOnMainThread()) {
                    Static.activity.runOnUiThread(runnable);
                } else {
                    runnable.run();
                }
            } catch (Exception ex) {
                Static.ex(ex);
            }
        }
    }
}
