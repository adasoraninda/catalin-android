package com.codetron.dogs.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.codetron.dogs.R
import com.codetron.dogs.databinding.FragmentDetailBinding
import com.codetron.dogs.ui.ViewState
import com.codetron.dogs.utils.ViewModelFactory
import com.codetron.dogs.utils.visibleIf
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding

    private val viewModelFactory by lazy {
        ViewModelFactory.getInstance(requireContext())
    }

    private val viewModel by viewModels<DetailViewModel> {
        viewModelFactory
    }

    private val args by navArgs<DetailFragmentArgs>()

    private val layoutContent by lazy { binding?.lytContent }
    private val layoutLoading by lazy { binding?.lytLoading }
    private val layoutError by lazy { binding?.lytError }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_send -> {
                Toast.makeText(
                    requireContext(),
                    "Coming soon",
                    Toast.LENGTH_SHORT
                ).show()
            }
            R.id.action_share -> {
                val dog = binding?.lytContent?.dog

                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_SUBJECT, "Check out this dog breed")
                intent.putExtra(
                    Intent.EXTRA_TEXT,
                    "${dog?.name} bred for ${dog?.bredFor}"
                )
                intent.putExtra(Intent.EXTRA_STREAM, "${dog?.imageUrl}")
                startActivity(Intent.createChooser(intent, "Share with"))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.getDog(args.dogId)
        }

        viewModel.dog.observe(viewLifecycleOwner) { state ->
            layoutError?.root?.visibleIf(state is ViewState.Error)
            layoutLoading?.root?.visibleIf(state is ViewState.Loading)
            layoutContent?.root?.visibleIf(state is ViewState.Success)

            if (state is ViewState.Error) {
                layoutError?.txtError?.text = state.error?.message
            }

            if (state is ViewState.Success) {
                state.data?.let { layoutContent?.dog = it }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}