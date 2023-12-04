package com.kirillmesh.vknewsclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kirillmesh.vknewsclient.ui.screens.LoginScreen
import com.kirillmesh.vknewsclient.ui.screens.MainScreen
import com.kirillmesh.vknewsclient.ui.screens.SetStatusBarColor
import com.kirillmesh.vknewsclient.ui.states.AuthState
import com.kirillmesh.vknewsclient.ui.theme.VkNewsClientTheme
import com.kirillmesh.vknewsclient.ui.viewmodels.MainViewModel
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VkNewsClientTheme {
                val viewModel: MainViewModel = viewModel()
                val authState = viewModel.authState.collectAsState(AuthState.Initial)

                val authLauncher = rememberLauncherForActivityResult(
                    contract = VK.getVKAuthActivityResultContract(),
                ) {
                    viewModel.performAuthorization()
                }
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

