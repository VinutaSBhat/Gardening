package com.example.gardening


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
//import com.codingstuff.instag.CommentsActivity
//import com.codingstuff.instag.Model.Post
//import com.codingstuff.instag.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*

class PostAdapter(private val context: Context, private val mList: List<Post>, private val auth: FirebaseAuth) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {


    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var likeReference: DatabaseReference? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        Log.d("Adapter", "onCreateViewHolder")
        val v = LayoutInflater.from(context).inflate(R.layout.each_post, parent, false)
        likeReference = database.getReference("images")

        return PostViewHolder(v)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        Log.d("Adapter", "onBindViewHolder: position $position")

        val post = mList[position]
        post.picture?.let { holder.setPostPic(it) }
        post.description?.let { holder.setPostCaption(post.description ?: "") }

        holder.setPostUsername()


        post.userId?.let { post.userId ?: "" }


        val postId = post.postKey // Assuming that postKey is the correct property
        val userId = auth.currentUser?.uid



        holder.likePic.setOnClickListener {
            if (userId != null) {
                val likesRef = FirebaseDatabase.getInstance().getReference("Posts/$postId/Likes")

                likesRef.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (!dataSnapshot.exists()) {
                            val likesMap = hashMapOf(
                                "timestamp" to ServerValue.TIMESTAMP
                            )
                            likesRef.child(userId).setValue(likesMap)
                            holder.likePic.setImageDrawable(context.getDrawable(R.drawable.after_liked))

                        } else {
                            likesRef.child(userId).removeValue()
                            holder.likePic.setImageDrawable(context.getDrawable(R.drawable.before_liked))

                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // Handle error
                    }
                })
            }

        }




        // Likes count
//        if (postId != null) {
//            likeReference?.child(postId)?.addValueEventListener(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                   val count = snapshot.childrenCount.toInt()
//                    Toast.makeText(context,count,Toast.LENGTH_SHORT).show()
//                    holder.setPostLikes(count)
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                     Toast.makeText(context,"database error", Toast.LENGTH_SHORT).show()
//
//                }
//            })
//        }


    }

    override fun getItemCount(): Int {

        return mList.size
    }

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var postPic: ImageView
        lateinit var commentsPic: ImageView
        lateinit var likePic: ImageView
        lateinit var postUsername: TextView
        lateinit var postDate: TextView
        lateinit var postCaption: TextView
        lateinit var postLikes: TextView
        lateinit var deleteBtn: ImageButton
        private val mView: View = itemView

        init {
            likePic = mView.findViewById(R.id.like_btn)
            commentsPic = mView.findViewById(R.id.comments_post)
//            deleteBtn = mView.findViewById(R.id.delete_btn)
        }

        @SuppressLint("SetTextI18n")
        fun setPostLikes(count: Int) {
            postLikes = mView.findViewById(R.id.like_count_tv)
            postLikes.text = "$count Likes"
        }

        fun setPostPic(urlPost: String) {
            postPic = mView.findViewById(R.id.user_post)
            Glide.with(context)
                .load(urlPost)
                // Placeholder image resource
                .error(R.drawable.error_image) // Error image resource
                .into(postPic)
            postPic.clipToOutline = true


        }

        @SuppressLint("CommitPrefEdits")
        fun setPostUsername() {
            postUsername = mView.findViewById(R.id.username_tv)


            val sharedPref = context.getSharedPreferences("addName", Context.MODE_PRIVATE)
            var edit = sharedPref?.edit()
            val getname = sharedPref?.getString("username", "default value")

            postUsername.text=getname.toString()

        }


        fun setPostCaption(caption: String) {
            postCaption = mView.findViewById(R.id.caption_tv)
            postCaption.text = caption

        }
    }
}
