package com.arctouch.io.outdoorsychallenge.features.searchrv

import android.animation.LayoutTransition
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent.ACTION_RECOGNIZE_SPEECH
import android.speech.RecognizerIntent.EXTRA_LANGUAGE_MODEL
import android.speech.RecognizerIntent.EXTRA_PROMPT
import android.speech.RecognizerIntent.EXTRA_RESULTS
import android.speech.RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
//import androidx.navigation.fragment.findNavController
//import com.arctouch.io.outdoorsychallenge.OutdoorsyGraphDirections
import com.arctouch.io.outdoorsychallenge.R
import com.arctouch.io.outdoorsychallenge.R.string.search_rv_voice_search_progress_text
import com.arctouch.io.outdoorsychallenge.connectivity.ErrorHandlingFragment
import com.arctouch.io.outdoorsychallenge.databinding.FragmentSearchRvBinding
import com.arctouch.io.outdoorsychallenge.extensions.hideKeyboard
import com.arctouch.io.outdoorsychallenge.features.searchrv.adapters.SearchRvVehicleAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchRvFragment : ErrorHandlingFragment() {

    override lateinit var binding: FragmentSearchRvBinding
    override val viewModel: SearchRvViewModel by viewModel()

    private lateinit var vehicleAdapter: SearchRvVehicleAdapter

//    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentSearchRvBinding.inflate(inflater).apply {
        lifecycleOwner = viewLifecycleOwner
        viewModel = this@SearchRvFragment.viewModel

        binding = this
    }.root

    override fun doOnViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews()
        observeEvents()
    }

//    private fun navigationFunctions() {
//        navController.navigate(OutdoorsyGraphDirections.actionGlobalSearchRv())
//        navController.popBackStack(R.id.search_rv, true)
//    }

    private fun setupViews() = with(binding) {
        binding.srlSearchRvResults.apply {
            setOnRefreshListener { this@SearchRvFragment.viewModel.onSwipeToRefresh() }
            setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorPrimary)
            layoutTransition?.enableTransitionType(LayoutTransition.CHANGING)
        }

        tietSearchRv.setOnEditorActionListener { _, _, _ ->
            hideKeyboard(root)
            this@SearchRvFragment.viewModel.onSearchRvButtonClicked()
            true
        }

        tilSearchRv.setEndIconOnClickListener { startVoiceRecognitionActivity() }

        vehicleAdapter = SearchRvVehicleAdapter(viewLifecycleOwner)
        rvSearchRvResults.adapter = vehicleAdapter
    }

    private fun observeEvents() = with(viewModel) {
        vehicles.observe(viewLifecycleOwner) { vehicleAdapter.submitList(it) }

        progressIsVisible.observe(viewLifecycleOwner) {
            binding.srlSearchRvResults.isRefreshing = it
        }
    }

    private fun startVoiceRecognitionActivity() {
        val intent = Intent(ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(EXTRA_LANGUAGE_MODEL, LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(EXTRA_PROMPT, getString(search_rv_voice_search_progress_text))
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            val matches = data?.getStringArrayListExtra(EXTRA_RESULTS)
            if (!matches.isNullOrEmpty()) {
                val query = matches[0]
                binding.tietSearchRv.setText(query)
                viewModel.onSearchRvButtonClicked()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {

        private const val VOICE_RECOGNITION_REQUEST_CODE = 1234
    }
}
