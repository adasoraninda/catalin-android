package com.codetron.dogs.ui.list

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.codetron.dogs.R
import com.codetron.dogs.databinding.FragmentListBinding
import com.codetron.dogs.ui.ViewState
import com.codetron.dogs.utils.NotificationHelper
import com.codetron.dogs.utils.ViewModelFactory
import com.codetron.dogs.utils.visibleIf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding

    private val viewModelFactory: ViewModelFactory by lazy {
        ViewModelFactory.getInstance(requireContext())
    }

    private val viewModel: ListViewModel by viewModels {
        viewModelFactory
    }

    private val dogsAdapter: ListDogAdapter by lazy {
        ListDogAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> showDialog()
            R.id.action_settings -> {
                findNavController().navigate(R.id.nav_list_to_settings)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListData()
        subscribeToGetAllDataViewModel()
        subscribeToDeleteAllDataViewModel()

        binding?.swpListDogs?.setOnRefreshListener {
            viewModel.fetchData()
        }

    }

    private fun initListData() {
        binding?.lstDogs?.apply {
            adapter = dogsAdapter.apply {
                onItemClickListener = { id, name ->
                    val directions = ListFragmentDirections.navListToDetail(id, name)
                    findNavController().navigate(directions)
                }
            }
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
            setHasFixedSize(true)
        }
    }

    private fun subscribeToGetAllDataViewModel() {
        val swipeRefresh = binding?.swpListDogs

        viewModel.result.observe(viewLifecycleOwner, { state ->
            binding?.lstDogs?.visibleIf(state is ViewState.Success)
            binding?.txtError?.visibleIf(state is ViewState.Error)
            binding?.psbList?.visibleIf(
                state is ViewState.Loading && swipeRefresh?.isRefreshing == false
            )

            if (state is ViewState.Error) {
                swipeRefresh?.isRefreshing = false
                binding?.txtError?.text = state.error?.message
            }

            if (state is ViewState.Success) {
                swipeRefresh?.isRefreshing = false

                if (state.data.isNullOrEmpty()) {
                    binding?.txtError?.visibility = View.VISIBLE
                } else {
                    NotificationHelper(requireContext()).sendNotification()
                    dogsAdapter.updateData(state.data)
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun subscribeToDeleteAllDataViewModel() {
        viewModel.eventFlow.onEach { event ->
            if (event is ListViewModel.Event.EventDelete) {
                val message = event.message

                if (message == null) {
                    dogsAdapter.updateData(emptyList())
                    binding?.txtError?.text = "No data"
                    Toast.makeText(
                        requireContext(),
                        "Successfully delete all data",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    binding?.txtError?.text = message
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun showDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete All")
            .setMessage("Are you sure you want to delete all data?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, _ ->
                viewModel.deleteAllData()
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}