package com.example.bibliotecnm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bibliotecnm.ui.theme.BibliotecnmTheme

data class Prestamo(
    val titulo: String,
    val autor: String,
    val fechaDevolucion: String
)

class PerfilActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BibliotecnmTheme {
                PantallaPerfil()
            }
        }
    }
}

@Composable
fun PantallaPerfil() {
    val listaPrestamos = listOf(
        Prestamo("Cien años de soledad", "Gabriel García Márquez", "Devolver el: 15 Oct 2026"),
        Prestamo("Don Quijote de la Mancha", "Miguel de Cervantes", "Devolver el: 22 Oct 2026"),
        Prestamo("El código Da Vinci", "Dan Brown", "Atrasado - Debería haberse devuelto: 01 Oct 2026"),
        Prestamo("Fundación", "Isaac Asimov", "Devolver el: 05 Nov 2026")
    )

    var menuExpandido by remember { mutableStateOf(false) }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Título superior
            Text(
                text = "Mis Préstamos",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Lista de préstamos
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(listaPrestamos) { prestamo ->
                    ItemPrestamo(prestamo = prestamo)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Contenedor para el botón inferior y su menú desplegable
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {

                OutlinedButton(
                    onClick = { menuExpandido = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Configuración"
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                    Text(text = "Configuración de Perfil")
                }


                DropdownMenu(
                    expanded = menuExpandido,
                    onDismissRequest = { menuExpandido = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Cambiar información") },
                        leadingIcon = { Icon(Icons.Default.Edit, contentDescription = null) },
                        onClick = {
                            menuExpandido = false
                            /* TODO: Agregar navegación o lógica en el futuro */
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Cerrar sesión") },
                        leadingIcon = { Icon(Icons.Default.ExitToApp, contentDescription = null) },
                        onClick = {
                            menuExpandido = false
                            /* TODO: Agregar lógica de cierre de sesión en el futuro */
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ItemPrestamo(prestamo: Prestamo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
            Text(text = prestamo.titulo, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text(text = "Autor: ${prestamo.autor}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = prestamo.fechaDevolucion,
                style = MaterialTheme.typography.bodySmall,
                color = if (prestamo.fechaDevolucion.contains("Atrasado")) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaPerfilPreview() {
    BibliotecnmTheme {
        PantallaPerfil()
    }
}
