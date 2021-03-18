package com.michaelflisar.materialpreferences.preferencescreen.choice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.michaelflisar.materialpreferences.preferencescreen.choice.databinding.DialogChoiceItemBinding

// currently unused... maybe choices will need to support icons on the right at some time...

/*
class ChoiceAdapter(
        val items: List<ChoiceItem>
) : RecyclerView.Adapter<ChoiceAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(DialogChoiceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        holder.unbind()
    }

    override fun getItemCount() = items.size

    class ViewHolder(val binding: DialogChoiceItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ChoiceItem) {
            binding.text.text = item.label.get(itemView.context)
        }

        fun unbind() {
            binding.text.text = null
        }
    }
}
*/