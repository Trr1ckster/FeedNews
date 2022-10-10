package com.example.feednews.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.example.feednews.databinding.FragmentDetailsBinding
import com.example.feednews.ui.home.MainViewModel
import com.google.android.material.snackbar.Snackbar


class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    lateinit var viewModel: MainViewModel
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        viewModel = (activity as MainActivity).viewModel
        val article = args.article
        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }

        return binding.root
    }
}