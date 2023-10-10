package org.rasulov.todoapp.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.rasulov.todoapp.testutils.TestViewModelScopeRule

@OptIn(ExperimentalCoroutinesApi::class)
open class BaseTest {

    @get:Rule
    val testViewModelScopeRule = TestViewModelScopeRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rule = MockKRule(this)


}