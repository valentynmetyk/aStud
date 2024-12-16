package com.lab6.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityInputDialog(
    currentCity: String,
    onCityChanged: (String) -> Unit,
    onDismiss: () -> Unit,
    onSubmit: () -> Unit
) {

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            color = Color(0xFF99DBF1)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color(0xFF99DBF1)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Enter City",
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                TextField(
                    value = currentCity,
                    onValueChange = onCityChanged,
                    label = { Text("City Name", color = Color.White) },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(0xFF0788B4),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = onSubmit,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF0788B4)
                        ),
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        Text("Change", color = Color.White)
                    }
                    Button(
                        onClick = onDismiss,

                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF03303F)
                        ),
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        Text("Cancel", color = Color.White)
                    }
                }
            }
        }
    }
}
