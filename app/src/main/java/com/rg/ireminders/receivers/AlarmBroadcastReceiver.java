package com.rg.ireminders.receivers;

import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.rg.ireminders.utils.NotificationsUtils;
import org.dmfs.provider.tasks.broadcast.DueAlarmBroadcastHandler;

/**
 * Alarm broadcast receiver
 */
public class AlarmBroadcastReceiver extends BroadcastReceiver {
  @Override public void onReceive(Context context, Intent intent) {
    if (intent.getAction().equals(DueAlarmBroadcastHandler.BROADCAST_DUE_ALARM)) {
      Uri taskUri = intent.getData();
      String title = intent.getStringExtra(DueAlarmBroadcastHandler.EXTRA_TASK_TITLE);
      int notificationId = (int) ContentUris.parseId(taskUri);

      NotificationsUtils.showNotification(context, title, taskUri, notificationId);
    }
  }
}
