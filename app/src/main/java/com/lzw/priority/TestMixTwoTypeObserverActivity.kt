package com.lzw.priority

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.lzw.library.livedata.PriorityLiveData
import com.lzw.library.livedata.PriorityObserver

class TestMixTwoTypeObserverActivity : FragmentActivity() {
    private val liveDataTest by lazy {
        PriorityLiveData<Int>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty_view)
        liveDataTest.observe(this, object : PriorityObserver<Int> {
            override fun getPriority(): Int {
                return 0
            }

            override fun onChanged(t: Int?) {
                Log.d(PriorityLiveData.TAG, "the observer priority ${getPriority()} value is $t")
            }

        })
        liveDataTest.observeForever(object : PriorityObserver<Int> {
            override fun getPriority(): Int {
                return 1
            }

            override fun onChanged(t: Int?) {
                Log.d(PriorityLiveData.TAG, "the observer priority ${getPriority()} value is $t")
            }
        })
    }


    override fun onResume() {
        super.onResume()
        liveDataTest.value = 3
    }

}