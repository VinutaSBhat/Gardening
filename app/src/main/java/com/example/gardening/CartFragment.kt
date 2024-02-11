package com.example.gardening

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class CartFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var recyclerView: RecyclerView
    private lateinit var androidData: cartmodel
    private lateinit var adapter: Cartadapter
    private lateinit var dataList: MutableList<cartmodel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        recyclerView = view.findViewById(R.id.cartfr)

        val gridLayoutManager = GridLayoutManager(requireContext(), 1)
        recyclerView.layoutManager = gridLayoutManager

        dataList = mutableListOf()

        var androidData = cartmodel( R.drawable.bonsai,"Bonsai", "2", "250")
        dataList.add(androidData)

        androidData = cartmodel(R.drawable.bonsai,"Bons", "2", "250")
        dataList.add(androidData)

        androidData = cartmodel(R.drawable.bonsai,"Bo", "2", "250")
        dataList.add(androidData)

        androidData = cartmodel(R.drawable.bonsai,"Bonsai", "4", "250")
        dataList.add(androidData)

        androidData = cartmodel(R.drawable.bonsai,"Bonsai", "2", "650")
        dataList.add(androidData)

        adapter = Cartadapter(requireContext(), dataList)

        recyclerView.adapter = adapter
    }

    }


