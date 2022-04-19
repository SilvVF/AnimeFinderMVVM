package com.example.animemvvm.ui.fragments

import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.animemvvm.R
import com.example.animemvvm.adapters.AnimeAdapter
import com.example.animemvvm.databinding.FragmentTopAnimeBinding
import com.example.animemvvm.ui.AnimeActivity
import com.example.animemvvm.ui.AnimeViewModel
import com.example.animemvvm.util.Constants.Companion.QUERY_PAGE_SIZE

import com.example.animemvvm.util.Resource



class TopAnimeFragment : Fragment(R.layout.fragment_top_anime) {

    private lateinit var TAFbinding: FragmentTopAnimeBinding
    lateinit var animeAdapter: AnimeAdapter
    private lateinit var viewModel: AnimeViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =  (activity as AnimeActivity).viewModel
        setupRecyclerView()

        animeAdapter.setOnItemClickListener { animeResult ->
            val bundle = Bundle().apply { // create a bundle to pass articles between fragments
                putSerializable("animeResult", animeResult)
            }
            findNavController().navigate( //res id of action to be performed slide in etc
                R.id.action_topAnimeFragment_to_viewAnimeFragment,
                bundle
            )
        }
        viewModel.animeList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { jikanResponse ->
                        animeAdapter.differ.submitList(jikanResponse.top.toList())
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    Log.e("FragTopError", response.message.toString())
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }

    }

    private fun hideProgressBar() {
        TAFbinding.paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        TAFbinding.paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        TAFbinding = FragmentTopAnimeBinding.inflate(layoutInflater)
        return TAFbinding.root
    }

    private var isLoading = false
    private var isLastPage = false
    private var isScrolling = false

    val scrollListener = object: RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem
                    && isNotAtBeginning && isTotalMoreThanVisible && isScrolling
            if (shouldPaginate){
                viewModel.getJikanResponse()
            }
        }
    }

    private fun setupRecyclerView(){
        animeAdapter = AnimeAdapter()
        TAFbinding.rvTopAnimeItems.apply {
            adapter = animeAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@TopAnimeFragment.scrollListener)
        }
    }


}