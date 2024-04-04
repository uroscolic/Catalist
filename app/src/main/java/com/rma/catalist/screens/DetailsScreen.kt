package com.rma.catalist.screens

import android.annotation.SuppressLint
import androidx.compose.material3.Card
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rma.catalist.R
import com.rma.catalist.repository.Repository
import com.rma.catalist.ui.theme.CatalistTheme
import com.rma.catalist.ui.theme.Samsung
val orange = Color.hsl(23f, 0.8f, 0.65f)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailsScreen(
    catId: String,
    onBack: () -> Unit
) {

    val cat = Repository.getById(catId)
    Scaffold(
        topBar = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row() {

                    CenterAlignedTopAppBar(
                        navigationIcon = {
                            Image(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Logo",
                                modifier = Modifier
                                    .weight(0.1f)
                                    .padding(start = 8.dp)
                                    .size(24.dp)
                                    .clickable {
                                        onBack()
                                    }

                            )
                        },
                        title = {
                            Text(
                                text = "Details",
                                fontFamily = Samsung,
                                fontWeight = FontWeight.Medium,
                                fontSize = 24.sp
                            )
                        }
                    )

                }
                Divider()
            }
        },
        content = {
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                Spacer(
                    modifier = Modifier.padding(16.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.cat),
                    contentDescription = "Cat",
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.White)
                )
                Text(
                    modifier = Modifier
                        .padding(8.dp),
                    text = "${cat?.name}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                if(cat?.alternativeName?.isNotEmpty() == true) {
                    Text(
                        text = "(${cat.alternativeName})",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
                Spacer(
                    modifier = Modifier.padding(16.dp)
                )
                RowForShortText(label = "Origin:", text = "${cat?.origin}")
                RowForShortText(label = "Life Span:", text = "${cat?.lifeSpan}")
                RowForShortText(label = "Weight:", text = "${cat?.weight}")
                RowForShortText(label = "Rare:", text = if(cat?.rare == 0) "No" else "Yes")
                RowForShortText(label = "Wikipedia:", text = "${cat?.wikipedia_url}")



                ColumnForLongText(label = "Temperament:", text = "${cat?.temperament}")

                Spacer(modifier = Modifier.padding(10.dp))

                CharacteristicWithProgressIndicator(
                    label = "Adaptability",
                    progress = cat?.adaptability?.toFloat() ?: 0f
                )
                CharacteristicWithProgressIndicator(
                    label = "Affection Level",
                    progress = cat?.affectionLevel?.toFloat() ?: 0f
                )
                CharacteristicWithProgressIndicator(
                    label = "Child Friendly",
                    progress = cat?.childFriendly?.toFloat() ?: 0f
                )
                CharacteristicWithProgressIndicator(
                    label = "Dog Friendly",
                    progress = cat?.dogFriendly?.toFloat() ?: 0f
                )
                CharacteristicWithProgressIndicator(
                    label = "Energy Level",
                    progress = cat?.energyLevel?.toFloat() ?: 0f
                )

                ColumnForLongText(label = "Description:", text = "${cat?.description}")
                Spacer(modifier = Modifier.padding(20.dp))

            }
        }
    )
}

@Composable
fun CharacteristicWithProgressIndicator(label: String, progress: Float) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Text(
            text = label,
            style = TextStyle(
                fontFamily = Samsung,
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )
        )
        val strength = progress / 5
        LinearProgressIndicator(

            progress = strength,
            color =
            if(strength >= 0.6f)
                Color.hsl(135f, 0.64f, 0.64f)
            else
                Color.hsl(0f, 0.64f, 0.64f),

            trackColor = Color(0xFFE0E0E0),
            modifier = Modifier
                .padding(top = 10.dp)
            
        )
    }
}

@Composable
fun ColumnForLongText(label: String, text: String) {
    Spacer(modifier = Modifier.padding(10.dp))
    Column {
        Text(
            modifier = Modifier
                .padding(start = 15.dp, bottom = 10.dp),
            text = label,
            fontSize = 20.sp,
            color = orange,
            fontWeight = FontWeight.Medium
        )
        Text(
            modifier = Modifier
                .padding(start = 15.dp, bottom = 10.dp),
            text = text,
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
fun RowForShortText(label: String, text: String) {
    Row {
        Text(
            modifier = Modifier
                .padding(start = 15.dp, bottom = 10.dp)
                .weight(0.5f),
            text = label,
            fontSize = 20.sp,
            color = orange,
            fontWeight = FontWeight.Medium
        )
        if(text.contains("http")) {
            Text(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .weight(1f),
                text = text,
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                style = TextStyle(
                    color = Color.hsl(221f, 0.50f, 0.60f),
                    fontFamily = Samsung,
                    textDecoration = TextDecoration.Underline
                )
            )
        } else {
            Text(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .weight(1f),
                text = text,
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal
            )
        }


    }
}


@Composable
@Preview
fun DetailsScreenPreview() {
    CatalistTheme {
        DetailsScreen(
            catId = "1",
            onBack = {}
        )
    }
}