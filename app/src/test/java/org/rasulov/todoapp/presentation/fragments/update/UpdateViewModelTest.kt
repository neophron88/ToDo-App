package org.rasulov.todoapp.presentation.fragments.update

import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.just
import io.mockk.runs
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.rasulov.todoapp.base.BaseTest
import org.rasulov.todoapp.domain.ToDoRepository
import org.rasulov.todoapp.presentation.utils.OperationSuccess
import org.rasulov.todoapp.testutils.generateToDo

class UpdateViewModelTest : BaseTest() {

    @RelaxedMockK
    private lateinit var repository: ToDoRepository

    private lateinit var viewModel: UpdateViewModel

    @Before
    fun setUp() {
        viewModel = createUpdateViewModel()
    }

    @Test
    fun updateToDo_shouldBeSuccess() {
        val todo = generateToDo()
        coEvery { repository.updateToDo(todo) } just runs

        viewModel.updateToDO(todo)

        val event = viewModel.uiEvent
        Assert.assertEquals(OperationSuccess, event.value)
    }

    private fun createUpdateViewModel() = UpdateViewModel(repository)
}