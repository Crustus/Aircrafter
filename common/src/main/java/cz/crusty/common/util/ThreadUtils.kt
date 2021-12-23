package cz.crusty.common.util

import android.os.Looper

class ThreadUtils {

    companion object {
        fun isMain(): Boolean = Looper.myLooper() == Looper.getMainLooper()
    }

}