package github.idmeetrious.moxytestapp.presentation.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import github.idmeetrious.moxytestapp.databinding.FragmentRepositoryListBinding
import github.idmeetrious.moxytestapp.domain.entities.Repository
import github.idmeetrious.moxytestapp.presentation.adapters.RepositoryListAdapter
import github.idmeetrious.moxytestapp.presentation.presenters.RepositoryListPresenter
import github.idmeetrious.moxytestapp.presentation.views.RepositoryListView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

private const val TAG = "RepositoryListFragment"

class RepositoryListFragment : MvpAppCompatFragment(), RepositoryListView {
    private var _binding: FragmentRepositoryListBinding? = null
    private val binding get() = _binding!!

    private val presenter by moxyPresenter { RepositoryListPresenter() }

    private var adapter: RepositoryListAdapter? = null
    private var rv: RecyclerView? = null
    private var pb: ProgressBar? = null
    private var downloadZip: ByteArray? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRepositoryListBinding.inflate(inflater, container, false)
        val rootView = binding.root
        bindViews()
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setRecycler()
        setSearchClickListener()
    }

    private fun setSearchClickListener() {
        binding.searchIb.setOnClickListener {
            val query = binding.searchEt.text?.toString()?.trim()
            if (!query.isNullOrEmpty()) {
                binding.searchEtLayout.clearFocus()
                binding.searchEtLayout.isErrorEnabled = false
                presenter.getUserRepos(query)
            } else {
                binding.searchEtLayout.isErrorEnabled = true
                binding.searchEtLayout.error = "Incorrect query, check your input"
            }
        }
    }

    private fun setAdapter() {
        lifecycleScope.launchWhenStarted {
            presenter.repositories.collect {
                adapter?.updateList(it)
            }
        }
    }

    private fun setRecycler() {
        rv?.setHasFixedSize(true)
        rv?.layoutManager = LinearLayoutManager(requireContext())
        rv?.adapter = adapter
    }

    private fun bindViews() {
        adapter = RepositoryListAdapter { item -> onItemClick(item) }
        rv = binding.recyclerView
        pb = binding.progressBar
    }

    private fun onItemClick(item: Repository) {
        Log.i(TAG, "--> onItemClick: ${item.url}")
        presenter.downloadRepo(item)
    }

    override fun saveTo(data: ByteArray, name: String) {
        downloadZip = data
        saveWithIntent(name)
    }

    private fun saveWithIntent(name: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "application/zip"
                putExtra(Intent.EXTRA_TITLE, "${name}.zip")
            }
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            data?.data?.let { uri ->
                Log.i(TAG, "--> onActivityResult: $uri")
                downloadZip?.let {
                    presenter.pendingSaveTo(uri, it)
                }
            }
        }
//        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun loading() {
        Log.i(TAG, "--> loading: ")
        pb?.visibility = View.VISIBLE
        rv?.visibility = View.INVISIBLE
    }

    override fun success() {
        Log.i(TAG, "--> success: ")
        pb?.visibility = View.INVISIBLE
        rv?.visibility = View.VISIBLE
    }

    override fun data(list: List<Repository>) {
        Log.i(TAG, "--> data: ${list.size}")
    }

    override fun error(msg: String) {
        Log.i(TAG, "--> error: $msg")
        pb?.visibility = View.GONE
        rv?.visibility = View.GONE
        showErrorMsg(msg)
    }

    private fun showErrorMsg(msg: String) {
        view?.let {
            Snackbar
                .make(it, msg, Snackbar.LENGTH_SHORT)
                .setTextColor(Color.WHITE)
                .show()
        }
    }


}