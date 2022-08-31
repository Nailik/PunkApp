package com.example.punkapp.ui.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.punkapp.backend.BeerColorCodes
import com.example.punkapp.backend.data.Beer

@Composable
fun DataView(modifier: Modifier, beer: Beer, isExpanded: Boolean) {

    val scroll = rememberScrollState(0)

    Column(
        modifier = modifier
            .fillMaxSize()
            .let {
                if (isExpanded) it.verticalScroll(scroll) else it
            }
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            val height by animateDpAsState(
                targetValue = if (isExpanded) 256.dp else 128.dp, animationSpec = tween(200)
            )

            val width by animateDpAsState(
                targetValue = if (isExpanded) 128.dp else 56.dp, animationSpec = tween(200)
            )

            TextSection(beer)

            AsyncImage(
                model = beer.imageUrl,
                contentDescription = null,
                contentScale = if (isExpanded) ContentScale.Fit else ContentScale.Inside,
                modifier = Modifier
                    .height(height)
                    .width(width)
                    .padding(horizontal = 8.dp)
                    .padding(top = 8.dp)
            )
        }

        InformationBar(beer)

        AnimatedVisibility(visible = isExpanded) {
            FoodPairing(beer)
        }

        Spacer(modifier = Modifier.weight(1f))

        beer.contributedBy?.also { contributedBy ->
            Text("Contributed by: $contributedBy", style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(8.dp))
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodPairing(beer: Beer) {
    beer.foodPairing?.also { foodPairing ->
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            ListItem(
                tonalElevation = 5.dp,
                headlineText = {
                    Text("Food Pairing", style = MaterialTheme.typography.titleMedium)
                })

            foodPairing.filterNotNull().forEach { pairing ->
                ListItem(
                    tonalElevation = 100.dp,
                    headlineText = {
                        Text(text = pairing)
                    })
            }
        }
    }
}


/**
 * the textSection shows the name, tagline and description of a beer in a column
 */
@Composable
fun RowScope.TextSection(beer: Beer) {
    Column(
        modifier = Modifier
            .weight(1f)
            .padding(horizontal = 8.dp)
    ) {


        Text(
            text = beer.tagline ?: "", style = MaterialTheme.typography.titleSmall, modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Text(
            text = beer.description ?: "",
            style = MaterialTheme.typography.bodyMedium
        )

    }
}

/**
 * The Information Bar contains the srm color, abv, ibu, ebc and ph data
 * and also the firstBrewed date
 */
@Composable
fun InformationBar(beer: Beer) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, end = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val triple = beer.srm?.let { BeerColorCodes.getSrmColorCode(it) }
        val color: Color = triple?.let { Color(it.first, it.second, it.third) } ?: run {
            Color.Transparent
        }

        val textColor: Color = if (isSystemInDarkTheme()) {
            if ((beer.srm ?: 0.0) < 8) {
                MaterialTheme.colorScheme.inverseOnSurface
            } else {
                LocalContentColor.current
            }
        } else {
            if ((beer.srm ?: 0.0) < 8) {
                LocalContentColor.current
            } else {
                MaterialTheme.colorScheme.inverseOnSurface
            }
        }


        Box(
            modifier = Modifier
                .size(56.dp)
                .padding(8.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color)
        ) {
            Column(modifier = Modifier.align(Alignment.Center)) {
                Text(
                    text = beer.srm?.toString() ?: "?",
                    style = MaterialTheme.typography.bodySmall,
                    color = textColor,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "srm",
                    style = MaterialTheme.typography.labelSmall,
                    color = textColor,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }

        DataElement(name = "ibu", value = beer.ibu)
        DataElement(name = "abv", value = beer.abv)
        DataElement(name = "ebc", value = beer.ebc)
        DataElement(name = "ph", value = beer.ph)

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = beer.firstBrewed ?: "",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun RowScope.DataElement(name: String, value: Double?) {
    Column(modifier = Modifier.align(Alignment.CenterVertically)) {
        Text(
            text = value?.toString() ?: "?",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = name,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}