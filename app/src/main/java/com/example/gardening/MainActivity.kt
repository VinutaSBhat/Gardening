package com.example.gardening

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

import android.Manifest
import android.Manifest.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gardening.databinding.ActivityMainBinding


import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException
import java.util.*
import com.example.gardening.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

//    private lateinit var userName: TextView

//    private lateinit var gClient: GoogleSignInClient
//    private lateinit var gOptions: GoogleSignInOptions
private lateinit var binding: ActivityMainBinding

    //add image
    private lateinit var addPhoto: ImageView
    private val requestCodeGallery = 123
    private val pReqCode = 11
    private var pickedImageUri: Uri? = null

    //retrive posts
//    private lateinit var mRecyclerView: RecyclerView
//    private var adapter: PostAdapter? = null
//    private var list: List<Post>? = null
//    private var query: Query? = null
//    private var listenerRegistration: ListenerRegistration? = null



    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        replaceFragment(HomeFragment())
        binding.bottomNavigationView.background = null

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.cart -> replaceFragment(CartFragment())
                R.id.community -> replaceFragment(communityFragment())
                R.id.profile -> replaceFragment(ProfileFragment())

            }
            true
        }
        binding.post.setOnClickListener{
            val intent = Intent(this@MainActivity, Post::class.java)
            startActivity(intent)

        }


//        userName = findViewById(R.id.userName)

//        gOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
//        gClient = GoogleSignIn.getClient(this, gOptions)

//        val gAccount: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)
//        if (gAccount != null) {
//            val gName = gAccount.displayName
//            userName.text = gName
//        }
        val button = findViewById<FloatingActionButton>(R.id.post)
        button.setOnClickListener {
            popAddPost.show()
        }





        //inipopup
        inipopup()
        setupPopupImageClick()


    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
    }


    //initial popup
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

            if (popupDescription.text.toString().isNotEmpty() && pickedImageUri != null) {
                val currentUser = FirebaseAuth.getInstance().currentUser

                if (pickedImageUri != null) {
                    val storageReference =
                        FirebaseStorage.getInstance().reference.child("posts")
                    val imageFileName =
                        UUID.randomUUID().toString() // Unique identifier for the image
                    val imageFilePath: StorageReference = storageReference.child(imageFileName)

                    imageFilePath.putFile(pickedImageUri!!)
                        .addOnSuccessListener {
                            imageFilePath.downloadUrl
                                .addOnSuccessListener { uri ->
                                    val imageDownloadLink = uri.toString()

                                    val post = currentUser?.let { user ->
                                        PostClass(
                                            null,
                                            popupDescription.text.toString(),
                                            imageDownloadLink,
                                            user.uid
                                        )
                                    }
                                    post?.let { addPost(it) }
                                }
                                .addOnFailureListener { e ->
                                    showMessage("Error getting image download URL: ${e.message}")
                                    hideProgressAndShowButton()
                                }
                        }
                        .addOnFailureListener { e ->
                            showMessage("Error uploading image: ${e.message}")
                            hideProgressAndShowButton()
                        }
                } else {
                    showMessage("Please verify all input fields")
                    hideProgressAndShowButton()
                }
            }
        }
    }

        private fun hideProgressAndShowButton() {
            popupClickProgress.visibility = View.INVISIBLE
            popupAddBtn.visibility = View.VISIBLE
        }

        private fun addPost(post: PostClass) {
            val database = FirebaseDatabase.getInstance()
            val myRef: DatabaseReference = database.getReference("images").push()

            // Get post unique ID and update post key
            val key = myRef.key
            if (key != null) {
                post.run { updatePostKey(key = key) }
            }

            // Add post data to Firebase database
            myRef.setValue(post)
                .addOnSuccessListener {
                    showMessage("Post Added successfully")
                    popupClickProgress.visibility = View.INVISIBLE
                    popupAddBtn.visibility = View.VISIBLE
                    popAddPost.dismiss()
                }
                .addOnFailureListener { e ->
                    showMessage("Error adding post: ${e.message}")
                    popupClickProgress.visibility = View.INVISIBLE
                    popupAddBtn.visibility = View.VISIBLE
                }
        }

        private fun showMessage(message: String) {
            Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
        }


        //add image from gallery
    @RequiresApi(Build.VERSION_CODES.R)
    private fun setupPopupImageClick() {
        addPhoto = popAddPost.findViewById(R.id.popup_addimage)
        addPhoto.setOnClickListener {
            if (Build.VERSION.SDK_INT >= 22) {
                checkAndRequestForPermission()
                openGallery()
            } else {


                // Call openGallery function
                openGallery()
            }

        }
    }


    @RequiresApi(Build.VERSION_CODES.R)
    @Suppress("DEPRECATION")
    private fun checkAndRequestForPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.READ_MEDIA_IMAGES
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this@MainActivity,
                        Manifest.permission.READ_MEDIA_IMAGES
                    )
                ) {

                    Toast.makeText(
                        this@MainActivity,
                        "Please accept for required permission",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        ActivityCompat.requestPermissions(
                            this@MainActivity,
                            arrayOf(Manifest.permission.READ_MEDIA_IMAGES), pReqCode
                        )
                    }
                }
            } else {
                Toast.makeText(
                    this@MainActivity,
                    "permission already granted",
                    Toast.LENGTH_SHORT
                ).show()
                this.openGallery()
            }
        } else {
            // Handle the case for devices running below Android 13
            // You might choose to use the old READ_EXTERNAL_STORAGE permission here
            // or handle it differently based on your app's requirements.
            // For simplicity, the code below shows a toast message.
            Toast.makeText(
                this@MainActivity,
                "App does not support versions below Android 13",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    private fun openGallery() {

        val galleryIntent = Intent(this@MainActivity, GalleryActivity::class.java)
        startActivityForResult(galleryIntent, requestCodeGallery)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == requestCodeGallery && data != null) {
            pickedImageUri = data.getParcelableExtra<Uri>("pickedImageUri")
            // Use the pickedImageUri as needed
            try {
                val imageView: ImageView = popAddPost.findViewById(R.id.popup_img)
                imageView.setImageURI(pickedImageUri)
                imageView.visibility = View.VISIBLE
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this@MainActivity, "Error loading image", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }







}


}


