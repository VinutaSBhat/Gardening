package com.example.gardening

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.gardening.R


class GalleryActivity : AppCompatActivity() {

    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private var pickedImageUri: Uri? = null
    private lateinit var popAddPost: Dialog
    private lateinit var popupPostImage: ImageView
    private lateinit var popupDescription: TextView
    private lateinit var popupClickProgress: ProgressBar
    private var imageSelected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        popAddPost = Dialog(this)
        popAddPost.setContentView(R.layout.popup_add_post)

        popupPostImage = popAddPost.findViewById(R.id.popup_img)
        popupDescription = popAddPost.findViewById(R.id.popup_description)
        popupClickProgress = popAddPost.findViewById(R.id.popup_progressBar)

        // Initialize galleryLauncher
        galleryLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    // Handle the result here
                    val data: Intent? = result.data
                    handleGalleryResult(data)
                }
            }

        // Only open the gallery if an image has not been selected before
        if (!imageSelected) {
            openGallery()
        }
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type = "image/*"
        galleryLauncher.launch(galleryIntent)
    }

    private fun handleGalleryResult(data: Intent?) {
        // Process the selected image data as needed
        if (data != null) {
            pickedImageUri = data.data
            // Perform any additional actions with the selected image URI

            // Example: Set the image in an ImageView
            popupPostImage.setImageURI(pickedImageUri)
            popupPostImage.isVisible = true
            popupClickProgress.visibility = View.VISIBLE

            setResult(Activity.RESULT_OK, Intent().apply {
                putExtra("pickedImageUri", pickedImageUri)
            })


            // Set the flag to indicate that an image has been selected
            imageSelected = true

            // Finish the activity
            finish()
        }
    }

}


