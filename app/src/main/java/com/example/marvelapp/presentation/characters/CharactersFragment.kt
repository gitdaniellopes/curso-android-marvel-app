package com.example.marvelapp.presentation.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.marvelapp.databinding.FragmentCharactersBinding
import com.example.marvelapp.framework.imageloader.ImageLoader
import com.example.marvelapp.presentation.detail.DetailViewArg
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CharactersFragment : Fragment() {

    private var _binding: FragmentCharactersBinding? = null
    private val binding: FragmentCharactersBinding get() = _binding!!

    @Inject
    lateinit var imageLoader: ImageLoader

    private val viewModel: CharactersViewModel by viewModels()
    private val charactersAdapter by lazy {
        CharactersAdapter(imageLoader) { character, view ->
            val extras = FragmentNavigatorExtras(
                view to character.name
            )
            val directions = CharactersFragmentDirections
                .actionCharactersFragmentToDetailFragment(
                    character.name,
                    DetailViewArg(
                        characterId = character.id,
                        name = character.name,
                        imageUrl = character.imageUrl
                    )
                )
            findNavController().navigate(directions, extras)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentCharactersBinding.inflate(inflater, container, false).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCharactersAdapter()
        observerInitialLoadingState()

        viewModel.state.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is CharactersViewModel.UiState.SearchResult -> {
                    charactersAdapter.submitData(viewLifecycleOwner.lifecycle, uiState.data)
                }
            }
        }
        viewModel.searchCharacters()

    }

    private fun initCharactersAdapter() {
        postponeEnterTransition()
        with(binding.recycleCharacters) {
            // garanto que quando eu sair desse fragmento, ele vai voltar para o primeiro item da lista
            //scrollToPosition(0)
            //se os itens tiverem o mesmo tamanho, eu ganho permance na minha recycleview
            setHasFixedSize(true)
            adapter = charactersAdapter.withLoadStateFooter(
                footer = CharactersLoadStateAdapter { charactersAdapter.retry() }
            )
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
        }
    }

    private fun observerInitialLoadingState() {
        lifecycleScope.launch {
            charactersAdapter.loadStateFlow.collectLatest { loadState ->
                binding.flipperCharacters.displayedChild = when (loadState.refresh) {
                    is LoadState.Loading -> {
                        setShimmerVisibility(true)
                        FLIPPER_CHILD_LOADING
                    }
                    is LoadState.NotLoading -> {
                        setShimmerVisibility(false)
                        FLIPPER_CHILD_CHARACTERS
                    }
                    is LoadState.Error -> {
                        setShimmerVisibility(false)
                        binding.includeViewCharactersErrorState.buttonRetry.setOnClickListener {
                            charactersAdapter.retry()
                        }
                        FLIPPER_CHILD_ERROR
                    }
                }
            }
        }
    }

    private fun setShimmerVisibility(visibility: Boolean) {
        binding.includeViewCharactersLoadingState.shimmerCharacters.run {
            isVisible = visibility
            if (visibility) {
                startShimmer()
            } else stopShimmer()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val FLIPPER_CHILD_LOADING = 0
        private const val FLIPPER_CHILD_CHARACTERS = 1
        private const val FLIPPER_CHILD_ERROR = 2
    }
}