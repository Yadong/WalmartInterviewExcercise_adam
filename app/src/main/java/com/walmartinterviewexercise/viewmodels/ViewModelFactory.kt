package com.walmartinterviewexercise.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.walmartinterviewexercise.api.APIService
import com.walmartinterviewexercise.model.CountryManager

class ViewModelFactory(private val apiService: APIService): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(CountryManager(apiService)) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }
}