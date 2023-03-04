package com.walmartinterviewexercise.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.walmartinterviewexercise.R
import com.walmartinterviewexercise.adapters.HomeAdapter
import com.walmartinterviewexercise.api.RetrofitBuilder
import com.walmartinterviewexercise.databinding.FragmentHomeBinding
import com.walmartinterviewexercise.extensions.isNetworkConnected
import com.walmartinterviewexercise.utils.AppLog
import com.walmartinterviewexercise.viewmodels.HomeViewModel
import com.walmartinterviewexercise.viewmodels.ViewModelFactory

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeActivity: HomeActivity
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeAdapter: HomeAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeActivity = context as HomeActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProvider(
            homeActivity,
            ViewModelFactory(RetrofitBuilder.apiService)
        )[HomeViewModel::class.java]
        homeAdapter = HomeAdapter()

        homeViewModel.getCountryListUIState().observe(homeActivity) {
            when (it) {
                is UIState.Success -> {
                    binding.progressBar.isVisible = false
                    homeAdapter.mutableList.clear()
                    binding.homeError.root.isVisible = false
                    if (it.data.isEmpty().not()) {
                        homeAdapter.mutableList.addAll(it.data)
                        homeAdapter.notifyDataSetChanged()
                    } else {
                        homeAdapter.notifyDataSetChanged()
                    }
                }
                is UIState.Error -> {
                    onError(message = it.message)
                }
                else -> {
                    // for further development
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        binding = FragmentHomeBinding.bind(root)

        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.adapter = homeAdapter

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if (homeActivity.isNetworkConnected()) {
            getCountryList()
        } else {
            binding.homeNetworkError.root.isVisible = true
            binding.homeNetworkError.buttonRetry.setOnClickListener {
                getCountryList()
            }
        }
    }

    private fun getCountryList() {
        binding.homeNetworkError.root.isVisible = false
        binding.progressBar.isVisible = true
        homeViewModel.getCountryList()
    }

    private fun onError(message: String) {
        AppLog.e(LOG_TAG, message = message, throwable = null)
        binding.homeError.root.isVisible = true
        binding.progressBar.isVisible = false
        binding.homeError.buttonRetry.setOnClickListener {
            getCountryList()
        }
    }

    companion object {

        private val LOG_TAG = HomeFragment.javaClass.simpleName.toString()

        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
