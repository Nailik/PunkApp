package com.example.punkapp.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.punkapp.backend.data.Beer

@Composable
fun BrewingView(modifier: Modifier, beer: Beer) {
    val scroll = rememberScrollState(0)

    Column(
        modifier = modifier
            .verticalScroll(scroll)
    ) {
        BrewerTips(beer)
        Attenuationlevel(beer)
        Volume(beer)
        Brewing(beer)
        Method(beer)
        Ingredients(beer)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrewerTips(beer: Beer) {
    beer.brewersTips?.also { brewersTips ->
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = CardDefaults.outlinedCardElevation(defaultElevation = 5.dp)
        ) {
            ListItem(
                headlineText = {
                    Text("Brewer Tips", style = MaterialTheme.typography.titleMedium)
                })
            ListItem(
                tonalElevation = 100.dp,
                headlineText = {
                    Text(text = brewersTips)
                })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Attenuationlevel(beer: Beer) {
    beer.attenuationLevel?.also { attenuationLevel ->
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = CardDefaults.outlinedCardElevation(defaultElevation = 5.dp)
        ) {
            ListItem(
                headlineText = {
                    Text("Attenuation Level", style = MaterialTheme.typography.titleMedium)
                })
            ListItem(
                tonalElevation = 100.dp,
                headlineText = {
                    Text(text = attenuationLevel.toString())
                })
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Volume(beer: Beer) {
    if (beer.volume != null || beer.boilVolume != null) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = CardDefaults.outlinedCardElevation(defaultElevation = 5.dp)
        ) {
            ListItem(
                headlineText = {
                    Text("Volume", style = MaterialTheme.typography.titleMedium)
                })
            beer.volume?.also { volume ->
                ListItem(
                    tonalElevation = 100.dp,
                    headlineText = {
                        Text(text = "Volume")
                    },
                    supportingText = {
                        Text(text = "${volume.value ?: ""} ${volume.unit ?: ""}")
                    })
            }
            beer.boilVolume?.also { volume ->
                ListItem(
                    tonalElevation = 100.dp,
                    headlineText = {
                        Text(text = "Boil Volume")
                    },
                    supportingText = {
                        Text(text = "${volume.value ?: ""} ${volume.unit ?: ""}")
                    })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Brewing(beer: Beer) {
    if (beer.targetFg != null || beer.targetOg != null) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = CardDefaults.outlinedCardElevation(defaultElevation = 5.dp)
        ) {
            ListItem(
                headlineText = {
                    Text("Gravity", style = MaterialTheme.typography.titleMedium)
                })
            beer.targetFg?.also { fg ->
                ListItem(
                    tonalElevation = 100.dp,
                    headlineText = {
                        Text(text = "Final Gravity")
                    },
                    supportingText = {
                        Text(text = fg.toString())
                    })
            }
            beer.targetOg?.also { og ->
                ListItem(
                    tonalElevation = 100.dp,
                    headlineText = {
                        Text(text = "Original Gravity")
                    },
                    supportingText = {
                        Text(text = og.toString())
                    })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Method(beer: Beer) {
    beer.method?.also { method ->
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = CardDefaults.outlinedCardElevation(defaultElevation = 5.dp)
        ) {
            ListItem(
                tonalElevation = 5.dp,
                shadowElevation = 10.dp,
                headlineText = {
                    Text("Method", style = MaterialTheme.typography.titleLarge)
                })

            method.mashTemp?.also { mashTemps ->

                ListItem(
                    tonalElevation = 20.dp,
                    shadowElevation = 10.dp,
                    headlineText = {
                        Text("Mash Temp", style = MaterialTheme.typography.titleMedium)
                    })

                mashTemps.forEach { temps ->
                    temps?.also { temp ->
                        ListItem(
                            tonalElevation = 100.dp,
                            headlineText = {
                                Text(text = "${temp.temperature?.value ?: ""} ${temp.temperature?.unit ?: ""}")
                            },
                            supportingText = {
                                Text(text = temp.duration?.toString() ?: "")
                            })
                    }
                }
            }

            method.fermentation?.also {
                ListItem(
                    tonalElevation = 20.dp,
                    shadowElevation = 10.dp,
                    headlineText = {
                        Text("Fermentation", style = MaterialTheme.typography.titleMedium)
                    })

                ListItem(
                    tonalElevation = 100.dp,
                    headlineText = {
                        Text(text = "${it.temperature?.value ?: ""} ${it.temperature?.unit ?: ""}")
                    })
            }

            method.twist?.also { twist ->
                ListItem(
                    tonalElevation = 20.dp,
                    shadowElevation = 10.dp,
                    headlineText = {
                        Text("Twist", style = MaterialTheme.typography.titleMedium)
                    })

                ListItem(
                    tonalElevation = 100.dp,
                    headlineText = {
                        Text(text = twist)
                    })
            }
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Ingredients(beer: Beer) {
    beer.ingredients?.also { ingredients ->

        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = CardDefaults.outlinedCardElevation(defaultElevation = 5.dp)
        ) {
            ListItem(
                tonalElevation = 5.dp,
                shadowElevation = 10.dp,
                headlineText = {
                    Text("Ingredients", style = MaterialTheme.typography.titleLarge)
                })


            ingredients.malt?.also {

                ListItem(
                    tonalElevation = 20.dp,
                    shadowElevation = 10.dp,
                    headlineText = {
                        Text("Malt", style = MaterialTheme.typography.titleMedium)
                    })

                it.forEach { malts ->
                    malts?.also { malt ->
                        ListItem(
                            tonalElevation = 100.dp,
                            headlineText = {
                                Text(text = malt.name ?: "")
                            },
                            supportingText = {
                                Text(text = "${malt.amount?.value ?: ""} ${malt.amount?.unit ?: ""} ")
                            })
                    }
                }
            }

            ingredients.hops?.also {

                ListItem(
                    tonalElevation = 10.dp,
                    headlineText = {
                        Text("Hops", style = MaterialTheme.typography.titleMedium)
                    })

                it.forEach { hops ->
                    hops?.also { hop ->
                        ListItem(
                            tonalElevation = 100.dp,
                            headlineText = {
                                Text(text = hop.name ?: "")
                            },
                            supportingText = {
                                Text(text = "${hop.amount?.value ?: ""} ${hop.amount?.unit ?: ""} ")
                            },
                            overlineText = {
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Text(text = hop.add ?: "")
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text(text = hop.attribute ?: "")
                                }
                            })
                    }
                }
            }

            ingredients.yeast?.also { yeast ->
                ListItem(
                    tonalElevation = 20.dp,
                    shadowElevation = 10.dp,
                    headlineText = {
                        Text("Yeast", style = MaterialTheme.typography.titleMedium)
                    })

                ListItem(
                    tonalElevation = 100.dp,
                    headlineText = {
                        Text(text = yeast)
                    })
            }
        }

    }
}