package cz.crusty.aircrafter.base

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    protected fun setTitle(title: String) {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = title
    }

    protected fun setSubtitle(subtitle: String) {
        (requireActivity() as AppCompatActivity).supportActionBar?.subtitle = subtitle
    }

    companion object {

    }
}