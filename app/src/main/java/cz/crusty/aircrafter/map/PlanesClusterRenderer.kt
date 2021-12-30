package cz.crusty.aircrafter.map

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import cz.crusty.common.util.ThreadUtils
import timber.log.Timber

class PlanesClusterRenderer(markerIconRes: Int, val context: Context, map: GoogleMap, private val clusterManager: ClusterManager<Item>)
    : DefaultClusterRenderer<Item>(context, map, clusterManager) {

    private var renderAsClusters = true
    private var planeBitmap: BitmapDescriptor? = null
    private var selectedItem: Item? = null

    init {
        planeBitmap = BitmapUtils.bitmapDescriptorFromVector(context, markerIconRes)
    }


    public fun setRenderAsClusters(enable: Boolean) {
        renderAsClusters = enable
        // FIXME markers not updated after this cluster call
        ThreadUtils.runOnUi(context) {
            clusterManager.cluster()
        }
    }

    public fun setSelectedMarker(selectedItem: Item) {
        this.selectedItem = selectedItem
    }

    override fun onClusterItemRendered(clusterItem: Item, marker: Marker) {
        super.onClusterItemRendered(clusterItem, marker)
        Timber.d("onClusterItem rendered %s, selected %s", clusterItem.plane.icao24, selectedItem?.plane?.icao24)
        if (selectedItem?.plane?.icao24 == clusterItem.plane.icao24) {
            marker.showInfoWindow()
        }
    }

    override fun onBeforeClusterItemRendered(item: Item, markerOptions: MarkerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions)
        markerOptions.icon(planeBitmap)
        item.plane.true_track?.let { markerOptions.rotation(it - 90) }
    }

    override fun shouldRenderAsCluster(cluster: Cluster<Item>): Boolean {
        //Timber.d("render as Clusters %s", renderAsClusters)
        return if (renderAsClusters) super.shouldRenderAsCluster(cluster) else false
    }
}