package org.example.project.ui.censor_screen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.constants.ApiStatus
import org.example.project.data.repository.NetworkRepository
import org.example.project.event.UpdateEvent
import org.example.project.utils.EventBus

class CensorViewModel(
    private val networkRepository: NetworkRepository
): ViewModel() {
    private val _onEvent = Channel<Event>(Channel.BUFFERED)
    val onEvent = _onEvent.receiveAsFlow()

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        EventBus.receive<UpdateEvent>(
            CoroutineScope(Dispatchers.Default),
            Dispatchers.Main
        ) {
            println("update!!!")
        }
    }

    fun getCensor(uncensored: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                networkRepository.getCensor(uncensored).collect { response ->
                    when(response.status) {
                        ApiStatus.LOADING -> {
                            _uiState.update { it.copy(isLoading = true) }
                        }
                        ApiStatus.SUCCESS -> {
                            _uiState.update { it.copy(isLoading = false, errorMessage = "", censoredText = response.data?.result ?: "") }
                        }
                        ApiStatus.ERROR -> {
                            _uiState.update { it.copy(isLoading = false, errorMessage = response.message) }
                        }
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = "Failed to fetch data") }
            }
        }
    }

    sealed class Event {
        data object OnLoadingSuccess : Event()
    }

    data class UiState(
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val censoredText: String = ""
    )
}