package com.michaelflisar.materialpreferences.preferencescreen.choice

import android.content.res.ColorStateList
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.michaelflisar.materialpreferences.preferencescreen.choice.databinding.DialogChoiceItemBinding
import com.michaelflisar.materialpreferences.preferencescreen.choice.single.SingleChoicePreference

class SingleChoiceAdapter(
    val mode: SingleChoicePreference.DisplayType,
    val items: List<Item>,
    val onItemClicked: (item: Item, index: Int) -> Unit
) : RecyclerView.Adapter<SingleChoiceAdapter.ViewHolder>() {

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
        val adapter: SingleChoiceAdapter,
        val binding: DialogChoiceItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.mdControlRadio.visibility =
                if (adapter.mode == SingleChoicePreference.DisplayType.Checkbox) View.VISIBLE else View.GONE
        }

        fun bind(item: Item) {
            binding.text.text = item.label
            when (adapter.mode) {
                SingleChoicePreference.DisplayType.Checkbox -> {
                    binding.mdControlRadio.isChecked = item.selected
                }
                is SingleChoicePreference.DisplayType.Highlighted -> {
                    if (adapter.mode.bold) {
                        binding.text.setTypeface(
                            binding.text.typeface,
                            if (item.selected) Typeface.BOLD else Typeface.NORMAL
                        )
                    }
                    if (adapter.mode.primaryColor) {
                        binding.text.setTextColor(if (item.selected) ColorStateList.valueOf(binding.text.context.colorAccent) else binding.text.context.textColor)
                    }
                }
                SingleChoicePreference.DisplayType.None -> {
                    // nothing to do
                }
            }
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