package com.trr1ckster.feednews.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.trr1ckster.feednews.R
import com.trr1ckster.feednews.adapters.NewsAdapter
import com.trr1ckster.feednews.databinding.FragmentSearchBinding
import com.trr1ckster.feednews.ui.factory
import com.trr1ckster.feednews.ui.MainViewModel
import com.trr1ckster.feednews.utils.Resource

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var recyclerAdapter: NewsAdapter
    private val viewModel: MainViewModel by viewModels { factory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        binding.recyclerViewSearch.layoutManager = LinearLayoutManager(requireContext())
        recyclerAdapter = NewsAdapter()
        binding.recyclerViewSearch.adapter = recyclerAdapter

        viewModel.everythingNewsLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let {
                        recyclerAdapter.differ.submitList(it.articles)
                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        Log.e("TAG", "An error occurred: $message")
                    }
                }
                is Resource.Loading -> {

                }
            }

        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.getEverythingNews(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.getEverythingNews(newText)
                return false
            }

        })

        recyclerAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_searchFragment_to_detailsFragment,
                bundle
            )
        }
        return binding.root
    }

}