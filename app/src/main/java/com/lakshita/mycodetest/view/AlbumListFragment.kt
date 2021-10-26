package com.lakshita.mycodetest.view

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lakshita.mycodetest.MainActivity
import com.lakshita.mycodetest.R
import com.lakshita.mycodetest.dagger.DaggerFragmentComponent
import com.lakshita.mycodetest.dagger.FragmentComponent
import com.lakshita.mycodetest.databinding.FragmentAlbumListBinding
import com.lakshita.mycodetest.repo.AlbumRepository
import com.lakshita.mycodetest.viewmodel.AlbumListViewModel
import javax.inject.Inject

class AlbumListFragment : Fragment() {

    @Inject
    lateinit var albumRepository: AlbumRepository

    private var fragmentComponent: FragmentComponent? = null

    private val viewModel: AlbumListViewModel by lazy {
        requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, AlbumListViewModel.Factory(albumRepository))
            .get(AlbumListViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        getNewFragmentComponent().inject(this)
        super.onAttach(context)
    }

    private var _binding: FragmentAlbumListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumListBinding.inflate(inflater, container, false)
        binding.pBar.visibility = View.VISIBLE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = AlbumAdapter()
        binding.albumRecyclerView.adapter = adapter
        binding.albumRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewModel.albumList.observe(viewLifecycleOwner, { albums ->
            albums?.apply {
                binding.pBar.visibility = View.GONE
                adapter.albums = albums
            }
        })
        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->
            if (!dataState) {
                val builder = AlertDialog.Builder(requireActivity())
                builder.setTitle(R.string.error_dialog_title)
                builder.setMessage(R.string.error_dialog_message)
                builder.setPositiveButton(R.string.error_dialog_button_text) { dialog, _ ->
                    dialog.dismiss()
                }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        })
    }

    private fun getNewFragmentComponent() : FragmentComponent {
        if (fragmentComponent == null) {
            fragmentComponent = DaggerFragmentComponent.builder()
                .appComponent((requireActivity() as MainActivity).appComponent)
                .build()
        }
        return fragmentComponent as FragmentComponent
    }
}