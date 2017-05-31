package kr.rvs.coinview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.CompoundButton;
import android.widget.Switch;

import kr.rvs.coinview.R;
import kr.rvs.coinview.util.Settings;

/**
 * Created by Junhyeong Lim on 2017-06-01.
 */

public class SettingsActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        init();
    }

    private void init() {
        Switch mobileNetworkSwitch = (Switch) findViewById(R.id.mobileNetworkAutoRefreshSwitch);
        mobileNetworkSwitch.setChecked(Settings.canRefreshIfMobileNetworkIsOn);
        mobileNetworkSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings.canRefreshIfMobileNetworkIsOn = isChecked;
            }
        });
    }
}
