package org.shao.mvvmsample.utility

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

fun <T> Single<T>.async(withDelay: Long = 0): Single<T> =
    this.subscribeOn(Schedulers.io())
        .delay(withDelay, TimeUnit.MILLISECONDS)