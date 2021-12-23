package cz.crusty.common.util

import timber.log.Timber

fun logThread(tag: String) {
    Timber.d("%s isMain: %s, [%s]", tag, ThreadUtils.isMain(), Thread.currentThread())
}