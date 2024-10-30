package com.example.lab_07.ui.supermarket.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.lab_07.database.categories.SupermarketItemEntity
import com.example.lab_07.ui.supermarket.repository.SuperMarketRepository
import java.io.File
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SuperMarketViewModel(private val repository: SuperMarketRepository) : ViewModel() {

    // Estado de los artículos de la lista de supermercado
    private val _items = MutableStateFlow<List<SupermarketItemEntity>>(emptyList())
    val items: StateFlow<List<SupermarketItemEntity>> = _items

    // Estados de carga y error
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        loadItems() // Carga inicial de los artículos cuando se inicia el ViewModel
    }

    // Método para cargar todos los artículos de la lista
    private fun loadItems() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _items.value = repository.getAllItems()
            } catch (e: Exception) {
                _errorMessage.value = "Error loading items"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Método para agregar un nuevo artículo
    fun addItem(itemName: String, quantity: Int, imagePath: String? = null) {
        val newItem = SupermarketItemEntity(
            itemName = itemName,
            quantity = quantity,
            imagePath = imagePath
        )

        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.insertItem(newItem)
                _items.value = _items.value + newItem // Optimiza al añadir el nuevo ítem localmente
            } catch (e: Exception) {
                _errorMessage.value = "Error adding item"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Método para actualizar un artículo existente
    fun updateItem(
        itemId: Int,
        newName: String,
        newQuantity: Int,
        newImagePath: String? = null
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val item = repository.getItemById(itemId)
                if (item != null) {
                    val updatedItem = item.copy(
                        itemName = newName,
                        quantity = newQuantity,
                        imagePath = newImagePath ?: item.imagePath
                    )
                    repository.updateItem(updatedItem)
                    _items.value = _items.value.map { if (it.id == itemId) updatedItem else it }
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error updating item"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Método para eliminar un artículo
    fun deleteItem(item: SupermarketItemEntity) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.deleteItem(item)

                // Eliminar la imagen del sistema de archivos si existe
                item.imagePath?.let {
                    val imageFile = File(it)
                    if (imageFile.exists()) {
                        imageFile.delete()
                    }
                }

                _items.value = _items.value - item // Optimiza al eliminar el ítem localmente
            } catch (e: Exception) {
                _errorMessage.value = "Error deleting item"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Método para obtener un artículo específico por su ID
    fun getItemById(itemId: Int): SupermarketItemEntity? {
        var item: SupermarketItemEntity? = null
        viewModelScope.launch {
            try {
                item = repository.getItemById(itemId)
            } catch (e: Exception) {
                _errorMessage.value = "Error fetching item"
            }
        }
        return item
    }
}

class SuperMarketViewModelFactory(
    private val repository: SuperMarketRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SuperMarketViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SuperMarketViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}