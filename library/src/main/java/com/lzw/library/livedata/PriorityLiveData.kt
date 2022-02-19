package com.lzw.library.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.*

open class PriorityLiveData<T> : MutableLiveData<T>(), Observer<T> {

    companion object {
        const val TAG = "PriorityLiveData"
    }

    private val priorityMap by lazy {
        TreeMap<PriorityObserverWrapper, Observer<in T>>()
    }

    private var isActive = false


    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (observer is PriorityObserver) {
            val wrapper = PriorityObserverWrapper(observer.getPriority())
            if (priorityMap.containsKey(wrapper)) {
                throw IllegalArgumentException("priority should not duplicate")
            } else {
                if (priorityMap.isEmpty()) {
                    super.observe(owner, this)
                }
                priorityMap[wrapper] = observer
                if (isActive && value != null) {
                    observer.onChanged(value)
                }
            }
        } else {
            throw IllegalArgumentException(
                "the observer is not PriorityObserver," + "should use PriorityObserver"
            )
        }
    }

    override fun removeObserver(observer: Observer<in T>) {
        if (priorityMap.isEmpty()) {
            return
        }
        if (observer is PriorityObserver) {
            val priority = PriorityObserverWrapper(observer.getPriority())
            priorityMap.remove(priority)
        } else if (observer is PriorityLiveData) {
            super.removeObserver(this)
            priorityMap.clear()
        }
    }

    override fun observeForever(observer: Observer<in T>) {
        if (observer is PriorityObserver) {
            val wrapper = PriorityObserverWrapper(observer.getPriority(), true)
            if (priorityMap.containsKey(wrapper)) {
                throw IllegalArgumentException("priority should not duplicate")
            }
            if (priorityMap.isEmpty()) {
                super.observeForever(this)
            }
            if (value != null) {
                observer.onChanged(value)
            }
            priorityMap[wrapper] = observer
        } else {
            throw IllegalArgumentException(
                "the observer is not PriorityObserver," + "should use PriorityObserver"
            )
        }
    }


    override fun onActive() {
        if (!isActive) {
            isActive = true
        }
    }

    override fun onInactive() {
        if (isActive) {
            isActive = false
        }
    }

    override fun onChanged(t: T) {
        if (priorityMap.isEmpty()) {
            return
        }
        val it = priorityMap.iterator()
        while (it.hasNext()) {
            val entry = it.next()
            if (this.isActive || entry.key.isAlwaysActive) {
                entry.value.onChanged(t)
            }
        }
    }

    override fun hasActiveObservers(): Boolean {
        if (isActive && priorityMap.isNotEmpty()) {
            return true
        }
        val it = priorityMap.iterator()
        while (it.hasNext()) {
            val entry = it.next()
            if (entry.key.isAlwaysActive) {
                return true
            }
        }
        return false
    }

    override fun hasObservers(): Boolean {
        return priorityMap.isNotEmpty()
    }

    protected class PriorityObserverWrapper(
        val priority: Int,
        val isAlwaysActive: Boolean = false
    ) : Comparable<PriorityObserverWrapper> {


        override fun equals(other: Any?): Boolean {
            return if (other is PriorityObserverWrapper) {
                other.priority == this.priority
                        && other.isAlwaysActive == this.isAlwaysActive
            } else {
                false
            }
        }


        override fun compareTo(other: PriorityObserverWrapper): Int {
            return this.priority - other.priority
        }

        override fun hashCode(): Int {
            var result = priority
            result = 31 * result + isAlwaysActive.hashCode()
            return result
        }
    }

}
