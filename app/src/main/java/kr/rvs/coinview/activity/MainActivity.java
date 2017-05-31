package kr.rvs.coinview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import kr.rvs.coinview.R;
import kr.rvs.coinview.abstraction.NotifiableObserver;
import kr.rvs.coinview.action.PriceInfoConsumer;
import kr.rvs.coinview.action.PriceInfoProducer;
import kr.rvs.coinview.adapter.PriceAdapter;
import kr.rvs.coinview.storage.PriceStorage;
import kr.rvs.coinview.util.Static;
import kr.rvs.coinview.util.URLs;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Static.producerThread.forceGet();
            }
        });

        Static.activity = this;
        Static.view = fab;

        init();
    }

    private void init() {
        Static.permissionRequest();

        final NotifiableObserver notifiableObserver = new NotifiableObserver();
        final PriceAdapter priceAdapter = new PriceAdapter();
        ListView listView = (ListView) findViewById(R.id.ticker_view);
        listView.setAdapter(priceAdapter);

        // Start threads
        BlockingQueue<PriceStorage> queue = new ArrayBlockingQueue<>(50);

        (Static.producerThread = new PriceInfoProducer(queue, TimeUnit.MINUTES, 1,
                URLs.COINONE_API
        )).start();
        new PriceInfoConsumer(queue,
                priceAdapter, notifiableObserver).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
