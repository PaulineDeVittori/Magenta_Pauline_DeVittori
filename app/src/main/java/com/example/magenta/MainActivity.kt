package com.example.magenta

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.rememberNavController
import com.example.magenta.navigation.AppNavigation
import com.example.magenta.navigation.BottomNavigationBar
import com.example.magenta.ui.theme.MagentaTheme
import com.google.firebase.FirebaseApp

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.magenta.viewmodel.ColorViewModel

class ColorViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ColorViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ColorViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}



class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this)

        setContent {
            MagentaTheme {
                val navController = rememberNavController()

                // Fournir le ViewModel
                val viewModelFactory = ColorViewModelFactory(application)
                val viewModel: ColorViewModel = viewModel(factory = viewModelFactory)

                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(
                                    text = "Magenta",
                                    color = Color(0xFFAD1457)
                                )
                            }
                        )
                    },
                    bottomBar = {
                        BottomNavigationBar(navController)
                    },
                    modifier = Modifier.fillMaxSize()
                ) {
                    AppNavigation(navController = navController, viewModel = viewModel)
                }
            }
        }

    }
}
