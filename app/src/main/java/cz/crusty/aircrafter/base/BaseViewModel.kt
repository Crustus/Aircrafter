package cz.crusty.aircrafter.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import timber.log.Timber

open class BaseViewModel: ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    protected fun <T> call(onRequest: suspend () -> Response<T>, onSuccess: (T?) -> Unit, onError: (String) -> Unit) {
        _loading.value = true
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val response = onRequest.invoke()
                    if (response.isSuccessful) {
                        onSuccess(response.body())
                    }
                    else {
                        onError(response.toString())
                    }
                }
                catch (t: Throwable) {
                    Timber.e(t)
                    onError(t.toString())
                }
                finally {
                    _loading.value = false
                }
            }
        }
    }

}
