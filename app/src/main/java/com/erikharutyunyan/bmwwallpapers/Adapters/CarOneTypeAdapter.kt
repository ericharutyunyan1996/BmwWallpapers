package com.erikharutyunyan.bmwwallpapers.Adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.erikharutyunyan.bmwwallpapers.Interfaces.OnItemsClickListener
import com.erikharutyunyan.bmwwallpapers.Models.CarImageModel
import com.erikharutyunyan.bmwwallpapers.R

class CarOneTypeAdapter(private val context: Context, private var cars: ArrayList<CarImageModel>) :
    RecyclerView.Adapter<CarOneTypeAdapter.CarsViewHolder>() {
    var onItemsClickListener: OnItemsClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_car_general, parent, false)
        return CarsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cars.size
    }

    override fun onBindViewHolder(holder: CarsViewHolder, position: Int) {
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
        holder.itemView.setOnClickListener {
            onItemsClickListener!!.onItemClick(it, position)
        }
    }

    class CarsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView? = itemView.findViewById(R.id.imagesOnType)
        val progress: ProgressBar? = itemView.findViewById(R.id.progressCarGeneral)
    }
}