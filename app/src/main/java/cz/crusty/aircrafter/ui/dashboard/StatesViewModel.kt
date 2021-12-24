package cz.crusty.aircrafter.ui.dashboard

import cz.crusty.aircrafter.base.BaseViewModel
import cz.crusty.aircrafter.repository.remote.API
import cz.crusty.aircrafter.repository.remote.model.StatesResponse
import cz.crusty.common.util.logThread
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class StatesViewModel(val api: API) : BaseViewModel() {

    private val _states: MutableStateFlow<StatesResponse?> = MutableStateFlow(null)
    val states: StateFlow<StatesResponse?> = _states


    init {
        loadStates()
    }

    public fun loadStates() {
        call(
            onRequest = {
                api.getPlanesOverCZE()
            },
            onSuccess = {
                logThread("onSuccess $it")
                _states.value = it
            },
            onError = {
                logThread("onError $it")
            }
        )
    }

}