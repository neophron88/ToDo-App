package org.rasulov.androidx.activity

import android.view.LayoutInflater
import androidx.activity.ComponentActivity
import androidx.viewbinding.ViewBinding


/**
 * A delegate that provide your viewbinding class within the Activity by lazy.
 **/
inline fun <reified VB : ViewBinding> ComponentActivity.viewBindings() = lazy {
    val clazz: Class<VB> = VB::class.java
    val inflate = clazz.getMethod("inflate", LayoutInflater::class.java)
    val bindingObj = inflate.invoke(null, layoutInflater)
    bindingObj as VB
}
