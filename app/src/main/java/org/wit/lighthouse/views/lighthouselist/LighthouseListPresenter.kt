package org.wit.lighthouse.views.lighthouselist

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import org.wit.lighthouse.views.map.LighthouseMapsView
import org.wit.lighthouse.main.MainApp
import org.wit.lighthouse.models.LighthouseModel
import org.wit.lighthouse.views.lighthouse.LighthouseView

class LighthouseListPresenter(val view: LighthouseListView) {
    var app: MainApp = view.application as MainApp
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var editIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>

    init {
        //app = view.application as MainApp
        registerEditCallback()
        registerRefreshCallback()
    }

    fun getLighthouses() = app.lighthouses.findAll()

    fun doAddLighthouse() {
        val launcherIntent = Intent(view, LighthouseView::class.java)
        editIntentLauncher.launch(launcherIntent)
        //refreshIntentLauncher.launch(launcherIntent)
    }

    fun doEditLighthouse(lighthouse: LighthouseModel) {
        val launcherIntent = Intent(view, LighthouseView::class.java)
        launcherIntent.putExtra("lighthouse_edit", lighthouse)
        //mapIntentLauncher.launch(launcherIntent)
        editIntentLauncher.launch(launcherIntent)
    }

    fun doShowLighthousesMap() {
        val launcherIntent = Intent(view, LighthouseMapsView::class.java)
        //refreshIntentLauncher.launch(launcherIntent)
        editIntentLauncher.launch(launcherIntent)
    }
    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { getLighthouses() }
    }
    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }
    }
    private fun registerEditCallback() {
        editIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }

    }
}

