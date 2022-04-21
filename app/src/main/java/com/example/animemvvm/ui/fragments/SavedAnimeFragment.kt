package com.example.animemvvm.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.accessibility.AccessibilityEventCompat.setAction
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.animemvvm.R
import com.example.animemvvm.adapters.AnimeAdapter
import com.example.animemvvm.adapters.AnimeAdapterSaved
import com.example.animemvvm.databinding.FragmentSavedAnimeBinding
import com.example.animemvvm.databinding.FragmentTopAnimeBinding
import com.example.animemvvm.ui.AnimeActivity
import com.example.animemvvm.ui.AnimeViewModel
import com.google.android.material.snackbar.Snackbar


class SavedAnimeFragment: Fragment(R.layout.fragment_saved_anime) {

    private lateinit var viewModel: AnimeViewModel
    private lateinit var animeAdapter : AnimeAdapterSaved
    private lateinit var binding: FragmentSavedAnimeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as AnimeActivity).viewModel
        setupRecyclerView()
        animeAdapter.setOnItemClickListener { animeItem ->
            val bundle = Bundle().apply { // create a bundle to pass articles between fragments
                putSerializable("animeResult", animeItem)
            }
            findNavController().navigate( //res id of action to be performed slide in etc
                R.id.action_savedAnimeFragment_to_viewAnimeFragment,
                bundle
            )
        }

        viewModel.getSavedAnime().observe(viewLifecycleOwner, Observer { animeList ->
            animeAdapter.differ.submitList(animeList)
        })

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN ,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val anime = animeAdapter.differ.currentList[position]
                viewModel.deleteAnime(anime)
                Snackbar.make(view!!, "Succefully deleted", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        viewModel.saveAnime(anime)
                    }
                    show()
                }
            }

        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvSavedAnime)
        }
    }




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedAnimeBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun setupRecyclerView(){
        animeAdapter = AnimeAdapterSaved()
        binding.rvSavedAnime.apply {
            adapter = animeAdapter
            layoutManager = LinearLayoutManager(activity)
            // addOnScrollListener(this@TopAnimeFragment.scrollListener)
        }
    }
}