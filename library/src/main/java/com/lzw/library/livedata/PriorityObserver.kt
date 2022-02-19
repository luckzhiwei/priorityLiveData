package com.lzw.library.livedata

import androidx.lifecycle.Observer

interface PriorityObserver<T> : Observer<T> {

    fun getPriority(): Int
}