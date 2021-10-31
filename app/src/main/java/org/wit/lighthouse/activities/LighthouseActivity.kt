package org.wit.lighthouse.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
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

        binding = ActivityLighthouseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp

        i("Lighthouse Activity started...")

        binding.btnAdd.setOnClickListener() {
            lighthouse.title = binding.lighthouseTitle.text.toString()
            lighthouse.description = binding.description.text.toString()
            if (lighthouse.title.isNotEmpty()) {

                app.lighthouses.add(lighthouse.copy())
                i("add Button Pressed: ${lighthouse}")
                for (i in app.lighthouses.indices) {
                    i("lighthouse[$i]:${this.app.lighthouses[i]}")
                }
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar
                    .make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}