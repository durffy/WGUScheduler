package com.example.wguscheduler.utilities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.wguscheduler.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationReceiver extends BroadcastReceiver {
    private static int mNotificationId;
    private String mChannelId = "test";

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, intent.getStringExtra(""), Toast.LENGTH_LONG).show();
        createNotificationChannel(context,mChannelId);

        Notification n = new NotificationCompat.Builder(context, mChannelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(intent.getStringExtra(""))
                .setContentTitle("Test:"+ Integer.toString(mNotificationId)).build();

        NotificationManager notificationManager = (NotificationManager)context
                .getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(mNotificationId++, n);
    }

    private void createNotificationChannel(Context context, String mChannelId) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "notification channel";
            String description = "notification channel description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(mChannelId, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }
}
