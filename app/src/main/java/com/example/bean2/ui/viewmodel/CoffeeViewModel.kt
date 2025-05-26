package com.example.bean2.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bean2.data.model.CoffeeBag
import com.example.bean2.data.model.CoffeeType
import com.example.bean2.data.repository.CoffeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoffeeViewModel @Inject constructor(
    private val repository: CoffeeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<CoffeeUiState>(CoffeeUiState.Loading)
    val uiState: StateFlow<CoffeeUiState> = _uiState

    private val _selectedType = MutableStateFlow<CoffeeType?>(null)
    val selectedType: StateFlow<CoffeeType?> = _selectedType

    private val _selectedCoffeeBag = MutableStateFlow<CoffeeBag?>(null)
    val selectedCoffeeBag: StateFlow<CoffeeBag?> = _selectedCoffeeBag

    init {
        loadCoffeeBags()
    }

    fun setSelectedType(type: CoffeeType?) {
        _selectedType.value = type
        loadCoffeeBags(type)
    }

    private var selectedCoffeeBagJob: Job? = null
    
    fun selectCoffeeBag(id: String) {
        selectedCoffeeBagJob?.cancel()
        selectedCoffeeBagJob = viewModelScope.launch {
            repository.getCoffeeBagByIdFlow(id)
                .collect { coffeeBag ->
                    _selectedCoffeeBag.value = coffeeBag
                }
        }
    }

    fun addCoffeeBag(coffeeBag: CoffeeBag) {
        viewModelScope.launch {
            repository.addCoffeeBag(coffeeBag)
            loadCoffeeBags(_selectedType.value)
        }
    }

    fun updateCoffeeBag(coffeeBag: CoffeeBag) {
        viewModelScope.launch {
            repository.updateCoffeeBag(coffeeBag)
            loadCoffeeBags(_selectedType.value)
        }
    }

    fun deleteCoffeeBag(id: String) {
        viewModelScope.launch {
            repository.deleteCoffeeBag(id)
            loadCoffeeBags(_selectedType.value)
        }
    }

    private fun loadCoffeeBags(type: CoffeeType? = null) {
        viewModelScope.launch {
            _uiState.value = CoffeeUiState.Loading
            try {
                repository.getCoffeeBagsByType(type).collectLatest { coffeeBags ->
                    _uiState.value = CoffeeUiState.Success(coffeeBags)
                }
            } catch (e: Exception) {
                _uiState.value = CoffeeUiState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }
}

sealed class CoffeeUiState {
    object Loading : CoffeeUiState()
    data class Success(val coffeeBags: List<CoffeeBag>) : CoffeeUiState()
    data class Error(val message: String) : CoffeeUiState()
}
