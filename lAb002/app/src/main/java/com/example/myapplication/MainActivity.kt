package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.ui.unit.dp
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainActivityScreen()
            }
        }
    }
}

@Composable
fun MainActivityScreen() {
    val context = LocalContext.current
    val itemList = remember { mutableStateListOf<Item>() }
    var lastId = remember { mutableStateOf(loadLastId(context)) }

    LaunchedEffect(Unit) {
        itemList.addAll(loadItems(context))
    }
    LaunchedEffect(itemList) {
        saveLastId(context, lastId.value)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE7E8D1))
            .padding(top = 50.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE7E8D1))
                .drawBehind {
                    val strokeWidth = 1 * density
                    val y = size.height - strokeWidth / 2
                    drawLine(
                        Color.Black,
                        Offset(2f, y),
                        Offset(size.width, y),
                        strokeWidth
                    )
                },
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Text(
                text = "#",
                modifier = Modifier
                    .weight(1.3f)
                    .padding(start = 20.dp),
                color = Color.Black
            )
            Text(
                text = "Task Name",
                modifier = Modifier.weight(3f),
                color = Color.Black
            )
            Text(
                text = "Status",
                modifier = Modifier.weight(1f),
                color = Color.Black
            )
            Text(
                text = "Remove",
                modifier = Modifier
                    .weight(1.2f),
                color = Color.Black,
                maxLines = 1
            )
        }

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE7E8D1))
                .weight(7.0f)
        ) {
            items(itemList, key = { it.id }) { item ->

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(24.dp)
                        )
                        .padding(8.dp)
                ) {

                    Text(
                        text = item.id,
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically)
                            .padding(start = 8.dp)
                    )

                    Text(
                        text = item.name,
                        modifier = Modifier
                            .weight(4f)
                            .align(Alignment.CenterVertically)
                            .padding(horizontal = 16.dp)
                            .background(Color.Transparent),
                        maxLines = 5
                    )

                    Checkbox(
                        checked = item.isChecked,
                        onCheckedChange = { newState ->
                            val updatedItem = item.copy(isChecked = newState)
                            val index = itemList.indexOf(item)
                            itemList[index] = updatedItem
                            saveItems(context, itemList)
                        },
                        colors = CheckboxDefaults.colors(checkedColor = Color(0xFFA7BEAE)),
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    )

                    Button(
                        onClick = {
                            // remove element and reassign ids
                            itemList.remove(item)
                            itemList.forEachIndexed { index, currentItem ->
                                currentItem.id = (index + 1).toString() // update id for each elements
                            }
                            lastId.value = itemList.size // update lastId after remove
                            saveItems(context, itemList)
                        },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB85042), ),
                        modifier = Modifier
                            .align(Alignment.CenterVertically) // Вирівнювання по центру


                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            modifier = Modifier
                                .size(20.dp)
                        )
                    }
                }

            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .weight(2.0f)
                .background(Color(0xFFE7E8D1))
                .drawBehind {
                    val strokeWidth = 1.dp.toPx()
                    val y = 0f

                    drawLine(
                        Color.Black,
                        Offset(0f,y),
                        Offset(size.width,y),
                        strokeWidth
                    )
                }

        ) {
            val textFieldName = remember { mutableStateOf("") }

            TextField(
                value = textFieldName.value,
                onValueChange = { newName -> textFieldName.value = newName },
                shape = CircleShape,
                placeholder = {Text("Enter task name")},
                colors = TextFieldDefaults.colors().copy(
                    focusedContainerColor = Color(0xFF98ad9e),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedContainerColor = Color(0xFF98ad9e),
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 75.dp)
                    .padding(3.dp)
            )
            Button(
                onClick = {
                    lastId.value++
                    val newItem = Item(id = lastId.value.toString(),
                        name = textFieldName.value)
                    itemList.add(newItem)
                    saveItems(context, itemList)
                    saveLastId(context, lastId.value)
                    textFieldName.value = ""
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA7BEAE)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                Text(text = "Add Task")
            }
        }
    }
}

data class Item(var id: String, val name: String, var isChecked: Boolean = false)
// funcs for save data and id by sharedPreferences and transform to JSON
fun saveItems(context: Context, items: List<Item>) {
    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    val gson = Gson()
    val json = gson.toJson(items)
    editor.putString("itemList", json)
    editor.apply()
}

fun loadItems(context: Context): MutableList<Item> {
    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val json = sharedPreferences.getString("itemList", null)
    val gson = Gson()
    val type = object : TypeToken<MutableList<Item>>() {}.type
    return gson.fromJson(json, type) ?: mutableListOf()
}
// funcs to load data from file
fun saveLastId(context: Context, lastId: Int) {
    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putInt("lastId", lastId)
    editor.apply()
}

fun loadLastId(context: Context): Int {
    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    return sharedPreferences.getInt("lastId", 0) // default ID - 0
}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    MyApplicationTheme {
        MainActivityScreen()
    }
}