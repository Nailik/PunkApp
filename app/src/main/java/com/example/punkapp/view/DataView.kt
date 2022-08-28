package com.example.punkapp.view

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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

    Column(
        modifier = modifier
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            val height by animateDpAsState(
                targetValue = if (isExpanded) 256.dp else 128.dp,
                animationSpec = tween(200)
            )
            val width by animateDpAsState(
                targetValue = if (isExpanded) 128.dp else 56.dp,
                animationSpec = tween(200)
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
            text = beer.name ?: "",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = beer.tagline ?: "",
            style = MaterialTheme.typography.titleSmall
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
            .padding(end = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val triple = BeerColorCodes.getSrmColorCode(beer.srm ?: 0.0)
        val color: Color? = triple?.let { Color(it.first, it.second, it.third) }

        Box(modifier = Modifier
            .size(56.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(10.dp))
            .let {
                return@let color?.let { col ->
                    it.background(col)
                } ?: run {
                    it
                }
            }) {
            Text(
                text = beer.srm?.toString() ?: "?",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.inverseOnSurface,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Text(
            text = "${beer.abv?.toString() ?: "?"} %",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.align(Alignment.CenterVertically)
        )

        Text(
            text = beer.ibu?.toString() ?: "",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.align(Alignment.CenterVertically)
        )

        Text(
            text = beer.ebc?.toString() ?: "",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.align(Alignment.CenterVertically)
        )

        Text(
            text = beer.ph?.toString() ?: "",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.align(Alignment.CenterVertically)
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = beer.firstBrewed ?: "",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}