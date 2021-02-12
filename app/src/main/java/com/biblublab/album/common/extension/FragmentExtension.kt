package com.biblublab.album.common.extension

import android.app.Activity
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.biblublab.album.R
import com.biblublab.album.common.viewBinding.FragmentViewBindingDelegate
import com.google.android.material.snackbar.Snackbar

fun <T : ViewBinding> Fragment.viewBinding(viewBindingFactory: (View) -> T) =
    FragmentViewBindingDelegate(this, viewBindingFactory)

fun Activity.showSnackbar(msgRes: String) {
    Snackbar.make(findViewById<CoordinatorLayout>(R.id.main_coordinator), msgRes, Snackbar.LENGTH_LONG).show()
}

fun Fragment.waitForTransition(targetView: View) {
    postponeEnterTransition()
    targetView.doOnPreDraw { startPostponedEnterTransition() }
}