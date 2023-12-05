package com.kirillmesh.vknewsclient.presentation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.kirillmesh.vknewsclient.di.ApplicationComponent
import com.kirillmesh.vknewsclient.di.DaggerApplicationComponent

class NewsFeedApplication : Application() {

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(
            this
        )
    }
}

@Composable
fun getApplicationComponent() : ApplicationComponent {
    return (LocalContext.current.applicationContext as NewsFeedApplication).component
}