package com.example.countryinfo.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.countryinfo.R
import com.example.countryinfo.databinding.FragmentCountryDetailBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


/**
 * Created by Nitin Dasari on 10/5/22.
 */
class CountryDetailFragment : Fragment() {

    private var _binding: FragmentCountryDetailBinding? = null
    private val binding get() = _binding!!
    private val countryViewModel: CountryViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding =
            FragmentCountryDetailBinding.inflate(
                LayoutInflater.from(requireContext()),
                container,
                false
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val countryCode = arguments?.getString("countryCode")
        countryViewModel.checkCountryInfo(countryCode)
        safeCollectFlow(countryViewModel.countryDetailsFlow) { state ->
            when (state) {
                is CountryUiState.Success -> {
                    val country = state.country
                    with(binding) {
                        tvNameRegion.text =
                            String.format("%s, %s", country.name, country.region)
                        tvCountryCode.text = country.code
                        tvCapitalCity.text = country.capital
                        tvCurrencyValue.text = String.format(
                            "%s (%s)",
                            country.currencyCode,
                            country.currencySymbol
                        )
                        tvLanguageValue.text = String.format(
                            "%s (%s)",
                            country.languageName,
                            country.languageCode
                        )
                    }
                }
                is CountryUiState.Error -> {
                    showAlert(
                        requireContext(),
                        getString(R.string.error_title),
                        state.errorMessage,
                        positiveButtonClickListener = { dialog, _ ->
                            dialog.cancel()
                            dialog.dismiss()
                            findNavController().popBackStack()
                        })
                }
                else -> {
                    // do nothing
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance(countryCode: String): CountryDetailFragment {
            val args = Bundle().apply {
                putString("countryCode", countryCode)
            }
            val fragment = CountryDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }
}