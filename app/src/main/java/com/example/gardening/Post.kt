package com.example.gardening

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.ServerValue

class Post : AppCompatActivity() {

//    private lateinit var description:String
//    private lateinit var picture:String
//    private lateinit var userId:String
//    private lateinit var timeStamp: Any
//
//    class Post(description:String, picture:String, userId:String){
//        this.description=description
//        this.picture=picture
//        this.userId=userId
//        this.timeStamp=ServerValue.TIMESTAMP
//    }





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
    }
}