package com.example.feednews.ui.save

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.feednews.R
import com.example.feednews.adapters.NewsAdapter
import com.example.feednews.databinding.FragmentDetailsBinding
import com.example.feednews.databinding.FragmentSaveBinding
import com.example.feednews.databinding.FragmentSearchBinding
import com.example.feednews.ui.MainActivity
import com.example.feednews.ui.home.MainViewModel
import com.google.android.material.snackbar.Snackbar

class SaveFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentSaveBinding
    private lateinit var recyclerAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSaveBinding.inflate(inflater, container, false)

        viewModel = (activity as MainActivity).viewModel
        binding.recyclerViewSaved.layoutManager = LinearLayoutManager(requireContext())
        recyclerAdapter = NewsAdapter()
        binding.recyclerViewSaved.adapter = recyclerAdapter

        viewModel.getSavedArticles().observe(viewLifecycleOwner) {
            recyclerAdapter.differ.submitList(it)
        }

        recyclerAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_saveFragment_to_detailsFragment,
                bundle
            )
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = recyclerAdapter.differ.currentList[position]
                viewModel.deleteArticle(article)
                view?.let {
                    Snackbar.make(it, "Successfully deleted article", Snackbar.LENGTH_SHORT).apply {
                        setAction("Undo") {
                            viewModel.saveArticle(article)
                        }.show()
                    }
                }
            }

        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.recyclerViewSaved)
        }


        return binding.root
    }


}