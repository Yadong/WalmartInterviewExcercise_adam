package com.walmartinterviewexercise.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.walmartinterviewexercise.api.APIService

class ViewModelFactory(private val apiService: APIService): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(apiService) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }
}