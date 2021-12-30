package cz.crusty.aircrafter.map

import android.view.LayoutInflater
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import cz.crusty.aircrafter.R

class AircraftInfoWindowAdapter(val inflater: LayoutInflater) : GoogleMap.InfoWindowAdapter {

    override fun getInfoContents(marker: Marker): View? {
        //val item: Item? = marker.tag as Item
        val view = inflater.inflate(R.layout.map_marker_info_content, null, false)
        return view
    }

    override fun getInfoWindow(p0: Marker): View? {
        return null
    }

}
