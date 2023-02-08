package com.trr1ckster.feednews.ui.details

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.webkit.WebViewClient
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.trr1ckster.feednews.R
import com.trr1ckster.feednews.databinding.FragmentDetailsBinding
import com.trr1ckster.feednews.ui.MainViewModel
import com.trr1ckster.feednews.ui.factory


class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels { factory() }
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val article = args.article
        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.action_button, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

                when (menuItem.itemId) {
                    R.id.action_favorite -> {
                        viewModel.saveArticle(article)
                        view?.let {
                            Snackbar.make(
                                it,
                                "Article saved successfully",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }

                    R.id.action_share -> {
                        val shareIntent = Intent(Intent.ACTION_SEND).setType("text/plain")
                            .putExtra(Intent.EXTRA_TEXT, article.url)
                        startActivity(shareIntent)
                    }
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}