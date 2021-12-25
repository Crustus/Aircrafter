package cz.crusty.aircrafter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import com.google.maps.android.clustering.ClusterManager
import cz.crusty.aircrafter.repository.remote.model.StatesResponse
import cz.crusty.aircrafter.ui.dashboard.StatesViewModel
import cz.crusty.aircrafter.ui.dialog.MapOptionsBottomSheetDialog
import cz.crusty.aircrafter.ui.dialog.MapOptionsViewModel
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.map_options_bottom_sheet_dialog.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.util.*
import kotlin.concurrent.timer


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private var clusterRenderer: PlanesClusterRenderer? = null
    private lateinit var optionsViewModel: MapOptionsViewModel
    private var updateTimer: Timer? = null
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

        optionsViewModel = dialog.viewModel

        optionsViewModel.apply {
            lifecycleScope.launchWhenStarted {
                clusterPlanes.collect {
                    Timber.d("clusterPlanes %s", it)
                    clusterRenderer?.setRenderAsClusters(it)
                }
            }
        }

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
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(cze, 7f))

        clusterManager = ClusterManager(this, map)
        clusterRenderer = PlanesClusterRenderer(R.drawable.ic_plane_solid, this, map, clusterManager)
        clusterManager.renderer = clusterRenderer
        map.setOnCameraIdleListener(clusterManager)
        map.setOnMarkerClickListener(clusterManager)
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