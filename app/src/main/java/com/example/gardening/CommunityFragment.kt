package com.example.gardening

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_community, container, false)
        if (isAdded && isVisible) {
            // Fragment is attached and visible
            Toast.makeText(requireContext(), "onCreateView: Fragment is attached and visible", Toast.LENGTH_SHORT).show()
        } else {
            // Fragment is not attached or not visible
            Toast.makeText(requireContext(), "onCreateView: Fragment is not attached or not visible", Toast.LENGTH_SHORT).show()
        }
        // Example of performing a fragment transaction
//        val fragmentManager = requireActivity().supportFragmentManager
//        val transaction = fragmentManager.beginTransaction()

        // Check if the fragment already exists before replacing
//        val existingFragment = fragmentManager.findFragmentByTag("CommunityFragment")
//        if (existingFragment == null) {
//            transaction.replace(R.id.frame_layout, CommunityFragment(), "CommunityFragment")
//            transaction.addToBackStack(null)
//            transaction.commit()
//            Toast.makeText(requireContext(), "commited trans", Toast.LENGTH_SHORT).show()
//        }

            mRecyclerView = view.findViewById(R.id.recyclerView)
            mRecyclerView.setHasFixedSize(true)
            mRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            if (adapter == null) {
            list = mutableListOf()
            Toast.makeText(requireContext(), "sending to adapter", Toast.LENGTH_SHORT).show()
            adapter = PostAdapter(requireContext(), list!!, FirebaseAuth.getInstance())
            mRecyclerView.adapter = adapter
            Toast.makeText(requireContext(), "after adapter", Toast.LENGTH_SHORT).show()}

            mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val isBottom = !mRecyclerView.canScrollVertically(1)
                    if (isBottom) {
                        Toast.makeText(requireContext(), "Reached Bottom", Toast.LENGTH_SHORT)
                            .show()
                    }
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
                            Toast.makeText(requireContext(), "on datachange"+ list?.size, Toast.LENGTH_SHORT)
                                .show()
                            Toast.makeText(requireContext(), "on datachange"+ (list?.get(1)  ), Toast.LENGTH_SHORT)
                                .show()
                            adapter?.notifyDataSetChanged()
                            Toast.makeText(requireContext(), "on datachange after", Toast.LENGTH_SHORT)
                                .show()

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












