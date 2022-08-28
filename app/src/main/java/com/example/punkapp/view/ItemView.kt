package com.example.punkapp.view

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.punkapp.backend.data.Beer

@Composable
fun LazyItemScope.ItemView(beer: Beer, isExpanded: Boolean, onClick: () -> Unit) {
    Card(modifier = Modifier
        .let {
            return@let if (isExpanded) {
                it
                    .fillParentMaxSize()
                    .padding(bottom = 8.dp)
            } else {
                it.fillMaxSize()
            }
        }
        .animateContentSize()
        .padding(top = 8.dp)
        .padding(horizontal = 8.dp)
        .clickable(onClick = onClick),
        elevation = CardDefaults.outlinedCardElevation(defaultElevation = 5.dp)) {
        CardContent(beer, isExpanded)
    }
}

/**
 * The card content contains an image, text and information baout a beer
 */
@Composable
fun CardContent(beer: Beer, isExpanded: Boolean) {
    var bottomNavItem by remember { mutableStateOf(BottomNavItem.Data) }


    Column(modifier = Modifier.fillMaxSize()) {

        val modifier = Modifier.fillMaxSize().let {
            return@let if (isExpanded) {
                it.weight(1f)
            } else {
                it
            }
        }

        when (bottomNavItem) {
            BottomNavItem.Data -> DataView(modifier, beer, isExpanded)
            BottomNavItem.Recipe -> RecipeView(modifier)
            BottomNavItem.AddPost -> OtherView(modifier)
        }
        if (isExpanded) {
            BottomNavigationView(bottomNavItem, onSelectPage = {
                bottomNavItem = it
            })
        }
    }

}