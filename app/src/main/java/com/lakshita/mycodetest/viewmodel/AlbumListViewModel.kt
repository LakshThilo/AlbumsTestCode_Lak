package com.lakshita.mycodetest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.lakshita.mycodetest.repo.AlbumRepository
import kotlinx.coroutines.launch
import java.io.IOException

class AlbumListViewModel(repository: AlbumRepository) : ViewModel() {

    private val albumRepository: AlbumRepository = repository

    val albumList = albumRepository.albums
    var dataState = MutableLiveData<Boolean>()

    init {
      refreshDataFromRepository()
    }

    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                albumRepository.refreshAlbums()
                dataState.postValue(true)
            } catch (networkError: IOException) {
                if(albumList.value.isNullOrEmpty()) {
                    println(networkError.localizedMessage)
                    dataState.postValue(false)
                }
            }
        }
    }

    // ViewModel Factory
    class Factory(private val repository: AlbumRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumListViewModel(repository) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}