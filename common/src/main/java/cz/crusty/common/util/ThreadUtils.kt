package cz.crusty.common.util

import android.content.Context
import android.os.Looper
import androidx.core.content.ContextCompat

object ThreadUtils {

    fun isMain(): Boolean = Looper.myLooper() == Looper.getMainLooper()

    fun runOnUi(context: Context, function: () -> Unit) {
        ContextCompat.getMainExecutor(context).execute(function)
    }
}