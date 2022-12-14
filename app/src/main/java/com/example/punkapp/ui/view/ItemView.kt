package com.example.punkapp.ui.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.example.punkapp.backend.data.Beer

@Composable
fun LazyItemScope.ItemView(beer: Beer, isExpanded: Boolean, onClick: () -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .let {
                return@let if (isExpanded) {
                    it.fillParentMaxSize()
                } else {
                    it.fillMaxSize()
                }
            }
            .animateContentSize()
            .let {
                if (isExpanded) {
                    it
                } else {
                    it
                        .padding(8.dp)
                        .clip(CardDefaults.elevatedShape)
                }
            }
            .clickable(enabled = !isExpanded) {
                onClick()
            },
        shape = if (isExpanded) RectangleShape else CardDefaults.shape
    ) {
        CardContent(beer, isExpanded, onClick)
    }
}

/**
 * The card content contains an image, text and information baout a beer
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardContent(beer: Beer, isExpanded: Boolean, onClose: () -> Unit) {
    var bottomNavItem by rememberSaveable { mutableStateOf(BottomNavItem.Information) }

    Column(modifier = Modifier.fillMaxSize()) {

        val modifier = Modifier.fillMaxSize().let {
            return@let if (isExpanded) {
                it.weight(1f)
            } else {
                it
            }
        }

        CompositionLocalProvider(LocalAbsoluteTonalElevation provides 12.dp) {
            SmallTopAppBar(
                title = {
                    Text(beer.name ?: "")
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.background),
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                navigationIcon = {
                    if (isExpanded) {
                        Icon(
                            Icons.Filled.Close, contentDescription = "", modifier = Modifier
                                .clip(CircleShape)
                                .clickable(onClick = onClose)
                                .padding(8.dp)
                        )
                    }
                }
            )
        }

        if (!isExpanded) {
            bottomNavItem = BottomNavItem.Information
        }

        when (bottomNavItem) {
            BottomNavItem.Information -> DataView(modifier, beer, isExpanded)
            BottomNavItem.Brewing -> BrewingView(modifier, beer)
        }

        AnimatedVisibility(visible = isExpanded) {
            BottomNavigationView(bottomNavItem, onSelectPage = {
                bottomNavItem = it
            })
        }
    }

}