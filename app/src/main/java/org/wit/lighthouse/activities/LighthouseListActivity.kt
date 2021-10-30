package org.wit.lighthouse.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.wit.lighthouse.R
import org.wit.lighthouse.databinding.ActivityLighthouseListBinding
import org.wit.lighthouse.databinding.CardLighthouseBinding
import org.wit.lighthouse.main.MainApp
import org.wit.lighthouse.models.LighthouseModel

class LighthouseListActivity : AppCompatActivity() {

    lateinit var app: MainApp
    private lateinit var binding: ActivityLighthouseListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLighthouseListBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_lighthouse_list)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = LighthouseAdapter(app.lighthouses)
    }
}
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

        fun bind(placemark: LighthouseModel) {
            binding.lighthouseTitle.text = placemark.title
            binding.description.text = placemark.description
        }
    }
}