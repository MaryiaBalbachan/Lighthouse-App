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
import org.wit.lighthouse.adapters.LighthouseAdapter
import org.wit.lighthouse.adapters.LighthouseListener
import org.wit.lighthouse.databinding.ActivityLighthouseListBinding
import org.wit.lighthouse.databinding.CardLighthouseBinding
import org.wit.lighthouse.main.MainApp
import org.wit.lighthouse.models.LighthouseModel

class LighthouseListActivity : AppCompatActivity(), LighthouseListener {

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
        binding.recyclerView.adapter = LighthouseAdapter(app.lighthouses.findAll(),this)
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

    override fun onLighthouseClick(lighthouse: LighthouseModel) {
        val launcherIntent = Intent(this, LighthouseActivity::class.java)
        launcherIntent.putExtra("lighthouse_edit", lighthouse)
        startActivityForResult(launcherIntent,0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        binding.recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }
}



