package com.rma.catalist.core.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextMessage(text: String) {
    Text(
        modifier = Modifier.padding(10.dp),
        text = text,
        fontSize = 20.sp
    )
}
@Composable
fun Loading()
{
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
    ) {
        Text(
            text = "Loading...",
        )
        LinearProgressIndicator(

            color = Color.hsl(23f, 0.8f, 0.65f),

            trackColor = Color(0xFFE0E0E0),
            modifier = Modifier
                .padding(top = 10.dp)

        )
    }
}
