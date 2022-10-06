package com.example.countryinfo.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countryinfo.R
import com.example.countryinfo.UniversalListAdapter
import com.example.countryinfo.databinding.FragmentCountriesBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Created by Nitin Dasari on 10/5/22.
 */
class CountriesFragment : Fragment() {
    private var _binding: FragmentCountriesBinding? = null
    private val binding get() = _binding!!
    private val countryViewModel: CountryViewModel by sharedViewModel()
    private val countryAdapter = UniversalListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
            _binding =
                FragmentCountriesBinding.inflate(
                    LayoutInflater.from(requireContext()),
                    container,
                    false
                )
            return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvCountries.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = countryAdapter
        }

        safeCollectFlow(countryViewModel.uiStateFlow) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progress.isVisible = true
                }
                is UiState.Success -> {
                    binding.progress.isVisible = false
                    val items = state.countries
                    countryAdapter.submitList(items)
                }
                is UiState.Error -> {
                    binding.progress.isVisible = false
                    val errorMessage = state.errorMessage
                    showAlert(
                        requireContext(),
                        getString(R.string.error_title),
                        errorMessage
                    ).show()
                }
            }
        }

        safeCollectFlow(countryViewModel.countrySelectionFlow) {
            val bundle = bundleOf("countryCode" to it.code)
            findNavController().navigate(R.id.countryDetailFragment, bundle)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}