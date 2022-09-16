package com.example.feednews.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.feednews.NewsViewModelFactory
import com.example.feednews.adapters.NewsAdapter
import com.example.feednews.app.NewsApp
import com.example.feednews.databinding.FragmentMainBinding
import com.example.feednews.ui.MainActivity

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var recyclerAdapter: NewsAdapter
    lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        viewModel = (activity as MainActivity).viewModel
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerAdapter = NewsAdapter()
        binding.recyclerView.adapter = recyclerAdapter
        viewModel.newsLiveData.observe(viewLifecycleOwner) {
            recyclerAdapter.setArticleList(it.articles)
        }
        return binding.root
    }
}