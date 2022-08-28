package com.example.punkapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
fun ListElement(beer: Beer, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            AsyncImage(
                model = beer.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .height(128.dp)
                    .width(56.dp)
            )

        }


        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Text(
                text = beer.name ?: "",
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = beer.tagline ?: "",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(top = 12.dp)
            )

            Text(
                text = beer.description ?: "",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 16.dp)
            )

            Text(
                text = beer.firstBrewed ?: "",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 16.dp)
            )
        }


        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .wrapContentWidth()
        ) {
            val triple = BeerColorCodes.getSrmColorCode(beer.srm ?: 0.0)
            val color: Color? = triple?.let { Color(it.first, it.second, it.third) }

            Box(modifier = Modifier
                .size(40.dp)
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
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 12.dp)
            )

            Text(
                text = beer.ibu?.toString() ?: "",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 12.dp)
            )

            Text(
                text = beer.ebc?.toString() ?: "",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp)
            )



            Text(
                text = beer.ph?.toString() ?: "",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }

}

//name
//tagline
//first Brewed
//image Url
//ebc, srm, ph, abv, ibu
//contributedBy