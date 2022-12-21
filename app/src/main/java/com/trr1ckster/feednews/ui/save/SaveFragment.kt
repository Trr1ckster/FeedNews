package com.trr1ckster.feednews.ui.save

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.trr1ckster.feednews.R
import com.trr1ckster.feednews.adapters.NewsAdapter
import com.trr1ckster.feednews.databinding.FragmentSaveBinding
import com.trr1ckster.feednews.factory
import com.trr1ckster.feednews.ui.MainViewModel

class SaveFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels {factory()}
    private lateinit var binding: FragmentSaveBinding
    private lateinit var recyclerAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSaveBinding.inflate(inflater, container, false)

        binding.recyclerViewSave.layoutManager = LinearLayoutManager(requireContext())
        recyclerAdapter = NewsAdapter()
        binding.recyclerViewSave.adapter = recyclerAdapter

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
            attachToRecyclerView(binding.recyclerViewSave)
        }

        return binding.root
    }
}