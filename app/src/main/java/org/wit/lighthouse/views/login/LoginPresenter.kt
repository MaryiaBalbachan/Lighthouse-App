package org.wit.lighthouse.views.login

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import org.wit.lighthouse.views.lighthouselist.LighthouseListView


class LoginPresenter (val view: LoginView)  {
    private lateinit var loginIntentLauncher : ActivityResultLauncher<Intent>

    init{
        registerLoginCallback()
    }

    fun doLogin(email: String, password: String) {
        val launcherIntent = Intent(view, LighthouseListView::class.java)
        loginIntentLauncher.launch(launcherIntent)
    }

    fun doSignUp(email: String, password: String) {
        val launcherIntent = Intent(view, LighthouseListView::class.java)
        loginIntentLauncher.launch(launcherIntent)
    }
    private fun registerLoginCallback(){
        loginIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }
    }
}