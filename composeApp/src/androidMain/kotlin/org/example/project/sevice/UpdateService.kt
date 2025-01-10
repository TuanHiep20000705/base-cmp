package org.example.project.sevice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.project.event.UpdateEvent
import org.example.project.utils.EventBus

class UpdateService: Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        CoroutineScope(Dispatchers.Main).launch {
            repeat(10) {
                delay(2000)
                EventBus.post(UpdateEvent)
            }
        }
        super.onCreate()
    }
}