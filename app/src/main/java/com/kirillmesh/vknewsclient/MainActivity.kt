package com.kirillmesh.vknewsclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kirillmesh.vknewsclient.ui.compose.MainScreen
import com.kirillmesh.vknewsclient.ui.compose.SetStatusBarColor
import com.kirillmesh.vknewsclient.ui.compose.VkPostCard
import com.kirillmesh.vknewsclient.ui.theme.VkNewsClientTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VkNewsClientTheme {
                // A surface container using the 'background' color from the theme
                SetStatusBarColor(color = MaterialTheme.colorScheme.onSecondary)
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(MaterialTheme.colorScheme.primary)
//                        .padding(4.dp)
//                ) {
                MainScreen()
                //               }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Test() {
    ModalNavigationDrawer(
            drawerContent = {
                Column() {
                    Text("Action 1")
                    Text("Action 2")
                    Text("Action 3")
                }
            }
    ) {
        Scaffold(
                topBar = {
                    TopAppBar(
                            title = {
                                Text(text = "TopAppBar title")
                            },
                            navigationIcon = {
                                IconButton(onClick = {}) {
                                    Icon(Icons.Filled.Menu, contentDescription = null)
                                }
                            }

                    )
                },
                bottomBar = {
                    var selectedItem by remember { mutableStateOf(0) }
                    val items = listOf("Songs", "Artists", "Playlists")
                    NavigationBar() {
                        items.forEachIndexed { index, item ->
                            NavigationBarItem(
                                    icon = { Icon(Icons.Filled.Favorite, contentDescription = item) },
                                    label = { Text(item) },
                                    selected = selectedItem == index,
                                    onClick = { selectedItem = index }
                            )
                        }

                    }
                }
        ) {
            Text(
                    text = "This is scaffold content",
                    modifier = Modifier.padding(it)
            )
        }
    }

}
