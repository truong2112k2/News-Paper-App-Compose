package com.example.newspaperapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newspaperapp.data.repository.NewsPaperRepositoryImpl

class NewsPaperModelFactory(private val repository: NewsPaperRepositoryImpl) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsPaperViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewsPaperViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}