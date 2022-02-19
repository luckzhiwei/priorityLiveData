English | **[简体中文](README_zh.md)**

# PriorityLiveData

PriorityLiveData is based on Android's LiveData, and implements a priority mechanism for notifying observers. It solves the problem that the starting order of LiveData's Observer's onChanged function must depend on the calling (registration) sequence of LiveData's Observer function.


## Quick Start
*  todo 

## Use Example

```kotlin
class MainActivity : AppCompatActivity() {
     private val liveDataPriority by lazy {
        PriorityLiveData<Int>()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ....
        liveDataPriority.observe(this, object : PriorityObserver<Int> {
            //you need use observe with PriorityObserver and implement getPriority() function
            override fun getPriority(): Int {
                return 2
            }

            // is same with Observer
            override fun onChanged(t: Int?) {
                Log.d(PriorityLiveData.TAG, "the observer priority 2 value is $t")
            }

        })
        liveDataPriority.observe(this, object : PriorityObserver<Int> {
            override fun getPriority(): Int {
                return 1
            }

            override fun onChanged(t: Int?) {
                Log.d(PriorityLiveData.TAG, "the observer priority 1 value is $t")
            }
        })
    }

     override fun onResume() {
        super.onResume()
        liveDataPriority.value = 6
    }
     

}
```