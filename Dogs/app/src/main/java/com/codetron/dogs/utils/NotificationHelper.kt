package com.codetron.dogs.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import com.codetron.dogs.R
import com.codetron.dogs.ui.MainActivity
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class NotificationHelper(private val context: Context) {

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "Dogs_channel_01"
        private const val CHANNEL_NAME = "Dogs channel"
    }

    fun sendNotification() {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val icon = BitmapFactory.decodeResource(context.resources, R.drawable.dog_icon)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.dog_icon)
            .setLargeIcon(icon)
            .setContentTitle("Dogs retrieved")
            .setContentText("This notification has some content")
            .setAutoCancel(true)
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(icon)
                    .bigLargeIcon(null)
            )

        createNotificationChannel(notificationBuilder, notificationManager)

        val notification = notificationBuilder.build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel(
        builder: NotificationCompat.Builder,
        notificationManager: NotificationManager
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            builder.setChannelId(CHANNEL_ID)

            notificationManager.createNotificationChannel(channel)
        }
    }
}