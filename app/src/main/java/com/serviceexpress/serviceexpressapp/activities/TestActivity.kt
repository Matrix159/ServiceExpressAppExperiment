package com.serviceexpress.serviceexpressapp.activities

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.serviceexpress.serviceexpressapp.R
import android.provider.MediaStore
import android.content.Intent
import android.graphics.Bitmap
import android.widget.Toast
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import kotlinx.android.synthetic.main.activity_test.*


class TestActivity : AppCompatActivity() {
    private val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        dispatchTakePictureIntent()
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val extras = data!!.extras
            val imageBitmap = extras!!.get("data") as Bitmap
            detectBarcode(imageBitmap)
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
}
