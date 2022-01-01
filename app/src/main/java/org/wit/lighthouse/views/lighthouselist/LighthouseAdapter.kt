package org.wit.lighthouse.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.wit.lighthouse.databinding.CardLighthouseBinding
import org.wit.lighthouse.models.LighthouseModel

interface LighthouseListener {
    fun onLighthouseClick(lighthouse: LighthouseModel)
}

class LighthouseAdapter constructor(private var lighthouses: List<LighthouseModel>,
                                    private val listener: LighthouseListener) :
    RecyclerView.Adapter<LighthouseAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardLighthouseBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val lighthouse = lighthouses[holder.adapterPosition]
        holder.bind(lighthouse, listener)
    }

    override fun getItemCount(): Int = lighthouses.size

    class MainHolder(private val binding: CardLighthouseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(lighthouse: LighthouseModel, listener: LighthouseListener) {
            binding.lighthouseTitle.text = lighthouse.title
            binding.description.text = lighthouse.description
            Picasso.get()
                .load(lighthouse.image)
                .resize(200,200)
                .into(binding.imageIcon)
            binding.root.setOnClickListener {
                listener.onLighthouseClick(lighthouse)
            }
        }
    }
}