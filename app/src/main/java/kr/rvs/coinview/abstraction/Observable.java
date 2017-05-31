package kr.rvs.coinview.abstraction;

/**
 * Created by Junhyeong Lim on 2017-05-29.
 */

public interface Observable<T> {
    void update(T obj);

    boolean shouldRunOnMainThread();
}
