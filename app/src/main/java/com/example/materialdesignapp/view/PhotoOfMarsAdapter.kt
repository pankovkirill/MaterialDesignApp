package com.example.materialdesignapp.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.materialdesignapp.R
import com.example.materialdesignapp.model.POMServerResponseData
import com.squareup.picasso.Picasso

class PhotoOfMarsAdapter : RecyclerView.Adapter<PhotoOfMarsAdapter.PhotoOfMarsViewHolder>() {
    private var responseData: List<POMServerResponseData.Preview> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<POMServerResponseData.Preview>) {
        responseData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotoOfMarsViewHolder =
        PhotoOfMarsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_pom, parent, false)
        )

    override fun onBindViewHolder(holder: PhotoOfMarsViewHolder, position: Int) {
        holder.bind(responseData[position])
    }

    inner class PhotoOfMarsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(pomServerResponseData: POMServerResponseData.Preview) {

            itemView.apply {
                findViewById<ImageView>(R.id.photoOfMars).load(newLink(pomServerResponseData.img_src))
                findViewById<TextView>(R.id.textView).text = pomServerResponseData.earth_date
            }
        }

        fun newLink(link: String): String {
            val sb = StringBuffer(link)
            sb.insert(4, "s")
            return sb.toString()
        }
    }

    override fun getItemCount(): Int = responseData.size
}