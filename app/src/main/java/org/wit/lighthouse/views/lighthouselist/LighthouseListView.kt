package org.wit.lighthouse.views.lighthouselist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.lighthouse.R
import org.wit.lighthouse.adapters.LighthouseAdapter
import org.wit.lighthouse.adapters.LighthouseListener
import org.wit.lighthouse.databinding.ActivityLighthouseListBinding
import org.wit.lighthouse.main.MainApp
import org.wit.lighthouse.models.LighthouseModel
import timber.log.Timber

class LighthouseListView : AppCompatActivity(), LighthouseListener {
    lateinit var app: MainApp
    private lateinit var binding: ActivityLighthouseListBinding
    lateinit var presenter: LighthouseListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLighthouseListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        presenter = LighthouseListPresenter(this)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter =
            LighthouseAdapter(presenter.getLighthouses(), this)
        //loadLighthouses()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onResume() {
        //update the view
        binding.recyclerView.adapter?.notifyDataSetChanged()
        Timber.i("recyclerView onResume")
        super.onResume()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> { presenter.doAddLighthouse() }
            R.id.item_map -> { presenter.doShowLighthousesMap() }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onLighthouseClick(lighthouse: LighthouseModel) {
        presenter.doEditLighthouse(lighthouse)

    }

    private fun loadLighthouses() {
        binding.recyclerView.adapter = LighthouseAdapter(presenter.getLighthouses(), this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }
}
