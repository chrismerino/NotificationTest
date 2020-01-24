package tech.projekt401.notificationtest

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    val channelId = "tech.projekt401.notificationtest"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        crearCanalDeNotificacion(true, true)

        val notificationButton = findViewById<Button>(R.id.button)

        val intent = Intent(applicationContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        notificationButton.setOnClickListener {
            construirNotificacion(pendingIntent)
        }

    }


    private fun construirNotificacion(pendingIntent: PendingIntent){
        // Creando el canal de notificacion (deberia ir en una funcion aparte)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // Construyendo la notificacion para Android mayor que API26
            builder = Notification.Builder(this, channelId)
                .setContentTitle("Hola...")
                .setContentText("Has recibido un nuevo mensaje!")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

        } else {
            // Construyendo la notificacion para Android menor que API26
            builder = Notification.Builder(this)
                .setContentTitle("Hola...")
                .setContentText("Has recibido un nuevo mensaje!")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
        }
        notificationManager.notify(0, builder.build())
    }


    private fun crearCanalDeNotificacion(vibration: Boolean, mostrarBadge: Boolean){

        val channelDescription = "Este canal es para hacer pruebas de notificaciones"
        val importance = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NotificationManager.IMPORTANCE_HIGH
        } else {
            TODO("VERSION.SDK_INT < N")
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channelId, channelDescription, importance)
            notificationChannel.enableVibration(vibration)
            notificationChannel.setShowBadge(mostrarBadge)

            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

}
