package com.myonlineapp.keline.adapters

import androidx.recyclerview.widget.RecyclerView

class ColorsAdapter : RecyclerView.Adapter<ColorsAdapter.ColorsViewHolder>() {

    private var selectedPosition = -1

    inner class ColorsViewHolder(private val binding: ColorRvItemBinding) :
        ViewHolder(binding.root) {
        fun bind(color: Int, position: Int) {
            val imageDrawable = ColorDrawable(color)
            binding.imageColor.setImageDrawable(imageDrawable)
            if (position == selectedPosition) { //Color is selected
                binding.apply {
                    imageShadow.visibility = View.VISIBLE
                    imagePicked.visibility = View.VISIBLE
                }
            } else { //Color is not selected
                binding.apply {
                    imageShadow.visibility = View.INVISIBLE
                    imagePicked.visibility = View.INVISIBLE
                }
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorsViewHolder {
        return ColorsViewHolder(
            ColorRvItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: ColorsViewHolder, position: Int) {
        val color = differ.currentList[position]
        holder.bind(color, position)

        holder.itemView.setOnClickListener {
            if (selectedPosition >= 0)
                notifyItemChanged(selectedPosition)
            selectedPosition = holder.adapterPosition
            notifyItemChanged(selectedPosition)
            onItemClick?.invoke(color)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    var onItemClick: ((Int) -> Unit)? = null
}