package com.trr1ckster.feednews.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.trr1ckster.feednews.R
import com.trr1ckster.feednews.adapters.NewsAdapter
import com.trr1ckster.feednews.databinding.FragmentHomeBinding
import com.trr1ckster.feednews.ui.factory
import com.trr1ckster.feednews.ui.MainViewModel
import com.trr1ckster.feednews.utils.Resource

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerAdapter: NewsAdapter
    private val viewModel: MainViewModel by viewModels { factory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerAdapter = NewsAdapter()
        binding.recyclerView.adapter = recyclerAdapter


        recyclerAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_mainFragment_to_detailsFragment,
                bundle
            )
        }

        viewModel.newsLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    binding.homeProgressBar.visibility = View.INVISIBLE
                    response.data?.let {
                        recyclerAdapter.differ.submitList(it.articles)
                    }
                }
                is Resource.Error -> {
                    binding.homeProgressBar.visibility = View.INVISIBLE
                    response.message?.let { message ->
                        Log.e("TAG", "An error occurred: $message")
                    }
                }
                is Resource.Loading -> {
                    binding.homeProgressBar.visibility = View.VISIBLE
                }
            }

        }
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}