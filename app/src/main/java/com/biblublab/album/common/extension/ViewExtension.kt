package com.biblublab.album.common.extension

import android.view.View
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.load
import coil.transform.CircleCropTransformation
import com.biblublab.album.R
import com.facebook.shimmer.ShimmerFrameLayout

fun ShimmerFrameLayout.beginShimmerAnim() {
    if (!isShimmerStarted) startShimmer()
    this.show()
}

fun ShimmerFrameLayout.endShimmerAnim() {
    this.hide()
    if (isShimmerStarted) stopShimmer()
}

fun View.show() {
    animate().alpha(1.0f)
}

fun View.hide() {
    animate().alpha(0.0f)
}

fun View.visible(isVisible : Boolean){
    if(isVisible) this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}
fun ImageView.loadImg(url: String) {
        this.load(url){
            placeholder( CircularProgressDrawable(context).apply {
            strokeWidth = 10f
            centerRadius = 30f
            start()
        })
        }

}
