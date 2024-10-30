package com.example.lab_07.ui.mealsdetail.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.lab_07.R
import com.example.lab_07.networking.response.details.Details

@Composable
fun MealDetail(detail: Details, navController: NavController){

    Surface{
        Image(
            painter = rememberAsyncImagePainter(detail.urlImage),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .height(300.dp)
                .background(shape = MaterialTheme.shapes.extraLarge, color = Color.Black)
        )
        Column(modifier = Modifier.fillMaxSize()
        ){
            Spacer(modifier = Modifier.height(100.dp))
            Column {

                Text(text = detail.name,

                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .background(color = Color.Black),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineLarge)

                Spacer(modifier = Modifier.height(200.dp))

                Text(text = detail.instructions,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.bodyMedium
                )

            }
        }
    }
}

