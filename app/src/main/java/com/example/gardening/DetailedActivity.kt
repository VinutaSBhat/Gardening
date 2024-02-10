package com.example.gardening

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import javax.annotation.Nonnull

class DetailedActivity : AppCompatActivity() {
    private var detailDesc: TextView? = null
    private var detailTitle: TextView? = null
    private var detailImage: ImageView? = null
    private var detailprice: TextView? = null
    private lateinit var additem: ImageView
    private lateinit var removeitem: ImageView
    private lateinit var addtocart: AppCompatButton
    private lateinit var quantity: TextView
    var totalquantity:Int=0
    var totalprice:Int=0
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth:FirebaseAuth
private lateinit var view:Dataclass



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed)

        detailDesc = findViewById(R.id.detailDesc)
        detailTitle = findViewById(R.id.detailTitle)
        detailImage = findViewById(R.id.detailImage)
        detailprice = findViewById(R.id.price)
        additem=findViewById(R.id.cartplus)
        removeitem=findViewById(R.id.cartminus)
        addtocart=findViewById(R.id.addtocart)
        quantity=findViewById(R.id.carttext)

auth=FirebaseAuth.getInstance()

        val bundle = intent.extras
        bundle?.let {
            detailDesc?.setText(it.getInt("Desc"))
            detailImage?.setImageResource(it.getInt("Image"))
            detailTitle?.setText(it.getString("Title"))
            detailprice?.setText("Rs."+it.getInt("Price"))

        }

        addtocart.setOnClickListener{v->
            addedtocart()

        }

        additem.setOnClickListener { v ->
            if (totalquantity < 10) {
                totalquantity++
                quantity.text = totalquantity.toString()

                totalprice=bundle!!.getInt("Price")*totalquantity

                Toast.makeText(this@DetailedActivity, totalprice.toString(), Toast.LENGTH_SHORT).show()
            }

        }

        removeitem.setOnClickListener { v ->
            if (totalquantity >0) {
                totalquantity--
                quantity.text = totalquantity.toString()
                totalprice=bundle!!.getInt("Price")*totalquantity

                Toast.makeText(this@DetailedActivity, totalprice.toString(), Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun addedtocart() {

        val cartItem = cartmodel(
            intent.getIntExtra("Image", 0),
            intent.getStringExtra("Title") ?: "",
            "1", // You may set a default quantity here
            intent.getStringExtra("Price") ?: ""
        )

        // Add the item to the cart
        ShoppingCart.addedtocart(cartItem)

        // Notify the user that the item has been added to the cart
        Toast.makeText(this, "Added to Cart", Toast.LENGTH_SHORT).show()
    }

        // Use cartMap as needed





    }
}
