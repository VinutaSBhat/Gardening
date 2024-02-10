package com.example.gardening

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView



class Cartadapter(private val context: Context, private var cartModel: List<cartmodel>): RecyclerView.Adapter<MyViewHolde>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolde {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)

        return MyViewHolde(view)
    }

    override fun onBindViewHolder(holder: MyViewHolde, position: Int) {
        val data = cartModel[position]

        holder.recImage.setImageResource(data.cartImage)
        holder.recTitle.text = data.carttitle
        holder.recprice.text = data.carttitle


    }

    override fun getItemCount(): Int {
        return cartModel.size
    }
}

class MyViewHolde(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var recImage: ImageView = itemView.findViewById(R.id.imagecart)
    var recTitle: TextView = itemView.findViewById(R.id.titlecart)
    var recprice: TextView = itemView.findViewById(R.id.price)

}