package com.biblublab.album.common.mvi

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

abstract class BaseFragment<STATE, EFFECT, ACTION, ViewModel : BaseViewModel<STATE, EFFECT, ACTION>>(private val layoutId: Int) : Fragment() {

    abstract val viewModel: ViewModel

    private val viewStateObserver = Observer<STATE> {
        Log.d("TAG", "observed viewState : $it")
        renderViewState(it)
    }

    private val viewEffectObserver = Observer<EFFECT> {
        Log.d("TAG", "observed viewEffect : $it")
        renderViewEffect(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.viewStates().observe(this, viewStateObserver)
        viewModel.viewEffects().observe(this, viewEffectObserver)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initUI()
    }

    abstract fun initUI()

    abstract fun renderViewState(viewState: STATE)

    abstract fun renderViewEffect(viewEffect: EFFECT)
}