package com.example.feednews.ui.search

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.feednews.R
import com.example.feednews.adapters.NewsAdapter
import com.example.feednews.databinding.FragmentSearchBinding
import com.example.feednews.ui.MainActivity
import com.example.feednews.ui.home.MainViewModel

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var recyclerAdapter: NewsAdapter
    private  lateinit var viewModel: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        viewModel = (activity as MainActivity).viewModel

        binding.recyclerViewSearch.layoutManager = LinearLayoutManager(requireContext())
        recyclerAdapter = NewsAdapter()
        binding.recyclerViewSearch.adapter = recyclerAdapter

        viewModel.everythingNewsLiveData.observe(viewLifecycleOwner) {
            recyclerAdapter.differ.submitList(it.articles)
        }

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