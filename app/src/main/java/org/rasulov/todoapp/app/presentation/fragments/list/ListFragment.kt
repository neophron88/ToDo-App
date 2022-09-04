package org.rasulov.todoapp.app.presentation.fragments.list

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.rasulov.androidx.fragment.addMenuProvider
import org.rasulov.androidx.fragment.viewBindings
import org.rasulov.todoapp.R
import org.rasulov.todoapp.app.Singletons
import org.rasulov.todoapp.app.domain.entities.Priority
import org.rasulov.todoapp.app.domain.entities.ToDo
import org.rasulov.todoapp.app.domain.entities.ToDoSearchBy
import org.rasulov.todoapp.app.presentation.fragments.list.adapter.ToDoAdapter
import org.rasulov.todoapp.app.presentation.fragments.list.adapter.ToDoDiffUtil
import org.rasulov.todoapp.app.presentation.fragments.update.entities.ToDoParcel
import org.rasulov.todoapp.app.presentation.utils.setOnQueryListener
import org.rasulov.todoapp.app.presentation.utils.setPriority
import org.rasulov.todoapp.app.presentation.utils.viewModel
import org.rasulov.todoapp.databinding.FragmentListBinding


class ListFragment : Fragment(R.layout.fragment_list), ToDoAdapter.OnClickListener {

    private val viewModel by viewModel {
        ListViewModel(Singletons.toDoRepository)
    }

    private val binding: FragmentListBinding by viewBindings()

    private val controller by lazy { findNavController() }

    private val adapter by lazy { ToDoAdapter(this) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()

        setUpFloatingAction()

        addMenuProvider()

        observeData()

    }

    private fun setUpRecyclerView() {
        binding.list.itemAnimator = SlideInUpAnimator().apply { addDuration = 300 }
        binding.list.adapter = adapter
        binding.list.layoutManager = StaggeredGridLayoutManager(
            2,
            StaggeredGridLayoutManager.VERTICAL
        )

        adapter.setSwipeConfig(toRight = false) { item, _ ->
            viewModel.deleteToDo(item.id)
            canBeRestored(item)
        }

        adapter.setDiffUtilItemCallBack(ToDoDiffUtil())
    }

    private fun canBeRestored(item: ToDo) {
        Snackbar
            .make(binding.root, "Deleted ${item.title}", Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.undo)) { viewModel.addToDO(item) }
            .show()
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


    private fun observeData() {
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.getAllToDos().collectLatest { renderResult(it) }
            }
        }
    }

    private fun renderResult(list: List<ToDo>) {
        disableViews()
        adapter.submitData(list)
        if (list.isNotEmpty()) {
            binding.list.isVisible = true
        } else {
            binding.noDataContainer.isVisible = true
        }

    }

    private fun disableViews() {
        binding.list.isVisible = false
        binding.noDataContainer.isVisible = false
        binding.loadProgress.isVisible = false
    }

    override fun onItemClick(toDo: ToDo) {
        val action =
            ListFragmentDirections.actionListFragmentToUpdateFragment(ToDoParcel.fromToDo(toDo))
        controller.navigate(action)
    }
}