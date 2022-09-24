package org.rasulov.todoapp.app.presentation.fragments.list

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.transition.Fade
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.rasulov.androidx.fragment.*
import org.rasulov.todoapp.R
import org.rasulov.todoapp.app.domain.entities.Priority
import org.rasulov.todoapp.app.domain.entities.ToDo
import org.rasulov.todoapp.app.domain.entities.ToDoSearchBy
import org.rasulov.todoapp.app.presentation.fragments.list.adapter.ToDoAdapter
import org.rasulov.todoapp.app.presentation.fragments.list.adapter.ToDoDiffUtil
import org.rasulov.todoapp.app.presentation.fragments.list.adapter.ToDoHolder
import org.rasulov.todoapp.app.presentation.fragments.update.entities.ToDoParcel
import org.rasulov.todoapp.app.presentation.utils.getColorsFromRes
import org.rasulov.todoapp.app.presentation.utils.setOnQueryListener
import org.rasulov.todoapp.app.presentation.utils.setPriority
import org.rasulov.todoapp.databinding.FragmentListBinding

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

        val adapter = ToDoAdapter(this, requireContext().getColorsFromRes(R.array.colors))

        setUpRecyclerView(adapter)

        setUpFloatingAction()

        addMenuProvider()

        observeData(adapter)

    }

    private fun setUpRecyclerView(adapter: ToDoAdapter) {

        binding.list.itemAnimator = SlideInUpAnimator().apply { addDuration = 300 }
        binding.list.layoutManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)

        adapter.setSwipeConfig { toDo, _ ->
            viewModel.deleteToDo(toDo.id)
            canBeRestored(toDo)
        }
        adapter.setDiffUtilItemCallBack(ToDoDiffUtil())
        binding.list.adapter = adapter
    }

    private fun canBeRestored(item: ToDo) {
        Snackbar
            .make(binding.parent, "Deleted ${item.title}", Snackbar.LENGTH_LONG)
            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
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


    private fun observeData(adapter: ToDoAdapter) = viewLifeCycleScope.launch {
        repeatOnViewLifeCycle(Lifecycle.State.STARTED) {
            viewModel.allToDos.collectLatest {
                renderResult(adapter, it)
            }
        }
    }

    private fun renderResult(adapter: ToDoAdapter, list: List<ToDo>) {
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