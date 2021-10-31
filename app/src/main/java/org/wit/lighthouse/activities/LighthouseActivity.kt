package org.wit.lighthouse.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.lighthouse.R
import org.wit.lighthouse.databinding.ActivityLighthouseBinding
import org.wit.lighthouse.helpers.showImagePicker
import org.wit.lighthouse.main.MainApp
import org.wit.lighthouse.models.LighthouseModel
import timber.log.Timber.i

class LighthouseActivity : AppCompatActivity() {
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var binding: ActivityLighthouseBinding

    var lighthouse = LighthouseModel()

    lateinit var app: MainApp


    //val lighthouses = ArrayList<lighthouseModel>()

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
            Picasso.get()
                .load(lighthouse.image)
                .into(binding.lighthouseImage)
            if (lighthouse.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_lighthouse_image)
            }
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
        binding.chooseImage.setOnClickListener {
            i("Select image")
        }
        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }
        binding.lighthouseLocation.setOnClickListener {
            i ("Set Location Pressed")
        }
        binding.lighthouseLocation.setOnClickListener {
            val launcherIntent = Intent(this, MapActivity::class.java)
            mapIntentLauncher.launch(launcherIntent)
        }
        registerMapCallback()
        registerImagePickerCallback()
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
    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            lighthouse.image = result.data!!.data!!
                            Picasso.get()
                                .load(lighthouse.image)
                                .into(binding.lighthouseImage)
                            binding.chooseImage.setText(R.string.change_lighthouse_image)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { i("Map Loaded") }
    }
}