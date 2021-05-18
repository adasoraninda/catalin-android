package com.codetron.countries.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.codetron.countries.CountriesApp
import com.codetron.countries.databinding.ActivityMainBinding
import com.codetron.countries.util.visibleIf
import com.codetron.countries.viewmodel.MainViewModel
import com.codetron.countries.viewmodel.MainViewState
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by viewModels<MainViewModel> {
        viewModelFactory
    }

    private val listCountryAdapter by lazy {
        CountryListAdapter()
    }

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding

    private val viewContent by lazy { binding?.lytContent }
    private val viewError by lazy { binding?.lytError }
    private val viewLoading by lazy { binding?.lytLoading }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as CountriesApp).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initListData()
        observeViewModel()

        binding?.swpContentMain?.setOnRefreshListener {
            viewModel.fetchCountries()
        }

        viewError?.btnRetry?.setOnClickListener {
            viewModel.fetchCountries()
        }
    }

    private fun initListData() {
        viewContent?.lstCountry?.adapter = listCountryAdapter
        viewContent?.lstCountry?.setHasFixedSize(true)
    }

    private fun observeViewModel() {
        val swipeRefresh = binding?.swpContentMain

        viewModel.viewStateCountry.observe(this, { state ->
            viewLoading?.root?.visibleIf(
                state is MainViewState.Loading && swipeRefresh?.isRefreshing == false
            )
            viewError?.root?.visibleIf(state is MainViewState.Error)
            viewContent?.root?.visibleIf(state is MainViewState.Success)

            if (state is MainViewState.Success) {
                swipeRefresh?.isRefreshing = false
                listCountryAdapter.updateCountries(state.data)
            }

            if (state is MainViewState.Error) {
                swipeRefresh?.isRefreshing = false
                viewError?.txtMessage?.text = state.message
            }

        })
    }

}