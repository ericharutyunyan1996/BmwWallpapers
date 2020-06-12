@file:Suppress("DEPRECATION")

package com.erikharutyunyan.bmwwallpapers.Adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.erikharutyunyan.bmwwallpapers.Interfaces.OnItemsClickListener
import com.erikharutyunyan.bmwwallpapers.Models.CarTypes
import com.erikharutyunyan.bmwwallpapers.R


@Suppress("DEPRECATION")
class CarTypesAdapter(
    internal var context: Context, private var cars: ArrayList<CarTypes>
) : RecyclerView.Adapter<CarTypesAdapter.CarItemViewHolder>() {
    var carTypeClick: OnItemsClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_car_types, parent, false)
        return CarItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cars.size
    }

    override fun onBindViewHolder(holder: CarItemViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val myOptions = RequestOptions()
            .override(256, 256)
        Glide.with(context).load(cars[position].image).apply(myOptions)
            .into(object : SimpleTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    holder.image!!.setImageDrawable(resource)
                    holder.progress!!.visibility = View.GONE
                    holder.image.visibility = View.VISIBLE
                }
            })
        holder.title!!.text = cars[position].modelName
        if (position==cars.size-1){
            Log.d("positionSize",position.toString()+" "+ (cars.size-1).toString())
            holder.viewBelow!!.visibility=View.GONE
        }
        else{
            holder.viewBelow!!.visibility=View.VISIBLE
        }
        holder.item!!.setOnClickListener {
            carTypeClick?.onItemClick(
                it,
                position
            )
        }
    }


    class CarItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView? = itemView.findViewById(R.id.category_image)
        val title: TextView? = itemView.findViewById(R.id.category_title)
        val item: ConstraintLayout? = itemView.findViewById(R.id.category_item)
        val progress: ProgressBar? = itemView.findViewById(R.id.category_progress)
        val viewBelow: View? = itemView.findViewById(R.id.belowView)
    }

}