package sn0ww01f.project.foodie

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // Handle notification
        Toast.makeText(context, "New Recipe Notification!", Toast.LENGTH_LONG).show()
    }
}
