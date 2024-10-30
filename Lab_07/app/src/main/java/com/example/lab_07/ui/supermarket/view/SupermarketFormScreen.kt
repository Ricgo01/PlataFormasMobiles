package com.example.lab_07.ui.supermarket.view

import android.Manifest
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.lab_07.ui.supermarket.viewmodel.SuperMarketViewModel
import com.example.lab_07.database.categories.SupermarketItemEntity
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupermarketFormScreen(
    viewModel: SuperMarketViewModel,
    navController: NavController,
    item: SupermarketItemEntity? = null
) {
    var itemName by remember { mutableStateOf(item?.itemName ?: "") }
    var quantity by remember { mutableStateOf(item?.quantity?.toString() ?: "") }
    var imagePath by remember { mutableStateOf(item?.imagePath) }
    var showError by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Variable para almacenar la URI de la imagen capturada
    var capturedImageUri by remember { mutableStateOf<Uri?>(null) }

    // Lanzador para capturar imagen
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            // Si la captura de la imagen fue exitosa, actualizamos `imagePath` con la URI de la imagen
            imagePath = capturedImageUri?.toString()
        }
    }

    // Lanzador para solicitar permiso de cámara
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Crear archivo y lanzar la cámara si el permiso está concedido
            val imageFile = createImageFile(context)
            capturedImageUri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                imageFile
            )

            capturedImageUri?.let { uri ->
                cameraLauncher.launch(uri)
            }
        } else {
            // Muestra un mensaje si el permiso es denegado
            Toast.makeText(context, "Camera permission is required to take pictures", Toast.LENGTH_SHORT).show()
        }
    }

    // Solicita el permiso cuando se haga clic en el botón de captura de imagen
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(if (item == null) "Add Item" else "Edit Item") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (itemName.isNotBlank() && quantity.isNotBlank()) {
                    val qty = quantity.toIntOrNull() ?: 0
                    if (item == null) {
                        viewModel.addItem(itemName, qty, imagePath)
                    } else {
                        viewModel.updateItem(item.id, itemName, qty, imagePath)
                    }
                    navController.popBackStack()
                } else {
                    showError = true
                }
            }) {
                Icon(Icons.Default.Check, contentDescription = "Save Item")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = itemName,
                onValueChange = { itemName = it },
                label = { Text("Item Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = quantity,
                onValueChange = { quantity = it },
                label = { Text("Quantity") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Mostrar la imagen capturada o seleccionada
            if (imagePath != null) {
                Image(
                    painter = rememberAsyncImagePainter(imagePath),
                    contentDescription = null,
                    modifier = Modifier.size(128.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }) {
                Text("Capture Image")
            }

            if (showError) {
                Text(
                    text = "Please fill out all fields.",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}

// Función para crear un archivo de imagen en un directorio específico
fun createImageFile(context: Context): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
    val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
}

