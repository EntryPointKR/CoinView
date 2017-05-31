package kr.rvs.coinview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import kr.rvs.coinview.R;
import kr.rvs.coinview.abstraction.Observable;
import kr.rvs.coinview.storage.PriceStorage;
import kr.rvs.coinview.util.Static;

/**
 * Created by Junhyeong Lim on 2017-05-21.
 */

public class PriceAdapter extends BaseAdapter implements Observable<PriceStorage> {

    @Override
    public int getCount() {
        return Static.priceList.size();
    }

    @Override
    public Object getItem(int position) {
        return Static.priceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.price_listview, parent, false);
        }

        TextView logo = (TextView) convertView.findViewById(R.id.logo);
        TextView currPriceView = (TextView) convertView.findViewById(R.id.currPrice);
        TextView highPriceView = (TextView) convertView.findViewById(R.id.highPrice);

        PriceStorage storage = (PriceStorage) getItem(position);

        logo.setText(storage.getType().getSimpleName());
        currPriceView.setText(context.getString(R.string.current_price, storage.getCurrPriceStr()));
        highPriceView.setText(context.getString(R.string.high_price, storage.getHighPriceStr()));

        return convertView;
    }

    @Override
    public void update(final PriceStorage storage) {
        Static.priceList.add(storage);
        notifyDataSetChanged();
        Static.makeSnackbar(Static.getString(R.string.refreshed_info));
    }

    @Override
    public boolean shouldRunOnMainThread() {
        return true;
    }
}
