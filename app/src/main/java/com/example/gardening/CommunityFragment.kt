package com.example.gardening

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class CommunityFragment : Fragment() {

    private lateinit var mRecyclerView: RecyclerView
    private var adapter: PostAdapter? = null
    private var list: MutableList<Post>? = null
    private var valueEventListener: ValueEventListener? = null


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_community, container, false)
            val progress=view.findViewById<ProgressBar>(R.id.progress)
    val progressbar=view.findViewById<ProgressBar>(R.id.progress)
            mRecyclerView = view.findViewById(R.id.recyclerView)
            mRecyclerView.setHasFixedSize(true)
            mRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            if (adapter == null) {
            list = mutableListOf()

            adapter = PostAdapter(requireContext(), list!!, FirebaseAuth.getInstance())

            mRecyclerView.adapter = adapter
          }

            mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val isBottom = !mRecyclerView.canScrollVertically(1)

                }
            })

            val databaseReference = FirebaseDatabase.getInstance().getReference("images")
            requireActivity().runOnUiThread {
                valueEventListener =
                    databaseReference.addValueEventListener(object : ValueEventListener {
                        @SuppressLint("NotifyDataSetChanged")

                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            list?.clear()

                            for (postSnapshot in dataSnapshot.children) {
                                val postId = postSnapshot.key
                                val post =
                                    postSnapshot.getValue(Post::class.java)?.withId<Post>(postId!!)
                                if (post != null) {
                                    list?.add(post)
                                }
                            }

                            adapter?.notifyDataSetChanged()
                            progress.visibility=View.INVISIBLE

            progressbar.visibility=View.INVISIBLE
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            Toast.makeText(
                                requireContext(),
                                databaseError.message,
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }

                    })
            }



        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        valueEventListener?.let {
            FirebaseDatabase.getInstance().getReference("images").removeEventListener(it)
        }
    }
}












