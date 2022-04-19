package com.example.animemvvm.ui.fragments



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgument

import com.example.animemvvm.R
import com.example.animemvvm.databinding.FragmentViewAnimeBinding
import com.example.animemvvm.models.To
import com.example.animemvvm.modelsT.Top
import com.example.animemvvm.modelsT.search.Result
import com.example.animemvvm.ui.AnimeActivity
import com.example.animemvvm.ui.AnimeViewModel
import com.google.android.material.snackbar.Snackbar

class ViewAnimeFragment: Fragment(R.layout.fragment_view_anime) {

    private lateinit var binding: FragmentViewAnimeBinding
    val args : ViewAnimeFragmentArgs by navArgs()
    private lateinit var viewModel: AnimeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val anime = args.animeResult
        viewModel =  (activity as AnimeActivity).viewModel

        binding.webView.apply {
            webViewClient = WebViewClient()
            anime.url?.let { loadUrl(it) }
        }

        binding.fab.setOnClickListener {
            viewModel.saveAnime(anime)
            Snackbar.make(view, "Saved Successfully", Snackbar.LENGTH_SHORT).show()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewAnimeBinding.inflate(layoutInflater)
        return binding.root

    }

}