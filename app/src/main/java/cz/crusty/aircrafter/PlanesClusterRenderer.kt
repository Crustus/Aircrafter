package cz.crusty.aircrafter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import timber.log.Timber

class PlanesClusterRenderer(markerIconRes: Int, context: Context, map: GoogleMap, private val clusterManager: ClusterManager<Item>)
    : DefaultClusterRenderer<Item>(context, map, clusterManager) {

    private var renderAsClusters = true
    private var planeBitmap: BitmapDescriptor? = null

    init {
        planeBitmap = bitmapDescriptorFromVector(context, markerIconRes)
    }


    public fun setRenderAsClusters(enable: Boolean) {
        renderAsClusters = enable
        clusterManager.cluster()
    }

    override fun onBeforeClusterItemRendered(item: Item, markerOptions: MarkerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions)
        markerOptions.icon(planeBitmap)
        item.plane.true_track?.let { markerOptions.rotation(it - 90) }
    }

    override fun shouldRenderAsCluster(cluster: Cluster<Item>): Boolean {
        Timber.d("render as Clusters %s", renderAsClusters)
        return if (renderAsClusters) super.shouldRenderAsCluster(cluster) else false
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap = Bitmap.createBitmap(
                    intrinsicWidth,
                    intrinsicHeight,
                    Bitmap.Config.ARGB_8888
            )
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }
}