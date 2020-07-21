package org.shao.mvvmsample.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import org.shao.mvvmsample.model.RandomCatFactModel
import org.shao.mvvmsample.model.RandomCatFactService
import org.shao.mvvmsample.utility.async

class CatFactsViewModel(private val retrofitService: RandomCatFactService) : ViewModel() {
    val loading = MutableLiveData<Boolean>()
    val text = MutableLiveData<String>()
    private val error = MutableLiveData<Throwable>()

    init {
        loading.value = false
    }

    // Single is a commonly used class in RxJava. It is very similar with Observable but only emits success or errors
    fun loadRandomCatFact() : Single<RandomCatFactModel> =
        retrofitService.getRandomCatFact()
            .async(1000)
            .doOnSuccess { t: RandomCatFactModel? ->
                t?.let {
                    text.postValue(it.text)
                    //More data can be set here if needed. For example the "user" and "createAt".
                }
            }
            .doOnSubscribe { startLoading()}
            .doAfterTerminate { finishLoading() }
            .doOnError { error }

    // When loading, show the progress bar until the loading is finished
    private fun startLoading() = loading.postValue(true)
    private fun finishLoading() = loading.postValue(false)
}