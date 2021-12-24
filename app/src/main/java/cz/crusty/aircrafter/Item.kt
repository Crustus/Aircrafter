package cz.crusty.aircrafter

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import cz.crusty.aircrafter.repository.remote.model.StatesResponse

class Item(
    lat: Double,
    lng: Double,
    title: String,
    snippet: String,
    plane: StatesResponse.State
) : ClusterItem {

    private val position: LatLng
    private val title: String
    private val snippet: String
    val plane: StatesResponse.State


    override fun getPosition(): LatLng {
        return position
    }

    override fun getTitle(): String? {
        return title
    }

    override fun getSnippet(): String? {
        return snippet
    }

    init {
        position = LatLng(lat, lng)
        this.title = title
        this.snippet = snippet
        this.plane = plane
    }
}
