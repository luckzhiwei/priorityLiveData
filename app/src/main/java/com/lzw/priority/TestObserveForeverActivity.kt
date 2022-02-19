package com.lzw.priority

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import com.lzw.library.livedata.PriorityLiveData
import com.lzw.library.livedata.PriorityObserver

class TestObserveForeverActivity : FragmentActivity() {
    private val liveDataTest by lazy {
        val ret = MutableLiveData<Int>()
        ret.value = 2
        ret
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty_view)
        liveDataTest.observeForever(object : PriorityObserver<Int> {
            override fun getPriority(): Int {
                return 0
            }

            override fun onChanged(t: Int?) {
                Log.d(PriorityLiveData.TAG, "the observer priority 0 value is $t")
            }

        })
    }
}