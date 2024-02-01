@file:Suppress("DEPRECATION")

package com.example.gardening

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.app.appsearch.SetSchemaRequest.READ_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.IOException


class Home : AppCompatActivity() {


    //inipopup
    lateinit var popAddPost: Dialog
    private lateinit var popupPostImage: ImageView
    private  var popupaddimgbtn: ImageView ? =null

    private lateinit var popupAddBtn: Button
    private lateinit var popupDescription: TextView
    private lateinit var popupClickProgress: ProgressBar

    //add image
    private lateinit var addPhoto: ImageView
    private val requestCodeGallery = 123
    private val pReqCode = 11
     private var pickedImageUri:Uri?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            popAddPost.show()
        }


        //ini popup
        inipopup()
        setupPopupImageClick()
    }




    @SuppressLint("WrongViewCast")
    fun inipopup() {
        popAddPost = Dialog(this)
        popAddPost.setContentView(R.layout.popup_add_post)
        popAddPost.window!!.setLayout(
            Toolbar.LayoutParams.MATCH_PARENT,
            Toolbar.LayoutParams.MATCH_PARENT
        )
        popAddPost.window!!.attributes.gravity = Gravity.TOP
        val close = popAddPost.findViewById(R.id.popup_cross) as ImageView
        close.setOnClickListener {
            popAddPost.dismiss()
        }

        //ini pop widgets
        popupPostImage = popAddPost.findViewById(R.id.popup_img)
        popupDescription = popAddPost.findViewById(R.id.popup_description)
        popupAddBtn = popAddPost.findViewById(R.id.popup_add)
        popupClickProgress = popAddPost.findViewById(R.id.popup_progressBar)

        //add post click listener

        popupAddBtn.setOnClickListener {
            popupAddBtn.visibility = View.INVISIBLE
            popupClickProgress.visibility = View.VISIBLE

        }


    }






    //add image from gallery
    private fun setupPopupImageClick() {
        addPhoto = popAddPost.findViewById(R.id.popup_addimage)
        addPhoto.setOnClickListener {
            if (VERSION.SDK_INT >= 22) {
                checkAndRequestForPermission()
                openGallery()
            } else {


                // Call openGallery function
                openGallery()
            }

        }
//        popupaddimgbtn?.setOnClickListener{
//            checkAndRequestForPermission()
//        }
    }





     @Suppress("DEPRECATION")
     private fun checkAndRequestForPermission() {
         if (ContextCompat.checkSelfPermission(
                 this@Home,
                 android.Manifest.permission.READ_EXTERNAL_STORAGE
             ) != PackageManager.PERMISSION_GRANTED

         ) {
             if (ActivityCompat.shouldShowRequestPermissionRationale(
                     this@Home,
                     android.Manifest.permission.READ_EXTERNAL_STORAGE
                 )
             ) {

                 Toast.makeText(
                     this@Home,
                     "Please accept for required permission",
                     Toast.LENGTH_SHORT
                 ).show()
             } else {
                 ActivityCompat.requestPermissions(
                     this@Home,
                     arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), pReqCode
                 )
             }
         } else {
             Toast.makeText(this@Home,"permission already granted",Toast.LENGTH_SHORT).show()
             openGallery()
         }
     }

     private fun openGallery() {
         val galleryIntent = Intent(this@Home, GalleryActivity::class.java)
         startActivityForResult(galleryIntent, requestCodeGallery)
     }

     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
         Toast.makeText(this@Home, "inside onactivity result", Toast.LENGTH_SHORT).show()
         super.onActivityResult(requestCode, resultCode, data)
         if (resultCode == RESULT_OK && requestCode == requestCodeGallery && data != null) {
             try {
                 pickedImageUri = data.getParcelableExtra("pickedImageUri")
                 val imageView: ImageView = popAddPost.findViewById(R.id.popup_img)
                 imageView.setImageURI(pickedImageUri)
                 imageView.visibility = View.VISIBLE
             } catch (e: IOException) {
                 e.printStackTrace()
                 Toast.makeText(this@Home, "Error loading image", Toast.LENGTH_SHORT).show()
             }
         }
     }
 }






//    class Gallery : AppCompatActivity() {
//
//        private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
//        private val requesCode = 2
//        private var pickedImageUri:Uri?=null
//        private lateinit var popupPostImage: ImageView
//        lateinit var popAddPost: Dialog
//
//
//        override fun onCreate(savedInstanceState: Bundle?) {
//            super.onCreate(savedInstanceState)
//            popupPostImage = popAddPost.findViewById(R.id.popup_img)
//            galleryLauncher =
//                registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//                    if (result.resultCode == RESULT_OK) {
//                        // Handle the result here
//                        val data: Intent? = result.data
//                        handleGalleryResult(data)
//                    }
//                }
//
//            // Call openGallery function from the GalleryActivity
//            openGallery()
//        }
//
//        fun openGallery() {
//            val galleryIntent = Intent(Intent.ACTION_PICK)
//            galleryIntent.type = "image/*"
//            galleryLauncher.launch(galleryIntent)
//        }
//
//        private fun handleGalleryResult(data: Intent?) {
//            // Process the selected image data as needed
//            if (data != null) {
//                pickedImageUri = data.data!!
//                // Perform any additional actions with the selected image URI
//
//                // Example: Set the image in an ImageView
//                popupPostImage.setImageURI(pickedImageUri)
//                popupPostImage.isVisible = true
//            }
//        }
//    }







