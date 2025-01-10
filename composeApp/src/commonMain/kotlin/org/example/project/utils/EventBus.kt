package org.example.project.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object EventBus {
    private val scope = CoroutineScope(Dispatchers.Default)
    private val _events = MutableSharedFlow<Any>()
    val events = _events.asSharedFlow()

    fun post(event: Any) {
        scope.launch {
            _events.emit(event)
        }
    }

    inline fun <reified T> receive(
        scope: CoroutineScope,
        dispatcher: CoroutineDispatcher? = null,
        crossinline action: (t: T) -> Unit
    ) {
        events.filterIsInstance<T>().onEach {
            if (dispatcher == null) {
                action.invoke(it)
            } else {
                withContext(dispatcher) {
                    action.invoke(it)
                }
            }
        }.launchIn(scope)
    }

}