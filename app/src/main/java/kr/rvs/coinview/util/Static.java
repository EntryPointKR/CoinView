package kr.rvs.coinview.util;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;

import kr.rvs.coinview.action.PriceInfoProducer;
import kr.rvs.coinview.collection.PriceStorageLinkedList;

/**
 * Created by Junhyeong Lim on 2017-05-19.
 */
public class Static {
    public static Activity activity;
    public static PriceInfoProducer producerThread;
    public static View view;
    public static PriceStorageLinkedList priceList = new PriceStorageLinkedList();

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,##0");

    public static String getContentByURL(URL url) throws IOException {
        URLConnection conn = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        StringBuilder all = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            if (all.length() > 0) {
                all.append("\n");
            }
            all.append(line);
        }

        return all.toString();
    }

    public static void permissionRequest() {
        final String perm = Manifest.permission.INTERNET;
        int permCheck = ContextCompat.checkSelfPermission(activity, perm);
        if (permCheck == PackageManager.PERMISSION_DENIED) {
            getAlertDialogBuilder().setTitle("권한 요청")
                    .setMessage("CoinView 는 코인 시세 확인을 위해 인터넷 연결 권한이 필요합니다.")
                    .setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            Toast.makeText(activity, "gimoddi", Toast.LENGTH_SHORT).show();
                            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.INTERNET}, 1);
                        }
                    })
                    .setPositiveButton("확인", null)
                    .show();
        }
//        else {
//            Toast.makeText(activity, "권한 확인", Toast.LENGTH_SHORT).show();
//        }
    }

    public static AlertDialog.Builder getAlertDialogBuilder() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return new AlertDialog.Builder(activity, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            return new AlertDialog.Builder(activity);
        }
    }

    public static void makeToast(final CharSequence text, final boolean isLong) {
        Runnable toastRunnable = new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, text, isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
            }
        };

        if (Looper.myLooper() == Looper.getMainLooper()) {
            toastRunnable.run();
        } else {
            activity.runOnUiThread(toastRunnable);
        }
    }

    public static void makeSnackbar(String str, int duration) {
        Snackbar.make(view, str, duration).show();
    }

    public static void makeSnackbar(String str) {
        makeSnackbar(str, Snackbar.LENGTH_LONG);
    }

    public static URL getURL(String url) {
        URL ret = null;
        try {
            ret = new URL(url);
        } catch (Throwable th) {
            // Ignore
        }

        return ret;
    }

    public static void doNotify(Notification notification) {
        NotificationManager manager = (NotificationManager) Static.activity.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, notification);
    }

    public static NotificationCompat.Builder getNotificationCompatBuilder() {
        return new NotificationCompat.Builder(activity);
    }

    public static String getString(int resId, Object... args) {
        return args.length > 0
                ? Static.activity.getString(resId, args)
                : Static.activity.getString(resId);
    }

    public static void ex(Throwable throwable) {
        Log.d(activity.getApplicationInfo().loadLabel(activity.getPackageManager()).toString(),
                "[!] Error occurs: " + throwable.toString());
    }

    public static boolean isMobileDataEnabled() {
        return Settings.Secure.getInt(Static.activity.getContentResolver(), "mobile_data", 1) == 1;
    }

    public static String numberCommaFormat(Integer number) {
        return DECIMAL_FORMAT.format(number);
    }
}
