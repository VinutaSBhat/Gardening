package com.example.gardening

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val context: Context, private var dataList: List<Dataclass>) :
    RecyclerView.Adapter<MyViewHolder>() {

    fun setSearchList(dataSearchList: List<Dataclass>) {
        dataList = dataSearchList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycle_item, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = dataList[position]

        holder.recImage.setImageResource(data.dataImage)
        holder.recTitle.text = data.dataTitle
        holder.recDesc.text = context.getString(data.dataDesc)
        holder.recLang.text = data.dataLang

        holder.recCard.setOnClickListener {
            val intent = Intent(context, DetailedActivity::class.java).apply {
            putExtra("Image", dataList[holder.adapterPosition].dataImage)
            putExtra("Title", dataList[holder.adapterPosition].dataTitle)
            putExtra("Desc", dataList[holder.adapterPosition].dataDesc)
                putExtra("Price", dataList[holder.adapterPosition].dataprice)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var recImage: ImageView = itemView.findViewById(R.id.recImage)
    var recTitle: TextView = itemView.findViewById(R.id.recTitle)
    var recDesc: TextView = itemView.findViewById(R.id.recDesc)
    var recLang: TextView = itemView.findViewById(R.id.recLang)
    var recCard: androidx.cardview.widget.CardView = itemView.findViewById(R.id.recCard)
}
