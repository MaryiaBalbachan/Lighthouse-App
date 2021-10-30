package org.wit.lighthouse.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import org.wit.lighthouse.databinding.ActivityLighthouseBinding
import org.wit.lighthouse.main.MainApp

import org.wit.lighthouse.models.LighthouseModel
import timber.log.Timber
import timber.log.Timber.i

class LighthouseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLighthouseBinding
    var lighthouse = LighthouseModel()
    //val lighthouses = ArrayList<LighthouseModel>()
    //var app : MainApp? = null
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLighthouseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
      //  Timber.plant(Timber.DebugTree())

        i("Lighthouse Activity started...")

        binding.btnAdd.setOnClickListener() {
            lighthouse.title = binding.lighthouseTitle.text.toString()
            lighthouse.description = binding.description.text.toString()
            if (lighthouse.title.isNotEmpty()) {

                app.lighthouses.add(lighthouse.copy())
                i("add Button Pressed: ${lighthouse}")
                for (i in app.lighthouses.indices) {
                    i("Lighthouse[$i]:${this.app.lighthouses[i]}")
                }
            }
            else {
                Snackbar
                    .make(it,"Please Enter a Name", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}