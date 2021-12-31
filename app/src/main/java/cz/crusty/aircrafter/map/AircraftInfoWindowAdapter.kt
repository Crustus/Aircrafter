package cz.crusty.aircrafter.map

import android.view.LayoutInflater
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import cz.crusty.aircrafter.R
import cz.crusty.aircrafter.repository.remote.model.StatesResponse
import cz.crusty.common.util.CountryUtils
import kotlinx.android.synthetic.main.map_marker_info_content.view.*
import timber.log.Timber


class AircraftInfoWindowAdapter(val inflater: LayoutInflater) : GoogleMap.InfoWindowAdapter {

    override fun getInfoContents(marker: Marker): View? {
        Timber.d("getInfoContent() %s", marker.tag)
        val item: Item? = marker.tag as Item
        val view = inflater.inflate(R.layout.map_marker_info_content, null, false)

        if (item?.plane != null) {
            val plane: StatesResponse.State = item.plane

            view.call_sign.text = "${plane.callsign?.trim() ?: ""} (${plane.icao24})"
            view.country.text = CountryUtils.getFlag(plane.origin_country)
            view.lat_lon.text = "${plane.latitude}, ${plane.longitude}"
            view.altitude.text = "${plane.baro_altitude} m"
            view.velocity.text = "${plane.velocity} m/s"
            val rate: Int = plane.vertical_rate?.compareTo(0f) ?: 0
            val verticalRateText = when {
                rate > 0 -> "Climbing"
                rate < 0 -> "Descending"
                else -> ""
            }
            view.vertical_rate.text = "${plane.vertical_rate} m/s $verticalRateText"
        }

        return view
    }

    override fun getInfoWindow(p0: Marker): View? {
        return null
    }

}
