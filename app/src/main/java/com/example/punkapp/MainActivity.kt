package com.example.punkapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import com.example.punkapp.ui.theme.PunkAppTheme
import com.example.punkapp.ui.view.OverviewView

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Content()
        }
    }

    @Composable
    fun Content() {
        AppKeyboardFocusManager()
        PunkAppTheme {
            OverviewView()
        }
    }

    @Composable
    fun AppKeyboardFocusManager() {
        val context = LocalContext.current
        val focusManager = LocalFocusManager.current
        DisposableEffect(key1 = context) {
            val keyboardManager = KeyBoardManager(context)
            keyboardManager.attachKeyboardDismissListener {
                focusManager.clearFocus()
            }
            onDispose {
                keyboardManager.release()
            }
        }
    }
}