package org.example.project.ui.censor_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.project.utils.collectInLaunchedEffect
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun CensorScreen() {
    val viewModel = koinViewModel<CensorViewModel>()

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    val isLoading by remember {
        derivedStateOf {
            uiState.value.isLoading
        }
    }

    var uncensoredText by remember {
        mutableStateOf("")
    }

    val censoredText by remember {
        derivedStateOf {
            uiState.value.censoredText
        }
    }

    val errorMessage by remember {
        derivedStateOf {
            uiState.value.errorMessage
        }
    }

    viewModel.onEvent.collectInLaunchedEffect { event ->
        when(event) {
            is CensorViewModel.Event.OnLoadingSuccess -> {
                println("isLoading success")
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        TextField(
            value = uncensoredText,
            onValueChange = { uncensoredText = it },
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            placeholder = {
                Text("Uncensored text")
            }
        )
        Button(onClick = {
            viewModel.getCensor(uncensoredText)
        }) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(15.dp),
                    strokeWidth = 1.dp,
                    color = Color.White
                )
            } else {
                Text("Censor!")
            }
        }
        Text(censoredText)
        errorMessage?.let {
            Text(
                text = it,
                color = Color.Red
            )
        }
    }
}