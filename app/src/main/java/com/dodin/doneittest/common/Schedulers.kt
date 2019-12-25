package com.dodin.doneittest.common

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class Schedulers {
    fun network() = Schedulers.io()
    fun ui() = AndroidSchedulers.mainThread()
}