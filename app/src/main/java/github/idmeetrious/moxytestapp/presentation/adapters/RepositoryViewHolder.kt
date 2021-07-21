package github.idmeetrious.moxytestapp.presentation.adapters

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import github.idmeetrious.moxytestapp.R

class RepositoryViewHolder(view: View): RecyclerView.ViewHolder(view) {
    var name: TextView
    var downloadBtn: ImageButton

    init {
        view.let {
            name = it.findViewById(R.id.repository_list_item_name_tv)
            downloadBtn = it.findViewById(R.id.repository_list_item_ib)
        }
    }
}