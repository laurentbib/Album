package com.biblublab.album.common.mvi

interface ViewModelContract<ACTION> {
    fun process(viewAction: ACTION)
}