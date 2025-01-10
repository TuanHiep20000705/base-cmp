package org.example.project

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.project.event.UpdateEvent
import org.example.project.sevice.UpdateService
import org.example.project.utils.EventBus

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
        val intent = Intent(this, UpdateService::class.java)
        startService(intent)
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}