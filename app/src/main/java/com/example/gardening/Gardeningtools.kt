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

        androidData = Dataclass("Hand Tool Kit", R.string.handtoolkit, "Rs.399", R.drawable.handtoolkit,399)
        dataList.add(androidData)

        androidData = Dataclass("Hand Pruner Cutter", R.string.handprunercutter, "Rs.650", R.drawable.handprunercutter,650)
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
