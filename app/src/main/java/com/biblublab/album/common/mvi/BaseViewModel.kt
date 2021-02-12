package com.biblublab.album.common.mvi

import android.content.ContentValues
import android.util.Log
import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.biblublab.album.common.SingleLiveEvent

open class BaseViewModel<STATE, EFFECT, ACTION> : ViewModel(), ViewModelContract<ACTION> {

    private val _viewStates: MutableLiveData<STATE> = MutableLiveData()
    fun viewStates(): LiveData<STATE> = _viewStates

    private var _viewState: STATE? = null
    protected var viewState: STATE
        get() = _viewState
            ?: throw UninitializedPropertyAccessException("\"viewState\" was queried before being initialized")
        set(value) {
            Log.d(ContentValues.TAG, "setting viewState : $value")
            _viewState = value
            _viewStates.value = value
        }


    private val _viewEffects: SingleLiveEvent<EFFECT> = SingleLiveEvent()
    fun viewEffects(): SingleLiveEvent<EFFECT> = _viewEffects

    private var _viewEffect: EFFECT? = null
    protected var viewEffect: EFFECT
        get() = _viewEffect
            ?: throw UninitializedPropertyAccessException("\"viewEffect\" was queried before being initialized")
        set(value) {
            Log.d(ContentValues.TAG, "setting viewEffect : $value")
            _viewEffect = value
            _viewEffects.value = value
        }

    @CallSuper
    override fun process(viewAction: ACTION) {
        Log.d(ContentValues.TAG, "processing viewEvent: $viewAction")
    }
}