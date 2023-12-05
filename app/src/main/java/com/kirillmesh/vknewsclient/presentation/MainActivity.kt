package com.kirillmesh.vknewsclient.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kirillmesh.vknewsclient.domain.entities.AuthState
import com.kirillmesh.vknewsclient.presentation.screens.LoginScreen
import com.kirillmesh.vknewsclient.presentation.screens.MainScreen
import com.kirillmesh.vknewsclient.presentation.screens.SetStatusBarColor
import com.kirillmesh.vknewsclient.presentation.theme.VkNewsClientTheme
import com.kirillmesh.vknewsclient.presentation.viewmodels.MainViewModel
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val component = getApplicationComponent()
            val viewModel: MainViewModel = viewModel(factory = component.getViewModelFactory())
            val authState = viewModel.authState.collectAsState(AuthState.Initial)

            val authLauncher = rememberLauncherForActivityResult(
                contract = VK.getVKAuthActivityResultContract(),
            ) {
                viewModel.performAuthorization()
            }
            VkNewsClientTheme {
                SetStatusBarColor(color = MaterialTheme.colors.onSecondary)
                when (authState.value) {
                    is AuthState.Authorized -> MainScreen()
                    is AuthState.NotAuthorized -> LoginScreen {
                        authLauncher.launch(listOf(VKScope.WALL, VKScope.FRIENDS))
                    }
                    else -> {}
                }
            }
        }
    }
}

