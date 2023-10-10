package org.rasulov.todoapp.data.repository

import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.rasulov.todoapp.base.BaseTest
import org.rasulov.todoapp.data.repository.entity.FindBy
import org.rasulov.todoapp.data.sources.database.DataBaseToDoSource
import org.rasulov.todoapp.data.sources.preference.PreferenceToDoSource
import org.rasulov.todoapp.domain.EmptyFieldException
import org.rasulov.todoapp.domain.entities.AppSettings
import org.rasulov.todoapp.domain.entities.Priority
import org.rasulov.todoapp.domain.entities.ToDoSearchBy
import org.rasulov.todoapp.testutils.generateToDo
import org.rasulov.todoapp.testutils.generateToDoList

@OptIn(ExperimentalCoroutinesApi::class)
class ToDoRepositoryImplTest : BaseTest() {

    @RelaxedMockK
    private lateinit var dataBaseSource: DataBaseToDoSource

    @RelaxedMockK
    private lateinit var preferenceSource: PreferenceToDoSource

    private lateinit var repository: ToDoRepositoryImpl

    @Before
    fun setUp() {
        setUpDataBaseSource()
        setUpPreferenceSource()
        repository = createRepository()
    }

    @Test
    fun getAllToDos_shouldCallGetAllToDosFromSource() = runTest {
        repository.getAllToDos().first()
        verify(exactly = 1) {
            dataBaseSource.getAllToDos(any())
        }
    }

    @Test(expected = EmptyFieldException::class)
    fun addToDo_withEmptyTitle_throwsEmptyFieldException() = runTest {
        val toDo = generateToDo(title = " ")
        repository.addToDo(toDo)
    }

    @Test
    fun addToDo_callInsertToDoFromSource() = runTest {
        val toDo = generateToDo()
        repository.addToDo(toDo)
        coVerify(exactly = 1) {
            dataBaseSource.insertToDo(toDo)
        }
        confirmVerified(dataBaseSource)
    }

    @Test
    fun setSearchBy_updatedSearchByLiveData() = runTest {
        val searchBy = ToDoSearchBy("Hard Work")
        val appSettings = AppSettings(Priority.HIGH)
        repository.setSearchBy(searchBy)
        repository.getAllToDos().first()
        coVerify {
            dataBaseSource.getAllToDos(FindBy(searchBy, appSettings))
        }
    }

    private fun createRepository() = ToDoRepositoryImpl(
        database = dataBaseSource,
        preference = preferenceSource
    )

    private fun setUpDataBaseSource() {
        every { dataBaseSource.getAllToDos(any()) } returns flowOf(generateToDoList())
    }

    private fun setUpPreferenceSource() {
        every {
            preferenceSource.getAppSettings()
        } returns flowOf(AppSettings(Priority.HIGH))
    }

}