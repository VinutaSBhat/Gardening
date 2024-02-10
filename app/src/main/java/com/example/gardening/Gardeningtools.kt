package com.example.gardening

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class Gardeningtools : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: MutableList<Dataclass>
    private lateinit var adapter: MyAdapter
    private lateinit var androidData: Dataclass
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gardeningtools)



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

        androidData = Dataclass("Camera", R.string.camera, "Java", R.drawable.bonsai,250)
        dataList.add(androidData)

        androidData = Dataclass("RecyclerView", R.string.recyclerview, "Kotlin", R.drawable.bamboofengshui,350)
        dataList.add(androidData)

        androidData = Dataclass("Date Picker", R.string.date, "Java", R.drawable.desertrose,2550)
        dataList.add(androidData)

        androidData = Dataclass("EditText", R.string.edit, "Kotlin", R.drawable.orchid,650)
        dataList.add(androidData)

        androidData = Dataclass("Rating Bar", R.string.rating, "Java", R.drawable.lavender,270)
        dataList.add(androidData)

        adapter = MyAdapter(this, dataList)

        recyclerView.adapter = adapter


    }

    private fun searchList(text: String) {
        val dataSearchList = dataList.filter {
            it.dataTitle.toLowerCase(Locale.getDefault()).contains(text.toLowerCase(Locale.getDefault()))
        }
        if (dataSearchList.isEmpty()) {
            Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show()
        } else {
            adapter.setSearchList(dataSearchList)
        }
    }
}
