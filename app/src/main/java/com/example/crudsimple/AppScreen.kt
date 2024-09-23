@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.crudsimple

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import com.example.crudsimple.ui.theme.CrudSimpleTheme

@Composable
fun AppScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val db = AppDb.getDatabase(context)
    val dao = db.AppDao()

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var dataRecords by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Gestion") },
                actions = {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                val record = App(name = name, description = description, type = type)
                                dao.insert(record)
                                name = ""
                                description = ""
                                type = ""
                            }
                        }
                    ) {
                        Text("Agregar racha")
                    }
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                dataRecords = getRecords(dao)
                            }
                        }
                    ) {
                        Text("Lista de rachas")
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Spacer(Modifier.height(16.dp))
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre") },
                    singleLine = true
                )
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Decripcion") },
                    singleLine = true
                )
                TextField(
                    value = type,
                    onValueChange = { type = it },
                    label = { Text("Tipo (Comida/ejercicio)") },
                    singleLine = true
                )
                Spacer(Modifier.height(16.dp))
                Text(text = dataRecords, fontSize = 16.sp)
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = {
                        coroutineScope.launch {
                            val lastRecord = dao.getLastRecord()
                            if (lastRecord != null) {
                                dao.delete(lastRecord)
                            } else {
                                Log.e("Rachas", "No hay rachas para eliminar")
                            }
                        }
                    }
                ) {
                    Text("Eliminar el último registro", fontSize = 16.sp)
                }
            }
        }
    )
}

suspend fun getRecords(dao: AppDao): String {
    var result = ""
    val records = dao.getAll()
    records.forEach { record ->
        val row = "${record.name} - ${record.description} - ${record.type}\n"
        result += row
    }
    return result
}

@Preview(showBackground = true)
@Composable
fun AppScreenPreview() {
    CrudSimpleTheme {
        AppScreen()
    }
}