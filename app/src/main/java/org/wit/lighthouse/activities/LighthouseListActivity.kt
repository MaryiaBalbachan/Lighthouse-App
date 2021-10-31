package org.wit.lighthouse.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
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
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = lighthouseAdapter(app.lighthouses)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, LighthouseActivity::class.java)
                startActivityForResult(launcherIntent,0)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

class lighthouseAdapter constructor(private var lighthouses: List<LighthouseModel>) :
    RecyclerView.Adapter<lighthouseAdapter.MainHolder>() {

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

