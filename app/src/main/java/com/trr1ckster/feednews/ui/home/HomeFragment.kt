package com.trr1ckster.feednews.ui.home

import android.os.Bundle
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
import com.trr1ckster.feednews.factory
import com.trr1ckster.feednews.ui.MainViewModel
import com.trr1ckster.feednews.utils.Status

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerAdapter: NewsAdapter
    private val viewModel: MainViewModel by viewModels { factory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

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

        viewModel.newsLiveData.observe(viewLifecycleOwner) {
            recyclerAdapter.differ.submitList(it.articles)
        }

        viewModel.status.observe(viewLifecycleOwner) {
            when (it) {
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                }
                Status.ERROR -> {
                    binding.noConnection.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                }
            }

        }

        return binding.root
    }
}