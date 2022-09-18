package org.rasulov.androidx.transition

import androidx.transition.Transition

typealias OnEnd = (transition: Transition) -> Unit

fun Transition.addListener(onEnd: OnEnd) {
    addListener(object : Transition.TransitionListener {
        override fun onTransitionStart(transition: Transition) = Unit

        override fun onTransitionEnd(transition: Transition) = onEnd(transition)

        override fun onTransitionCancel(transition: Transition) = Unit

        override fun onTransitionPause(transition: Transition) = Unit

        override fun onTransitionResume(transition: Transition) = Unit
    }
    )
}