package github.idmeetrious.moxytestapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import github.idmeetrious.moxytestapp.R
import github.idmeetrious.moxytestapp.domain.entities.Repository

class RepositoryListAdapter(val onClick: (Repository) -> Unit) : RecyclerView.Adapter<RepositoryViewHolder>() {
    private var list: List<Repository> = emptyList()

    fun updateList(list: List<Repository>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_repository_list_item, parent, false)
        return RepositoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        val item = list[position]
        holder.name.text = item.name
        holder.downloadBtn.setOnClickListener {
            item?.let {
                onClick(it)
            }
        }
    }

    override fun getItemCount(): Int = list.size
}