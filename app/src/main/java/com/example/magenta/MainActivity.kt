package com.example.magenta

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.magenta.data.AppDatabase
import com.example.magenta.data.ColorDatabaseInitializer
import com.example.magenta.navigation.AppNavigation
import com.example.magenta.navigation.BottomNavigationBar
import com.example.magenta.ui.theme.MagentaTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = AppDatabase.getDatabase(applicationContext)
        ColorDatabaseInitializer.initialize(applicationContext, db.colorDao())

        setContent {
            MagentaTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = { BottomNavigationBar(navController) },
                    modifier = Modifier.fillMaxSize()
                ) {
                    AppNavigation(navController = navController)
                }
            }
        }
    }
}
