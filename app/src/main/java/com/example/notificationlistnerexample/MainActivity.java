package com.example.notificationlistnerexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView txtView;
    private NotificationReceiver nReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtView = (TextView) findViewById(R.id.textView);
        nReceiver = new NotificationReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.kpbird.notificationlistnerexample.NOTIFICATION_LISTENER_EXAMPLE");
        registerReceiver(nReceiver, filter);

        //Ask for Allow Notification Access Setting Screen.
        Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(nReceiver);
    }

    public void buttonClicked(View v) {

        if (v.getId() == R.id.btnCreateNotify) {
            NotificationManager nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationCompat.Builder ncomp = new NotificationCompat.Builder(this);
            ncomp.setContentTitle("My Notification");
            ncomp.setContentText("Notification Listener Service Example");
            ncomp.setTicker("Notification Listener Service Example");
            ncomp.setSmallIcon(R.drawable.ic_launcher);
            ncomp.setAutoCancel(true);
            nManager.notify((int) System.currentTimeMillis(), ncomp.build());
        } else if (v.getId() == R.id.btnClearNotify) {
            Intent i = new Intent("com.kpbird.notificationlistnerexample.NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
            i.putExtra("command", "clearall");
            sendBroadcast(i);
        } else if (v.getId() == R.id.btnListNotify) {
            Intent i = new Intent("com.kpbird.notificationlistnerexample.NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
            i.putExtra("command", "list");
            sendBroadcast(i);
        }

    }

    class NotificationReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String temp = intent.getStringExtra("notification_event") + "n" + txtView.getText();
            Log.e(" ", temp);
            txtView.setText(temp);
        }
    }
}