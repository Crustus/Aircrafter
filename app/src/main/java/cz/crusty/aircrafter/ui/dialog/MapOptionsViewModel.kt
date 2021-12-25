package cz.crusty.aircrafter.ui.dialog

import cz.crusty.aircrafter.base.BaseViewModel
import cz.crusty.aircrafter.repository.remote.API
import cz.crusty.aircrafter.repository.remote.model.StatesResponse
import cz.crusty.common.util.logThread
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MapOptionsViewModel() : BaseViewModel() {

    private val _clusterPlanes: MutableStateFlow<Boolean> = MutableStateFlow(true)

    val clusterPlanes: StateFlow<Boolean> = _clusterPlanes
    init {

    }

    fun setClusterPlanes(checked: Boolean) {
        _clusterPlanes.value = checked
    }
}
