package org.wit.lighthouse.views.lighthouselist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.lighthouse.R
import org.wit.lighthouse.activities.LighthouseMapsActivity
import org.wit.lighthouse.adapters.LighthouseAdapter
import org.wit.lighthouse.adapters.LighthouseListener
import org.wit.lighthouse.databinding.ActivityLighthouseListBinding
import org.wit.lighthouse.main.MainApp
import org.wit.lighthouse.models.LighthouseModel
import org.wit.lighthouse.views.lighthouse.LighthouseView

class LighthouseListActivity : AppCompatActivity(), LighthouseListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityLighthouseListBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLighthouseListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager


        loadLighthouses()
        registerRefreshCallback()
        registerMapCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, LighthouseView::class.java)
                refreshIntentLauncher.launch(launcherIntent)
            }
            R.id.item_map -> {
                val launcherIntent = Intent(this, LighthouseMapsActivity::class.java)
                refreshIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onLighthouseClick(lighthouse: LighthouseModel) {
        val launcherIntent = Intent(this, LighthouseView::class.java)
        launcherIntent.putExtra("lighthouse_edit", lighthouse)
        mapIntentLauncher.launch(launcherIntent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        binding.recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadLighthouses() }
    }
    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { }
    }

    private fun loadLighthouses() {
        showLighthouses(app.lighthouses.findAll())
    }

    fun showLighthouses (lighthouses: List<LighthouseModel>) {
        binding.recyclerView.adapter = LighthouseAdapter(lighthouses, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }
}



