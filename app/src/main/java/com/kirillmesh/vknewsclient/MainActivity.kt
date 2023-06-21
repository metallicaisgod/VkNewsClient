package com.kirillmesh.vknewsclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kirillmesh.vknewsclient.ui.compose.VkPostCard
import com.kirillmesh.vknewsclient.ui.theme.VkNewsClientTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VkNewsClientTheme {
                // A surface container using the 'background' color from the theme
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(4.dp)
                ) {
                    VkPostCard()
                }
            }
        }
    }
}

@Composable
fun Greeting(name:  String, modifier: Modifier = Modifier)   {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true )
@Composable
fun GreetingPreview() {
    VkNewsClientTheme {
        Greeting("Android")
    }
}