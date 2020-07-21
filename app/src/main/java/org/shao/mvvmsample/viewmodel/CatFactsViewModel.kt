package org.shao.mvvmsample.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import org.shao.mvvmsample.model.RandomCatFactModel
import org.shao.mvvmsample.model.RandomCatFactService
import org.shao.mvvmsample.utility.async

class CatFactsViewModel(private val remote: RandomCatFactService) : ViewModel() {
    val loading = MutableLiveData<Boolean>()
    val text = MutableLiveData<String>()
    val error = MutableLiveData<Throwable>()

    init {
        loading.value = false
    }

    fun loadRandomCatFact() : Single<RandomCatFactModel> =
        remote.getRandomCatFact()
            .async(1000)
            .doOnSuccess { t: RandomCatFactModel? ->
                t?.let {
                    text.postValue(it.text)
                    //More data can be set here if needed. For example the "user" and "createAt".
                }
            }
            .doOnSubscribe { startLoading()}
            .doAfterTerminate { finishLoading() }

    private fun startLoading() = loading.postValue(true)
    private fun finishLoading() = loading.postValue(false)
}