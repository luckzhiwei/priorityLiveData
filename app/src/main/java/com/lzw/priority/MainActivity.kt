package com.lzw.priority

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.MutableLiveData
import com.lzw.library.livedata.PriorityLiveData
import com.lzw.library.livedata.PriorityObserver

class MainActivity : AppCompatActivity() {

    private val liveDataTest by lazy {
        val ret = PriorityLiveData<Int>()
        ret
    }

    private val buttonTestOnResumeReg: Button by lazy {
        findViewById<Button>(R.id.btn_test_onresume_reg)
    }

    private val buttonTestObserveForever: Button by lazy {
        findViewById<Button>(R.id.btn_test_observer_forever)
    }

    private val buttonTestPostValue: Button by lazy {
        findViewById<Button>(R.id.btn_test_post_value)
    }

    private val buttonTestMixTwoTypeObserver: Button by lazy {
        findViewById<Button>(R.id.btn_mix_two_type_observer)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
        initListener()
    }

    private fun initListener() {
        buttonTestOnResumeReg.setOnClickListener {
            val intent = Intent(this@MainActivity, TestOnResumeActivity::class.java)
            Log.d(PriorityLiveData.TAG, "start activity on resume reg ================")
            startActivity(intent)
        }
        buttonTestObserveForever.setOnClickListener {
            val intent = Intent(this@MainActivity, TestObserveForeverActivity::class.java)
            Log.d(PriorityLiveData.TAG, "start activity test observe forever ================")
            startActivity(intent)
        }
        buttonTestPostValue.setOnClickListener {
            val intent = Intent(this@MainActivity, TestPostValueActivity::class.java)
            Log.d(PriorityLiveData.TAG, "start activity test post value ================")
            startActivity(intent)
        }
        buttonTestMixTwoTypeObserver.setOnClickListener {
            val intent = Intent(this@MainActivity, TestMixTwoTypeObserverActivity::class.java)
            Log.d(PriorityLiveData.TAG, "start activity test mix two type observer ================")
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        liveDataTest.value = 6
    }
}
