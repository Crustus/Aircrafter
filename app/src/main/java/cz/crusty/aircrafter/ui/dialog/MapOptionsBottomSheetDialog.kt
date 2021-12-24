package cz.crusty.aircrafter.ui.dialog

import android.content.Context
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.map_options_bottom_sheet_dialog.view.*

class MapOptionsBottomSheetDialog(context: Context) : BottomSheetDialog(context) {

    lateinit var sheetBehavior: BottomSheetBehavior<View>

    fun setup(view: View) {

        //setContentView(view)

        sheetBehavior = BottomSheetBehavior.from(view);
        sheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })

        view.header.setOnClickListener {
            if (sheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
            } else {
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
            }
        }
    }
}