package cz.crusty.common.util

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import timber.log.Timber

class CameraUtils {

    class TakePicture(val registry: ActivityResultRegistry) {

        lateinit var getContent : ActivityResultLauncher<String>

        fun onCreate(owner: LifecycleOwner) {
            Timber.d("onCreate:")
            getContent = registry.register("key", owner, ActivityResultContracts.GetContent()) { uri ->
                // Handle the returned Uri
                Timber.d("returned: %s", uri)
            }

            selectImage()
        }

        private fun selectImage() {
            getContent.launch("image/*")
        }
    }

    companion object {
        fun takePicture(registry: ActivityResultRegistry, lifecycle: Lifecycle) {


            /*
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                registry.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            } catch (e: ActivityNotFoundException) {
                // display error state to the user
            }*/
        }
    }
}