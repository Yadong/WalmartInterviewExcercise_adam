package com.walmartinterviewexercise.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.walmartinterviewexercise.api.APIService
import com.walmartinterviewexercise.model.Country
import com.walmartinterviewexercise.ui.UIState
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class HomeViewModel(private val apiService: APIService): ViewModel() {

    private val fullFormListUIState = MutableLiveData<UIState<List<Country>>>()

    fun getCountryListUIState(): LiveData<UIState<List<Country>>> = fullFormListUIState

    fun getCountryList() {
        viewModelScope.launch {
            try {
                coroutineScope {
                    val list = apiService.getCountryList()
                    fullFormListUIState.postValue(UIState.Success(list))
                }
            } catch (e: java.lang.Exception) {
                fullFormListUIState.postValue(UIState.Error(e.localizedMessage.orEmpty()))
            }
        }
    }
}
