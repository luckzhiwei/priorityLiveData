简体中文 | **[English](README.md)**

# PriorityLiveData

PriorityLiveData 是基于Android的LiveData的基础上，实现对观察者的通知带优先级的机制，解决了LiveData的Observer的onChanged函数出发顺序必须依赖LiveData的Observe函数的调用（注册）先后顺序的问题


## 快速上手

* 进行中,稍后公布

## 使用案例

```kotlin
class MainActivity : AppCompatActivity() {
     private val liveDataPriority by lazy {
        PriorityLiveData<Int>()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //...
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