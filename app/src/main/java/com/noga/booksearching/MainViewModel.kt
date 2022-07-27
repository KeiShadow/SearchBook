package com.noga.booksearching

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.noga.booksearching.models.BookApiResponse
import com.noga.booksearching.models.ItBook
import com.noga.booksearching.recycleview.BookAdapter
import com.noga.booksearching.repository.MainRepository

class MainViewModel: ViewModel() {

    private val _loading: MutableLiveData<LoadingState> = MutableLiveData()
    private val _query: MutableLiveData<String> = MutableLiveData()


    val searchResponse: LiveData<BookApiResponse> = Transformations.switchMap(_query){ query ->
        MainRepository.searchBooks(query)
    }


    fun setQuery(query: String){
        val update = query
        if(update.equals(_query))
            return

        _query.value = update
    }

    fun cancelJobs(){
        MainRepository.cancelJobs()
    }
}

@Suppress("DataClassPrivateConstructor")
data class LoadingState private constructor(val status: Status, val msg: String? = null) {
    companion object {
        val LOADED = LoadingState(Status.SUCCESS)
        val LOADING = LoadingState(Status.RUNNING)
        fun error(msg: String?) = LoadingState(Status.FAILED, msg)
    }

    enum class Status {
        RUNNING,
        SUCCESS,
        FAILED
    }
}