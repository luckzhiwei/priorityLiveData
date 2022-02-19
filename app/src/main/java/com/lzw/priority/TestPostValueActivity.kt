package com.lzw.priority

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.lzw.library.livedata.PriorityLiveData
import com.lzw.library.livedata.PriorityObserver

class TestPostValueActivity : FragmentActivity() {
    private val liveDataTest by lazy {
        val ret = PriorityLiveData<Int>()
        ret
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty_view)
        liveDataTest.observe(this, object : PriorityObserver<Int> {
            override fun getPriority(): Int {
                return 2
            }

            override fun onChanged(t: Int?) {
                Log.d(PriorityLiveData.TAG, "the observer priority 2 value is $t")
            }

        })
        liveDataTest.observe(this, object : PriorityObserver<Int> {
            override fun getPriority(): Int {
                return 1
            }

            override fun onChanged(t: Int?) {
                Log.d(PriorityLiveData.TAG, "the observer priority 1 value is $t")
            }
        })
        val t = Thread(Runnable {
            liveDataTest.postValue(7)
        })
        t.start()
    }
}