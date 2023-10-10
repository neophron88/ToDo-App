package org.rasulov.todoapp.testutils

inline fun arrangeBlock(block: () -> Unit = {}) = block()
inline fun actBlock(block: () -> Unit = {}) = block()
inline fun assertBlock(block: () -> Unit = {}) = block()