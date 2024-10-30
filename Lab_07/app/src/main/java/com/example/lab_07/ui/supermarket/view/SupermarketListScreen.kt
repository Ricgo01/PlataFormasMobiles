package com.example.lab_07.ui.supermarket.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.lab_07.database.categories.SupermarketItemEntity
import com.example.lab_07.navigation.NavigationState
import com.example.lab_07.ui.supermarket.viewmodel.SuperMarketViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupermarketListScreen(
    viewModel: SuperMarketViewModel,
    navController: NavController,
    onNavigateToForm: (SupermarketItemEntity?) -> Unit
) {
    val items by viewModel.items.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Supermarket List") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(NavigationState.SupermarketListScreen.route) {
                            launchSingleTop = true
                        }
                    }) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Supermarket List")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onNavigateToForm(null) }) {
                Icon(Icons.Default.Add, contentDescription = "Add Item")
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            when {
                isLoading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
                errorMessage != null -> Text(
                    text = errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(items) { item ->
                            SupermarketItemRow(
                                item = item,
                                onEdit = { onNavigateToForm(it) },
                                onDelete = { viewModel.deleteItem(it) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SupermarketItemRow(
    item: SupermarketItemEntity,
    onEdit: (SupermarketItemEntity) -> Unit,
    onDelete: (SupermarketItemEntity) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onEdit(item) }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (item.imagePath != null) {
                Image(
                    painter = rememberAsyncImagePainter(item.imagePath),
                    contentDescription = null,
                    modifier = Modifier.size(64.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = item.itemName, style = MaterialTheme.typography.titleMedium)
                Text(text = "Quantity: ${item.quantity}", style = MaterialTheme.typography.bodySmall)
            }

            IconButton(onClick = { onDelete(item) }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete Item")
            }
        }
    }
}