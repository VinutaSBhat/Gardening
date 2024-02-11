package com.example.gardening

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView


class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var logout: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = context?.getSharedPreferences("addName", Context.MODE_PRIVATE)
        var edit = sharedPref?.edit()
        val getname = sharedPref?.getString("username", "default value")
        val text=view.findViewById<TextView>(R.id.userName)

        text.text=getname.toString()

        logout = view.findViewById(R.id.logout)

        logout.setOnClickListener {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }
    }



}