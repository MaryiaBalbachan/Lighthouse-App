package org.wit.lighthouse.main
import android.app.Application
import org.wit.lighthouse.models.LighthouseModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {
    val lighthouses = ArrayList<LighthouseModel>()
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Lighthouse started")
    }
}