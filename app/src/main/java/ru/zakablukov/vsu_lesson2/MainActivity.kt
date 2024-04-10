package ru.zakablukov.vsu_lesson2

import android.content.IntentFilter
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private val messageSurfReceiver = MessageSurfReceiver()
    private var secretKey: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val filter = IntentFilter("ru.shalkoff.vsu_lesson2_2024.SURF_ACTION")
        registerReceiver(messageSurfReceiver, filter, RECEIVER_EXPORTED)

        initFetchSecretKey()
    }

    override fun onDestroy() {
        unregisterReceiver(messageSurfReceiver)
        super.onDestroy()
    }

    private fun initFetchSecretKey() {
        val shareSecretKeyContentResolver = ShareSecretKeyContentResolver(contentResolver)
        val fetchSecretKeyBtn = findViewById<Button>(R.id.fetchSecretKeyBtn)
        fetchSecretKeyBtn.setOnClickListener {
            val msg = shareSecretKeyContentResolver.fetchSecretKey()
            Toast.makeText(
                this,
                msg,
                Toast.LENGTH_LONG
            ).show()
            secretKey = msg
            Log.d("Shared secret key", msg)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("secret_key", secretKey)
        outState.putString("broadcast_msg", messageSurfReceiver.broadcastMessage)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val key = savedInstanceState.getString("secret_key")
        secretKey = key
        val msg = savedInstanceState.getString("broadcast_msg")
        messageSurfReceiver.broadcastMessage = msg
        Log.i("Restored secret key", key ?: "Key not received")
        Log.i("Restored broadcast msg", msg ?: "Message not received")
    }
}