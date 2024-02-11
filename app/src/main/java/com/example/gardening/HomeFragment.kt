package com.example.gardening


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel


class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var imageSlider: ImageSlider
    private lateinit var cardView: CardView
    private lateinit var cardView1: CardView
    private lateinit var cardView2: CardView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageSlider = view.findViewById(R.id.slider)

        val slideModels = ArrayList<SlideModel>()

        slideModels.add(SlideModel(R.drawable.bonsai, ScaleTypes.FIT))
        slideModels.add(SlideModel(R.drawable.desertrose, ScaleTypes.FIT))
        slideModels.add(SlideModel(R.drawable.bamboofengshui, ScaleTypes.FIT))
        slideModels.add(SlideModel(R.drawable.lavender, ScaleTypes.FIT))
        slideModels.add(SlideModel(R.drawable.rosyadenium, ScaleTypes.FIT))

        imageSlider.setImageList(slideModels, ScaleTypes.FIT)

        cardView = view.findViewById(R.id.gardeningtools)

        cardView.setOnClickListener {
            context?.startActivity(Intent(context, Gardeningtools::class.java))
        }
        cardView1 = view.findViewById(R.id.nurseryplants)

        cardView1.setOnClickListener {
            context?.startActivity(Intent(context, Nursery_plants::class.java))
        }
        cardView2 = view.findViewById(R.id.seeds)

        cardView2.setOnClickListener {
            context?.startActivity(Intent(context, Seeds::class.java))
        }



    }
}
