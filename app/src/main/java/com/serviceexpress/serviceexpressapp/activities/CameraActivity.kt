package com.serviceexpress.serviceexpressapp.activities

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.serviceexpress.serviceexpressapp.R
import kotlinx.android.synthetic.main.activity_camera.*


class CameraActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        // Access the companion object as it's statically bound to the interface
        //SEIService.INSTANCE.listRepos("TestString")

        // Show an example using RxJava to show how it's easier with callbacks that way
    }

    fun shootCamera(v: View) {
        camera.captureImage { cameraKitImage ->
            // Get the Bitmap from the captured shot
            detectBarcode(cameraKitImage.bitmap)
        }
    }

    private fun detectBarcode(bitmap: Bitmap) {
        val options = FirebaseVisionBarcodeDetectorOptions.Builder()
                .setBarcodeFormats(FirebaseVisionBarcode.FORMAT_ALL_FORMATS)
                .build()
        val detector = FirebaseVision.getInstance().getVisionBarcodeDetector(options)
        val image = FirebaseVisionImage.fromBitmap(bitmap)
        detector.detectInImage(image)
                .addOnSuccessListener {
                    if (it.isEmpty()) {
                        barcode.text = "No barcode found"
                        return@addOnSuccessListener
                    }
                    for (firebaseBarcode in it) {
                        barcode.text = firebaseBarcode.displayValue //Display contents inside the barcode
                        //sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                    }
                }
                .addOnFailureListener {
                    it.printStackTrace()
                    Toast.makeText(baseContext, "Sorry, something went wrong!", Toast.LENGTH_SHORT).show()
                }
    }

    override fun onResume() {
        super.onResume()
        camera.start()
    }

    override fun onPause() {
        camera.stop()
        super.onPause()
    }
}
