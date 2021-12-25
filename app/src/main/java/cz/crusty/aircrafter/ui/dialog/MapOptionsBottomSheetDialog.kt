package cz.crusty.aircrafter.ui.dialog

import android.content.Context
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.map_options_bottom_sheet_dialog.view.*
import kotlinx.coroutines.flow.collect
import org.koin.java.KoinJavaComponent.inject

class MapOptionsBottomSheetDialog(context: Context) : BottomSheetDialog(context) {

    public val viewModel: MapOptionsViewModel by inject(MapOptionsViewModel::class.java)
    lateinit var sheetBehavior: BottomSheetBehavior<View>

    fun setup(view: View) {

        sheetBehavior = BottomSheetBehavior.from(view);
        sheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })

        view.apply {
            header.setOnClickListener {
                if (sheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
                } else {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
                }
            }

            cluster_planes.setOnCheckedChangeListener { buttonView, isChecked ->
                viewModel.setClusterPlanes(isChecked)
            }
        }
    }
}