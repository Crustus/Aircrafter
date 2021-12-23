package cz.crusty.common.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope

fun Fragment.flowWhenResumed(function: suspend () -> Unit) {
    viewLifecycleOwner.lifecycleScope.launchWhenResumed {
        function.invoke()
    }
}