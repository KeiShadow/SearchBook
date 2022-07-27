package com.noga.booksearching.repository

import androidx.lifecycle.LiveData
import com.noga.booksearching.LoadingState
import com.noga.booksearching.api.BookRestApi
import com.noga.booksearching.models.BookApiResponse
import com.noga.booksearching.models.ItBookDetail
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

object MainRepository {
    var completableJob: CompletableJob? = null

    fun searchBooks(query: String, page: String? = null): LiveData<BookApiResponse> {
        completableJob = Job()
        return object: LiveData<BookApiResponse>(){
            override fun onActive() {
                super.onActive()

                completableJob?.let { job ->
                    CoroutineScope(IO + job).launch {
                        val response = BookRestApi.apiService.searchBooks(query)
                        withContext(Main){
                            value = response
                            job.complete()
                        }
                    }

                }
            }
        }

    }

    fun getNewBooks(): LiveData<BookApiResponse>{
        completableJob = Job()
        return object: LiveData<BookApiResponse>(){
            override fun onActive() {
                super.onActive()
                completableJob?.let { job ->
                    CoroutineScope(IO  + job).launch {
                        val response = BookRestApi.apiService.getNewBooks()
                        withContext(Main){
                            value = response
                            job.complete()
                        }
                    }

                }
            }
        }
    }

    fun getBookDetail(isbn13: String): LiveData<ItBookDetail>{
        completableJob = Job()
        return object: LiveData<ItBookDetail>(){
            override fun onActive() {
                super.onActive()
                completableJob?.let { job ->
                    CoroutineScope(IO + job).launch {
                        val response = BookRestApi.apiService.getBookDetail(isbn13)
                        withContext(Main){
                            value = response
                            job.complete()
                        }
                    }
                }
            }
        }
    }


    fun cancelJobs(){
        completableJob?.cancel()
    }
}