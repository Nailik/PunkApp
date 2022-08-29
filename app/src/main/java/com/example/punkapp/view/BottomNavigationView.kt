package com.example.punkapp.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable


enum class BottomNavItem(var title: String) {
    Information("Information"),
    Brewing("Brewing")
}

@Composable
fun BottomNavigationView(currentPage: BottomNavItem, onSelectPage: (index: BottomNavItem) -> Unit) {
    NavigationBar {
        BottomNavItem.values().forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                alwaysShowLabel = true,
                selected = currentPage == item,
                onClick = { onSelectPage(item) })
        }
    }
}
