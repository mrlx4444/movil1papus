package com.example.bibliotecnm

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.bibliotecnm.ui.theme.BibliotecnmTheme

data class LibroRecomendado(
    val titulo: String,
    val autor: String,
    val generos: String,
    val anio: String,
    val disponible: Boolean
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BibliotecnmTheme {
                PantallaPrincipal()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPrincipal() {
    var textSearch by remember { mutableStateOf("") }
    var activeSearch by remember { mutableStateOf(false) }
    val context = LocalContext.current


    var libroSeleccionado by remember { mutableStateOf<LibroRecomendado?>(null) }
    var mostrarDialogo by remember { mutableStateOf(false) }


    val listaRecomendados = listOf(
        LibroRecomendado("El Alquimista", "Paulo Coelho", "Ficción, Filosofía", "1988", true),
        LibroRecomendado("1984", "George Orwell", "Distopía, Ciencia Ficción", "1949", false),
        LibroRecomendado("Sapiens", "Yuval Noah Harari", "Historia, Antropología", "2011", true),
        LibroRecomendado("El Principito", "Antoine de Saint-Exupéry", "Literatura Infantil, Fantasía", "1943", true),
        LibroRecomendado("Drácula", "Bram Stoker", "Terror, Gótico", "1897", false)
    )

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Buscador
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchBar(
                    modifier = Modifier.weight(1f),
                    query = textSearch,
                    onQueryChange = { textSearch = it },
                    onSearch = { activeSearch = false },
                    active = activeSearch,
                    onActiveChange = { activeSearch = it },
                    placeholder = { Text("Buscar...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    trailingIcon = {
                        if (activeSearch) {
                            IconButton(onClick = { if (textSearch.isNotEmpty()) textSearch = "" else activeSearch = false }) {
                                Icon(Icons.Default.Close, contentDescription = null)
                            }
                        }
                    }
                ) {
                    Text(text = "Sugerencias...", modifier = Modifier.padding(16.dp))
                }

                if (!activeSearch) {
                    IconButton(
                        onClick = {
                            val intent = Intent(context, PerfilActivity::class.java)
                            context.startActivity(intent)
                        },
                        modifier = Modifier.padding(start = 4.dp, top = 8.dp)
                    ) {
                        Icon(Icons.Default.Person, contentDescription = "Ir al perfil")
                    }
                }
            }


            if (!activeSearch) {
                Text(
                    text = "Títulos Recomendados",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
                )

                // Lista vertical
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                ) {
                    items(listaRecomendados) { libro ->
                        ItemLibroRecomendado(
                            libro = libro,
                            onAgregarClick = {
                                libroSeleccionado = libro
                                mostrarDialogo = true
                            }
                        )
                    }
                }
            }
        }

        //Cuadro de diálogo
        if (mostrarDialogo && libroSeleccionado != null) {
            Dialog(onDismissRequest = { mostrarDialogo = false }) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // Parte superior
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Cuadro para la foto del libro
                            Box(
                                modifier = Modifier
                                    .size(70.dp, 100.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(MaterialTheme.colorScheme.primaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Foto", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onPrimaryContainer)
                            }

                            Spacer(modifier = Modifier.width(16.dp))


                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = libroSeleccionado!!.titulo, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                                Text(text = libroSeleccionado!!.autor, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Parte inferior
                        Text(
                            text = if (libroSeleccionado!!.disponible) "Disponible: SÍ" else "Disponible: NO",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = if (libroSeleccionado!!.disponible) Color(0xFF2E7D32) else MaterialTheme.colorScheme.error
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Botones de acción
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(onClick = { mostrarDialogo = false }) {
                                Text("Cancelar")
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = { /* Estructura vacía temporal */ },
                                enabled = libroSeleccionado!!.disponible // Se deshabilita si no está disponible
                            ) {
                                Text("Solicitar Préstamo")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemLibroRecomendado(libro: LibroRecomendado, onAgregarClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(60.dp, 85.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Text("Libro", style = MaterialTheme.typography.labelSmall, color = Color.DarkGray)
            }
            Spacer(modifier = Modifier.width(12.dp))
// Textos del libro
            Column(modifier = Modifier.weight(1f)) {
                Text(text = libro.titulo, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(text = "Autor: ${libro.autor}", style = MaterialTheme.typography.bodySmall)
                Text(text = "Géneros: ${libro.generos}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(text = "Año: ${libro.anio}", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            }
// Botón: más opciones libro
            IconButton(onClick = onAgregarClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Ver detalles de préstamo",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PantallaPrincipalPreview() {
    BibliotecnmTheme {
        PantallaPrincipal()
    }
}