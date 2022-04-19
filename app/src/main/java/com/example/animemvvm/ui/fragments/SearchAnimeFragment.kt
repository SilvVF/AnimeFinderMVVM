package com.example.animemvvm.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animemvvm.R
import com.example.animemvvm.adapters.AnimeSearchAdapter
import com.example.animemvvm.databinding.FragmentSearchAnimeBinding
import com.example.animemvvm.ui.AnimeActivity
import com.example.animemvvm.ui.AnimeViewModel
import com.example.animemvvm.util.Constants
import com.example.animemvvm.util.Resource
import com.example.animemvvm.util.toTop
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchAnimeFragment: Fragment(R.layout.fragment_search_anime) {

    private lateinit var viewModel: AnimeViewModel
    private lateinit var binding: FragmentSearchAnimeBinding
    private lateinit var animeSearchAdapter: AnimeSearchAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as AnimeActivity).viewModel
        setupRecyclerView()

        animeSearchAdapter.setOnItemClickListener { animeResult ->
            val bundle = Bundle().apply {
                putSerializable("animeResult", animeResult.toTop())
            }
            findNavController().navigate(R.id.action_searchAnimeFragment_to_viewAnimeFragment,
                bundle
            )
        }

        var job: Job? = null
        binding.etSearch.addTextChangedListener{ searchText ->
            job?.cancel()
            job = MainScope().launch {
                delay(Constants.WAIT_TIME_FOR_SEARCH)
                searchText?.let {
                    if(searchText.isNotEmpty() && searchText.length >= 3){
                            viewModel.searchJikanResponce(searchText.toString())
                        }
                    }
                }
            }

            viewModel.searchAnimeList.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let { jikanResponse ->
                            animeSearchAdapter.differ.submitList(jikanResponse.results)
                        }
                    }
                    is Resource.Error -> {
                        hideProgressBar()
                        Log.e("FragSearchError", response.message.toString())
                    }
                    is Resource.Loading -> {
                        showProgressBar()
                    }
                }
            }
        }

        private fun hideProgressBar() {
            binding.paginationProgressBar.visibility = View.INVISIBLE
        }

        private fun showProgressBar() {
            binding.paginationProgressBar.visibility = View.VISIBLE
        }

        private fun setupRecyclerView(){
            animeSearchAdapter = AnimeSearchAdapter()
            binding.rvSearchAnime.apply {
                adapter = animeSearchAdapter
                layoutManager = LinearLayoutManager(activity)
                // addOnScrollListener(this@TopAnimeFragment.scrollListener)
            }
        }


        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            binding = FragmentSearchAnimeBinding.inflate(layoutInflater)
            return binding.root
        }

}
