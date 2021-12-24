package cz.crusty.aircrafter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import cz.crusty.aircrafter.repository.remote.model.StatesResponse
import cz.crusty.aircrafter.ui.dashboard.StatesViewModel
import cz.crusty.aircrafter.ui.dialog.MapOptionsBottomSheetDialog
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.map_options_bottom_sheet_dialog.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.timer


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private var updateTimer: Timer? = null
    private var planeBitmap: BitmapDescriptor? = null
    private lateinit var map: GoogleMap
    private lateinit var clusterManager: ClusterManager<Item>

    private val viewModel: StatesViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        floating.setOnClickListener {
            Snackbar.make(coordinator, "Hello", Snackbar.LENGTH_SHORT).show()
        }

        val dialog = MapOptionsBottomSheetDialog(this)
        dialog.setup(bottom_sheet)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel.apply {

            lifecycleScope.launch {
                states.collect {
                    //Timber.d("stateResponse: %s", it)
                    if (it != null) {
                        addPlanes(it.states.items)
                    }
                }
            }


            /*flowWhenResumed {
                pokemon.collect { value ->
                    //logThread("Pokemon ${value?.count}")
                    value?.results?.let { pokemonAdapter.add(it) }
                    setSubtitle("${pokemonAdapter.itemCount} / ${value?.count ?: "0"}")
                }
            }*/
        }
    }

    override fun onStart() {
        super.onStart()
        updateTimer = timer(period = 10_000) {
            viewModel.loadStates()
        }
    }

    override fun onStop() {
        super.onStop()
        updateTimer?.let {
            it.cancel()
            updateTimer = null
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val cze = LatLng(49.88177423198855, 15.166487457354766)
        //map.addMarker(MarkerOptions().position(cze).title("Czech republic"))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(cze, 7f))

        planeBitmap = bitmapDescriptorFromVector(this@MapsActivity, R.drawable.ic_plane_solid)

        clusterManager = ClusterManager(this, map)
        clusterManager.renderer = object : DefaultClusterRenderer<Item>(this, map, clusterManager) {
            override fun onBeforeClusterItemRendered(item: Item, markerOptions: MarkerOptions) {
                super.onBeforeClusterItemRendered(item, markerOptions)
                markerOptions.icon(planeBitmap)
                item.plane.true_track?.let { markerOptions.rotation(it - 90) }
            }
        }
        map.setOnCameraIdleListener(clusterManager)
        map.setOnMarkerClickListener(clusterManager)
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

    private fun addPlanes(items: ArrayList<StatesResponse.State>) {
        clusterManager.clearItems()
        for (plane in items) {
            if (plane.latitude == null || plane.longitude == null) {
                continue
            }

            val lat = plane.latitude
            val lon = plane.longitude
            clusterManager.addItem(
                Item(lat, lon, "Callsign  ${plane.callsign}", plane.origin_country, plane)
            )
        }
        clusterManager.cluster()
    }
}