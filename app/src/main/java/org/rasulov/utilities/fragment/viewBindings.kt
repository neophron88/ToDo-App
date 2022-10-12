@file:Suppress("UNCHECKED_CAST")

package org.rasulov.utilities.fragment

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KProperty


/**
 * A delegate that provide your viewbinding class within a Fragment by lazy.
 *
 * The Delegate considers the fragment's view lifecycle.
 *
 * When using this delegate make sure that the Fragment was inherited from
 * Fragment(@LayoutRes int contentLayoutId) with providing your layout in constructor or
 * create as usual in onCreateView method in order to viewBinding's delegate will be able
 * to bind to the view on its own.
 *
 *
 * Note that accessing viewBinding while fragment's view is
 * destroyed or not created will throw IllegalStateException.
 **/

inline fun <reified VB : ViewBinding> Fragment.viewBindings(): LazyFragmentsViewBinding<VB> {
    return LazyFragmentsViewBinding(this, VB::class.java)
}

class LazyFragmentsViewBinding<VB>(
    private val fragment: Fragment,
    private val VBClazz: Class<VB>,
) {

    private var binding: VB? = null
    private var currentView: View? = null

    operator fun getValue(thisRef: Any?, property: KProperty<*>): VB {
        checkLifeCycle()
        return binding!!
    }

    private fun checkLifeCycle() {
        val view = fragment.view ?: throw IllegalStateException(
            "Don't access viewBinding when the Fragment's View is null i.e., " +
                    "before onViewCreated() or after onDestroyView()"
        )

        if (this.currentView !== view) {
            this.currentView = view
            val bind = VBClazz.getMethod("bind", View::class.java)
            val bindingObj = bind.invoke(null, view)
            binding = bindingObj as VB
        }
    }

}