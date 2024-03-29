package org.rasulov.todoapp.utilities.primitives

import android.content.Context
import android.util.TypedValue


fun Int.dp(context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        context.resources.displayMetrics
    ).toInt()
}