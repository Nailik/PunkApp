package com.example.punkapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.punkapp.MainActivity.Companion.LocalNavController
import com.example.punkapp.backend.BeerColorCodes
import com.example.punkapp.backend.data.Beer
import com.example.punkapp.viewmodel.OverviewViewModel

/**
 * List of peers with pagination
 */
@Composable
fun OverviewView(viewModel: OverviewViewModel = viewModel()) {
    val data: LazyPagingItems<Beer> = viewModel.beer.collectAsLazyPagingItems()
    val navController = LocalNavController.current

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(count = data.itemCount, itemContent = { index ->
            data[index]?.also { beer ->
                ListElement(beer) {
                    navController.navigate("detailView/${beer.id}")
                }
            }
        })
    }

}

/**
 * one list element displays a card
 * when clicked, navigation to the detail view is started
 */
@Composable
fun ListElement(beer: Beer, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .padding(horizontal = 8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.outlinedCardElevation(defaultElevation = 5.dp)
    ) {
        CardContent(beer)
    }
}

/**
 * The card content contains an image, text and information baout a beer
 */
@Composable
fun CardContent(beer: Beer) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            AsyncImage(
                model = beer.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .height(128.dp)
                    .width(56.dp)
                    .padding(horizontal = 8.dp)
                    .padding(top = 8.dp)
            )

            TextSection(beer)

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
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 16.dp)
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