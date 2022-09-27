package org.rasulov.utilities.fragment

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KProperty


/**
 * A delegate that provide your viewbinding class within the Fragment by lazy.
 *
 * The Delegate considers the fragment's view lifecycle.
 *
 * When using this delegate make sure that your Fragment was inherited from
 * Fragment(@LayoutRes int contentLayoutId) with providing your layout in constructor.
 *
 *
 * Note that accessing viewBinding while fragment's view is
 * destroyed or not created will throw IllegalStateException.
 **/

inline fun <reified VB : ViewBinding> Fragment.viewBindings(): LazyFragmentsViewBinding<VB> {
    return LazyFragmentsViewBinding(this, VB::class.java)
}

@Suppress("UNCHECKED_CAST")
class LazyFragmentsViewBinding<VB>(
    private val fragment: Fragment,
    private val VBClazz: Class<VB>,
) {

    private var binding: VB? = null
    private var viewLifecycleOwner: LifecycleOwner? = null

    operator fun getValue(thisRef: Any?, property: KProperty<*>): VB {
        checkViewLifeCycle()
        return binding!!
    }

    private fun checkViewLifeCycle() {
        if (viewLifecycleOwner !== fragment.viewLifecycleOwner) {

            fragment.view
                ?: throw IllegalStateException(
                    "Don't access the ViewBinding when the Fragment's View is null i.e., " +
                            "before onViewCreated() or after onDestroyView()"
                )

            viewLifecycleOwner = fragment.viewLifecycleOwner
            val bind = VBClazz.getMethod("bind", View::class.java)
            val bindingObj = bind.invoke(null, fragment.view)
            binding = bindingObj as VB
        }
    }
}