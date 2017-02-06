package com.example.mohsin.navigationsample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/*
* This service is used to handle notification when app is in foreground
* */
public class FCMService extends FirebaseMessagingService {
    public FCMService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d("FCM:", "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d("FCM:", "Message data payload: " + remoteMessage.getData().get("data"));
            Object type = remoteMessage.getData().get("type");
            Object data = remoteMessage.getData().get("data");
            if (type != null && type.equals("coordinates") && data != null) {
                String coords = (String) data;
                Intent intent = new Intent("coordinates");
                intent.putExtra("coordinates", coords);
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d("FCM:", "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }
}
