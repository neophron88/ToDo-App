package org.rasulov.todoapp.presentation.fragments.list

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.coroutines.flow.collectLatest
import org.rasulov.todoapp.R
import org.rasulov.todoapp.databinding.FragmentListBinding
import org.rasulov.todoapp.domain.entities.Priority
import org.rasulov.todoapp.domain.entities.ToDo
import org.rasulov.todoapp.domain.entities.ToDoSearchBy
import org.rasulov.todoapp.presentation.fragments.list.adapter.ToDoAdapter
import org.rasulov.todoapp.presentation.fragments.list.adapter.ToDoHolder
import org.rasulov.todoapp.presentation.fragments.list.adapter.toToDoHolder
import org.rasulov.todoapp.presentation.fragments.update.entities.ToDoParcel
import org.rasulov.todoapp.presentation.utils.CanBeRestored
import org.rasulov.todoapp.presentation.utils.getColors
import org.rasulov.todoapp.presentation.utils.setOnQueryListener
import org.rasulov.todoapp.presentation.utils.setPriority
import org.rasulov.utilities.fragment.addMenuProvider
import org.rasulov.utilities.fragment.disableTransitionOverlap
import org.rasulov.utilities.fragment.repeatWhenViewStarted
import org.rasulov.utilities.fragment.viewBindings
import org.rasulov.utilities.lifecycle.postDelayed
import org.rasulov.utilities.recyclerview.setSwipeItem

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list), ToDoAdapter.OnClickListener {

    private val viewModel: ListViewModel by viewModels()

    private val binding: FragmentListBinding by viewBindings()

    private val controller by lazy { findNavController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        disableTransitionOverlap()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ToDoAdapter(this, requireContext().getColors(R.array.colors))

        setUpRecyclerView(adapter)

        setUpFloatingAction()

        addMenuProvider()

        viewLifecycleOwner.postDelayed(600) { observeData(adapter) }

        observeUiEvent()

    }


    private fun setUpRecyclerView(adapter: ToDoAdapter) = with(binding) {
        adapter.stateRestorationPolicy = PREVENT_WHEN_EMPTY
        list.adapter = adapter
        list.itemAnimator = SlideInUpAnimator().apply { addDuration = 300 }
        list.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        list.setSwipeItem { holder, _ -> viewModel.deleteToDo(holder.toToDoHolder().todo) }

    }

    private fun setUpFloatingAction() {
        binding.floatingActionButton.setOnClickListener {
            controller.navigate(R.id.action_listFragment_to_addFragment)
        }
    }

    private fun addMenuProvider() = addMenuProvider(
        menuRes = R.menu.list_fragment,

        onCreateMenu = { menu ->
            val searchView = menu.findItem(R.id.menu_search).actionView as SearchView
            searchView.setOnQueryListener {
                viewModel.setSearchBy(ToDoSearchBy(it))
            }
        },

        onMenuItemSelected = {
            when (it.itemId) {
                R.id.menu_priority_high -> viewModel.setPriority(Priority.HIGH)
                R.id.menu_priority_low -> viewModel.setPriority(Priority.LOW)
                R.id.menu_date_added -> viewModel.setPriority(Priority.NONE)
                R.id.menu_deleteAll -> makeSure()
            }
        }
    )

    private fun makeSure() {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Are you sure ?")
            .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
            .setPositiveButton("Yes") { _, _ -> viewModel.deleteAllToDos() }
        dialog.create().show()
    }


    private fun observeData(adapter: ToDoAdapter) = repeatWhenViewStarted {
        viewModel.allToDos.collectLatest {
            adapter.submitList(it)
            renderResult(it)
        }
    }

    private fun renderResult(data: List<ToDo>) = with(binding) {
        list.isVisible = data.isNotEmpty()
        noDataContainer.isVisible = data.isEmpty()
        loadProgress.isVisible = false
    }

    private fun observeUiEvent() = viewModel.uiEvent.observe(viewLifecycleOwner) {
        when (it) {
            is CanBeRestored -> canBeRestored(it.toDo)
            else -> throw IllegalStateException("$it event is not handled here!")
        }
    }


    private fun canBeRestored(item: ToDo) {
        Snackbar
            .make(binding.parent, "Deleted ${item.title}", Snackbar.LENGTH_LONG)
            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
            .setAction(getString(R.string.undo)) { viewModel.addToDO(item) }
            .show()
    }


    override fun onItemClick(holder: ToDoHolder) = with(holder) {
        val action =
            ListFragmentDirections.actionListFragmentToUpdateFragment(ToDoParcel.fromToDo(todo))
        controller.navigate(
            action, FragmentNavigatorExtras(
                bnd.title to "title",
                bnd.description to "description",
                bnd.priorityIndicator to "priority"
            )
        )
    }
}