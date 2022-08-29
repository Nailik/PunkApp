package com.example.punkapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.punkapp.ui.theme.PunkAppTheme
import com.example.punkapp.view.OverviewView

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Content()
        }
    }

    @Composable
    fun Content() {
        PunkAppTheme {
            // A surface container using the 'background' color from the theme
            Surface(modifier = Modifier.fillMaxSize()) {
                OverviewView()
            }
        }
    }

}