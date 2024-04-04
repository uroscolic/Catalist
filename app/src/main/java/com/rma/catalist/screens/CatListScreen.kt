package com.rma.catalist.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedSuggestionChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rma.catalist.R
import com.rma.catalist.model.CatInfo
import com.rma.catalist.repository.Repository
import com.rma.catalist.ui.theme.CatalistTheme
import com.rma.catalist.ui.theme.Samsung


@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@ExperimentalMaterial3Api
fun CatListScreen(
    cats: List<CatInfo>,
    onCatSelected: (CatInfo) -> Unit
) {
    var data by remember { mutableStateOf(cats) }
    val keyboard = LocalSoftwareKeyboardController.current
    Scaffold(
        topBar = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.background(Color(0xFFFFFBFE))
            ) {
                Row() {

                    CenterAlignedTopAppBar(
                        //modifier = Modifier.background(Color.White),
                        navigationIcon = {
                            Image(
                                painter = painterResource(id = R.drawable.cat),
                                contentDescription = "Logo",
                                modifier = Modifier
                                    .weight(0.1f)
                                    .padding(start = 8.dp)
                                    .size(24.dp)
                            )
                        },
                        title = {
                            Text(
                                text = "Cat List",
                                fontFamily = Samsung,
                                fontWeight = FontWeight.Medium,
                                fontSize = 24.sp
                            )
                        }
                    )

                }
                Divider()
                var query by remember { mutableStateOf("") }
                SearchBar(
                    modifier = Modifier
                        .padding(bottom = 4.dp),
                    query = query,
                    onQueryChange = {
                        query = it
                        data = Repository.search(query)
                    },
                    onSearch = {
                        keyboard?.hide()
                    },
                    active = false,
                    onActiveChange = {},
                    placeholder = {
                        Text(
                            "Search",
                            modifier = Modifier.alpha(0.6f),
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    query = ""
                                    data = Repository.search(query)
                                }
                        )
                    },
                ) {}
                Spacer(modifier = Modifier.padding(2.dp))


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

                data.forEach { cat ->
                    Column {
                        key (cat.id){
                            CatListItem(
                                cat = cat,
                                onCatSelected = onCatSelected,
                                color = Color.hsl(23f, 0.9f, 0.5f)
                            )
                            Spacer(
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun CatListItem(
    cat: CatInfo,
    onCatSelected: (CatInfo) -> Unit,
    color: Color
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .clickable {
                onCatSelected(cat)
            },
    )
    {
        val alternativeName = if (cat.alternativeName.isNotEmpty()) " (" + cat.alternativeName + ")" else ""
        Text(
            modifier = Modifier
            .padding(all = 16.dp),
            color = color,
            text = cat.name + alternativeName,
            fontSize = 20.sp,
            //fontWeight = FontWeight.Bold
        )
        Row() {
            Text(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 16.dp),
                text = cutToNCharacters(250, cat.description),
                fontSize = 18.sp
            )
        }
        val text = cat.temperament.split(",")
        Row() {

            for (i in 0 until 3) {
                val leftPadding = if (i == 0) 10.dp else 5.dp
                ElevatedSuggestionChip(
                    modifier = Modifier
                        .padding(start = leftPadding),
                    onClick = {},
                    label = { Text(text[i]) },
                )
            }
        }
        Row()
        {
            Box(
                Modifier.weight(0.1f),
                contentAlignment = Alignment.CenterEnd
            )
            {
                Icon(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .padding(top = 10.dp)
                        .padding(bottom = 10.dp),
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Arrow Forward",
                    tint = color,
                )
            }
        }


    }

}
private fun cutToNCharacters(n:Int, text: String): String {
    if(text.length > n)
        return text.substring(0, n) + "..."
    return text
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CatListScreenPreview() {
    CatalistTheme {
        CatListScreen(
            cats = Repository.allData(),

        ) {}
    }
}