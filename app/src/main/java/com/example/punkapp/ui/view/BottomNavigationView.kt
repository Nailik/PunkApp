package com.example.punkapp.ui.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.punkapp.ui.icons.Science


enum class BottomNavItem(var title: String, var icon: ImageVector) {
    Information("Information", Icons.Filled.Info),
    Brewing("Brewing", Science)
}

@Composable
fun BottomNavigationView(currentPage: BottomNavItem, onSelectPage: (index: BottomNavItem) -> Unit) {
    NavigationBar {
        BottomNavItem.values().forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(
                        text = item.title
                    )
                },
                alwaysShowLabel = true,
                selected = currentPage == item,
                onClick = { onSelectPage(item) })
        }
    }
}
