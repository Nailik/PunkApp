package com.example.punkapp.view

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable


enum class BottomNavItem(var title: String) {
    Data("Home"),
    Recipe("My Network"),
    AddPost("Post")
}

@Composable
fun BottomNavigationView(currentPage: BottomNavItem, onSelectPage: (index: BottomNavItem) -> Unit) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = contentColorFor(backgroundColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        BottomNavItem.values().forEach { item ->
            BottomNavigationItem(
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
                onClick = { onSelectPage(item) }
            )
        }
    }
}
