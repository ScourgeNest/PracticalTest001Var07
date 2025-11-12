package ro.pub.cs.systems.eim.practicaltest001var07

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log

class PracticalTest01Var07Service : Service() {

    private var processingThread: ProcessingThread? = null
    class ProcessingThread(var context: Context) : Thread() {
        // putem primi date prin constructor dacă e nevoie
        var running = true
        override fun run() {
            while (running) {
                var nr1 = (0..10).random()
                var nr2 = (0..10).random()
                var nr3 = (0..10).random()
                var nr4 = (0..10).random()
                val intent = Intent().apply {
                    action = "Update"
                    putExtra("nr1",  nr1)
                    putExtra("nr2",  nr2)
                    putExtra("nr3",  nr3)
                    putExtra("nr4",  nr4)
                }
                //intent.setPackage(packageName)
                Log.d("[Started Service]", "Sending broadcast with action: ${intent.action} to package: with : ${nr1},${nr2},${nr3},${nr4}")
                context.sendBroadcast(intent)
                sleep(10000)
            }
        }

        fun stopThread() {
            running = false
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("[Started Service]", "onCreate() method was invoked")
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.d("[Started Service]", "onBind() method was invoked")
        return null
    }

    override fun onUnbind(intent: Intent): Boolean {
        Log.d("[Started Service]", "onUnbind() method was invoked")
        return true
    }

    override fun onRebind(intent: Intent) {
        Log.d("[Started Service]", "onRebind() method was invoked")
    }

    override fun onDestroy() {
        Log.d("[Started Service]", "onDestroy() method was invoked")
        processingThread?.stopThread()
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("[Started Service]", "onStartCommand() method was invoked")
        dummyNotification()

        processingThread = ProcessingThread(this)
        processingThread?.start()
        return START_REDELIVER_INTENT
    }

    private val CHANNEL_ID = "11"
    private val CHANNEL_NAME = "ForegroundServiceChannel"
    private fun dummyNotification() {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        manager.createNotificationChannel(channel)

        val notification =
            Notification.Builder(applicationContext, CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText("Service is running...")
                .setSmallIcon(android.R.drawable.ic_media_play)
                .build()

        startForeground(1, notification)
    }
}