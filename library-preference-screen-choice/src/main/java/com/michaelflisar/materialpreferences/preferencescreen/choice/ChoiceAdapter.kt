package com.michaelflisar.materialpreferences.preferencescreen.choice

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.michaelflisar.materialpreferences.preferencescreen.choice.databinding.DialogChoiceItemBinding

// currently unused... maybe choices will need to support icons on the right at some time...


class ChoiceAdapter(
    val mode: Mode,
    val items: List<Item>,
    val onItemClicked: (item: Item, index: Int) -> Unit
) : RecyclerView.Adapter<ChoiceAdapter.ViewHolder>() {

    enum class Mode {
        Single,
        SingleCheckbox,
        MultiCheckbox
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            this,
            DialogChoiceItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        holder.unbind()
    }

    override fun getItemCount() = items.size

    class ViewHolder(
        val adapter: ChoiceAdapter,
        val binding: DialogChoiceItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.mdControlCheckbox.visibility =
                if (adapter.mode == Mode.MultiCheckbox) View.VISIBLE else View.GONE
            binding.mdControlRadio.visibility =
                if (adapter.mode == Mode.SingleCheckbox) View.VISIBLE else View.GONE
        }

        fun bind(item: Item) {
            binding.text.text = item.label
            if (adapter.mode == Mode.Single) {
                binding.text.setTypeface(
                    binding.text.typeface,
                    if (item.selected) Typeface.BOLD else Typeface.NORMAL
                )
            }
            binding.mdControlRadio.isChecked = item.selected
            binding.mdControlCheckbox.isChecked = item.selected
            binding.root.setOnClickListener {
                adapter.onItemClicked(item, absoluteAdapterPosition)
            }
        }

        fun unbind() {
            binding.text.text = null
            binding.root.setOnClickListener(null)
        }
    }

    class Item(
        val label: String,
        val selected: Boolean
    )
}