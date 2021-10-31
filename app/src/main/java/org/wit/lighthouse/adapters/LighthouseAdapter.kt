package org.wit.lighthouse.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wit.lighthouse.databinding.CardLighthouseBinding
import org.wit.lighthouse.models.LighthouseModel

class LighthouseAdapter constructor(private var lighthouses: List<LighthouseModel>) :
    RecyclerView.Adapter<LighthouseAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardLighthouseBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val lighthouse = lighthouses[holder.adapterPosition]
        holder.bind(lighthouse)
    }

    override fun getItemCount(): Int = lighthouses.size

    class MainHolder(private val binding : CardLighthouseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(lighthouse: LighthouseModel) {
            binding.lighthouseTitle.text = lighthouse.title
            binding.description.text = lighthouse.description
        }
    }
}