package com.example.homework3.ui.notification

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import com.example.homework3.R
import com.example.homework3.model.NewsItem

object NotificationManager {
    const val CHANNEL = "NEWS_CHANNEL"
    var notficiationId = 1
    var PERMISSION_REQUEST_CODE = 1

    fun showNotification(context: Context, newsItem: NewsItem) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("news://show/${newsItem.id}")
        )

        val pendingIntent = TaskStackBuilder.create(context)
            .addNextIntentWithParentStack(intent)
            .getPendingIntent(notficiationId, PendingIntent.FLAG_IMMUTABLE)

        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle(newsItem.title)
            .setContentText("Tap to view details")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(notficiationId++, notificationBuilder.build())
    }


    fun createNotificationChannel(context: Context) {
        val channel =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel(
                    CHANNEL,
                    "News channel",
                    NotificationManager.IMPORTANCE_HIGH
                )
            } else {
                return
            }


        channel.description = "Here news updates are shown"

        NotificationManagerCompat.from(context)
            .createNotificationChannel(channel)
    }

    fun requestPermission(context: Context) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                // Request the notification permission
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    PERMISSION_REQUEST_CODE
                )
            }
        }
    }
}