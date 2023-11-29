package com.kirillmesh.vknewsclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import com.kirillmesh.vknewsclient.ui.compose.ActivityResultTest
import com.kirillmesh.vknewsclient.ui.compose.MainScreen
import com.kirillmesh.vknewsclient.ui.compose.SetStatusBarColor
import com.kirillmesh.vknewsclient.ui.theme.VkNewsClientTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VkNewsClientTheme {
                // A surface container using the 'background' color from the theme
                SetStatusBarColor(color = MaterialTheme.colors.onSecondary)
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(MaterialTheme.colorScheme.primary)
//                        .padding(4.dp)
//                ) {
//                MainScreen()
                ActivityResultTest()
                //               }
            }
        }
    }
}

