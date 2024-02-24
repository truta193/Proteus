package com.truta.proteus_android.ui.screen

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class AppViewModel : ViewModel() {
    fun launchCatching(
        block: suspend CoroutineScope.() -> Unit,
        onError: ((Throwable) -> Unit)? = null
    ) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                Log.d("Proteus Error", throwable.message.orEmpty())
                onError?.invoke(throwable)
            },
            block = block
        )
}