package org.wit.lighthouse.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import org.wit.lighthouse.R
import org.wit.lighthouse.databinding.ActivityLighthouseBinding
import org.wit.lighthouse.main.MainApp
import org.wit.lighthouse.models.LighthouseModel
import timber.log.Timber.i

class LighthouseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLighthouseBinding
    var lighthouse = LighthouseModel()
    //val lighthouses = ArrayList<lighthouseModel>()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var edit = false

        binding = ActivityLighthouseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        if (intent.hasExtra("lighthouse_edit")) {
            edit = true
            lighthouse = intent.extras?.getParcelable("lighthouse_edit")!!
            binding.lighthouseTitle.setText(lighthouse.title)
            binding.description.setText(lighthouse.description)
            binding.btnAdd.setText(R.string.save_lighthouse)
        }

        binding.btnAdd.setOnClickListener() {
            lighthouse.title = binding.lighthouseTitle.text.toString()
            lighthouse.description = binding.description.text.toString()
            if (lighthouse.title.isEmpty()) {
                Snackbar.make(it,R.string.enter_lighthouse_title, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.lighthouses.update(lighthouse.copy())
                } else {
                    app.lighthouses.create(lighthouse.copy())
                }
            }
            setResult(RESULT_OK)
            finish()
        }

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_lighthouse, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> { finish() }
        }
        return super.onOptionsItemSelected(item)
    }
}