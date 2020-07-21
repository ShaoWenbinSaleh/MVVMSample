package org.shao.mvvmsample.model

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

// The interface required by Retrofit, functioned as a service for the network communication
interface RandomCatFactService {
    @GET("facts/random")
    fun getRandomCatFact() : Single<RandomCatFactModel>
    //get the specific cat fact through the fact id
    fun getCatFact(@Query("id")id: String): Single<RandomCatFactModel>
}