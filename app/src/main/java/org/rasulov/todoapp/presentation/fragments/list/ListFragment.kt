package org.rasulov.todoapp.presentation.fragments.list

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.coroutines.flow.collectLatest
import org.rasulov.todoapp.R
import org.rasulov.todoapp.databinding.FragmentListBinding
import org.rasulov.todoapp.databinding.TodoItemBinding
import org.rasulov.todoapp.domain.entities.Priority
import org.rasulov.todoapp.domain.entities.ToDo
import org.rasulov.todoapp.domain.entities.ToDoSearchBy
import org.rasulov.todoapp.presentation.fragments.update.entities.ToDoParcel
import org.rasulov.todoapp.presentation.utils.CanBeRestored
import org.rasulov.todoapp.presentation.utils.UiState
import org.rasulov.todoapp.presentation.utils.getColors
import org.rasulov.todoapp.presentation.utils.setOnQueryListener
import org.rasulov.todoapp.presentation.utils.setPriority
import org.rasulov.todoapp.utilities.fragment.addMenuProvider
import org.rasulov.todoapp.utilities.fragment.repeatWhenViewStarted
import org.rasulov.todoapp.utilities.fragment.viewBindings
import org.rasulov.todoapp.utilities.lifecycle.postDelayed
import org.rasulov.todoapp.utilities.recyclerview.adapterdelegate.ItemViewHolder
import org.rasulov.todoapp.utilities.recyclerview.adapterdelegate.ItemsAdapter
import org.rasulov.todoapp.utilities.recyclerview.setSwipeItem

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list) {

    private val viewModel: ListViewModel by viewModels()

    private val binding: FragmentListBinding by viewBindings()

    private val controller by lazy { findNavController() }

    private val colors by lazy { requireContext().getColors(R.array.colors) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = setupAdapter()

        setupRecyclerView(adapter)

        setupFloatingAction()

        addMenuProvider()

        viewLifecycleOwner.postDelayed(500) {
            observeUiState(adapter)
        }
        observeUiEvent()

    }

    private fun setupAdapter() = ItemsAdapter {

        item<ToDo, TodoItemBinding> {
            layout { R.layout.todo_item }
            diffutil {
                areItemsTheSame { oldItem, newItem -> oldItem.id == newItem.id }
                areContentsTheSame { oldItem, newItem -> oldItem == newItem }
            }
            viewholder {
                viewbinding(TodoItemBinding::bind)
                viewbindingCreated {
                    binding.root.setOnClickListener { onItemClick(item, binding) }
                }
                onbind {
                    binding.title.text = item.title
                    val color = colors[item.priority.ordinal - 1]
                    binding.priorityIndicator.backgroundTintList = ColorStateList.valueOf(color)
                    binding.description.text = item.description
                }
            }
        }

    }.also { it.stateRestorationPolicy = PREVENT_WHEN_EMPTY }

    private fun setupRecyclerView(adapter: ItemsAdapter) = with(binding) {
        list.adapter = adapter
        list.itemAnimator = SlideInUpAnimator().apply { addDuration = 300 }
        list.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        list.setSwipeItem { holder, _ ->
            val itemHolder = holder as ItemViewHolder<ToDo>
            viewModel.deleteToDo(itemHolder.item)
        }
    }

    private fun setupFloatingAction() =
        binding.floatingActionButton.setOnClickListener {
            controller.navigate(R.id.action_listFragment_to_addFragment)
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


    private fun observeUiState(adapter: ItemsAdapter) = repeatWhenViewStarted {
        viewModel.uiState.collectLatest {
            adapter.submitList(it.data)
            renderResult(it)
        }
    }

    private fun renderResult(state: UiState<ToDo>) = with(binding) {
        list.isVisible = state.isNotEmptyData
        noDataContainer.isVisible = state.isEmptyData
        loadProgress.isVisible = state.isLoading
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


    private fun onItemClick(item: ToDo, binding: TodoItemBinding) {
        val action =
            ListFragmentDirections.actionListFragmentToUpdateFragment(ToDoParcel.fromToDo(item))
        controller.navigate(action)
    }

}