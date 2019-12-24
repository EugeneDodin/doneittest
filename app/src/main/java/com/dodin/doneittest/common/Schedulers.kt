package com.dodin.doneittest.common

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object Schedulers {
    fun network() = Schedulers.io()
    fun ui() = AndroidSchedulers.mainThread()
}