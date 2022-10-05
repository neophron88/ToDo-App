package org.rasulov.utilities.primitives

import android.content.Context
import android.util.TypedValue


fun Int.dpToPixel(context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        context.resources.displayMetrics
    ).toInt()
}