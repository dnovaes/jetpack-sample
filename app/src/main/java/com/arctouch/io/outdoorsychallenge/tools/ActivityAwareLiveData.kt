package com.arctouch.io.outdoorsychallenge.tools

import androidx.lifecycle.MutableLiveData

class ActivityAwareLiveData<T> : MutableLiveData<T> {

    private val onActiveListener: (() -> Unit)?
    private val onInactiveListener: (() -> Unit)?

    constructor(
        initialValue: T?,
        onActiveListener: (() -> Unit)? = null,
        onInactiveListener: (() -> Unit)? = null
    ) : super(initialValue) {
        this.onActiveListener = onActiveListener
        this.onInactiveListener = onInactiveListener
    }

    constructor(
        onActiveListener: (() -> Unit)? = null,
        onInactiveListener: (() -> Unit)? = null
    ) : super() {
        this.onActiveListener = onActiveListener
        this.onInactiveListener = onInactiveListener
    }

    override fun onActive() {
        onActiveListener?.invoke()
    }

    override fun onInactive() {
        onInactiveListener?.invoke()
    }
}
