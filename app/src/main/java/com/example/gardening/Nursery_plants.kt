package com.example.gardening

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class Nursery_plants : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: MutableList<Dataclass>
    private lateinit var adapter: MyAdapter
    private lateinit var androidData: Dataclass
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nursery_plants)



        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.search)

        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchList(newText.orEmpty())
                return true
            }
        })

        val gridLayoutManager = GridLayoutManager(this, 1)
        recyclerView.layoutManager = gridLayoutManager

        dataList = mutableListOf()

        androidData = Dataclass("Bonsai", R.string.bonsai, "Rs.250", R.drawable.bonsai,250)
        dataList.add(androidData)

        androidData = Dataclass("Bamboo Feng Shui", R.string.Bamboo_feng, "Rs.350", R.drawable.bamboofengshui,350)
        dataList.add(androidData)

        androidData = Dataclass("Desert Rose", R.string.Desertrose, "Rs.2550", R.drawable.desertrose,2550)
        dataList.add(androidData)

        androidData = Dataclass("Orchid", R.string.orchid, "Rs.650", R.drawable.orchid,650)
        dataList.add(androidData)

        androidData = Dataclass("Lavender", R.string.lavender, "Rs.270", R.drawable.lavender,270)
        dataList.add(androidData)

        adapter = MyAdapter(this, dataList)

        recyclerView.adapter = adapter


    }

    private fun searchList(text: String) {
        val dataSearchList = dataList.filter {
            it.dataTitle.lowercase(Locale.getDefault()).contains(text.lowercase(Locale.getDefault()))
        }
        if (dataSearchList.isEmpty()) {
            Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show()
        } else {
            adapter.setSearchList(dataSearchList)
        }
    }
}