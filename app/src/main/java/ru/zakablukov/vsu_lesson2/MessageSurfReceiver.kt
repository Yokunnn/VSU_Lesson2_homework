package ru.zakablukov.vsu_lesson2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class MessageSurfReceiver : BroadcastReceiver() {

    var broadcastMessage: String? = null

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "ru.shalkoff.vsu_lesson2_2024.SURF_ACTION") {
            val message = intent.getStringExtra("message")
            Toast.makeText(
                context,
                message,
                Toast.LENGTH_SHORT
            ).show()
            broadcastMessage = message
            Log.d("Message", message ?: "no msg")
        }
    }
}