package com.example.android.a2048game

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.help_item.view.*

class ViewPagerAdapter : RecyclerView.Adapter<PagerVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH =
        PagerVH(LayoutInflater.from(parent.context).inflate(R.layout.help_item, parent, false))

    override fun onBindViewHolder(holder: PagerVH, position: Int) = holder.itemView.run {
        if (position == 0) {
            tvHelpTitle.text = resources.getString(R.string.help_title)
            tvHelpText.text = resources.getString(R.string.help_text_1)
            ivHelpImage.setImageResource(R.drawable.help_screen_1)
        }
        if (position == 1) {
            tvHelpTitle.text = resources.getString(R.string.help_title)
            tvHelpText.text = resources.getString(R.string.help_text_2)
            ivHelpImage.setImageResource(R.drawable.help_screen_2)
        }
        if (position == 2) {
            tvHelpTitle.text = resources.getString(R.string.help_title)
            tvHelpText.text = resources.getString(R.string.help_text_3)
            ivHelpImage.setImageResource(R.drawable.help_screen_3)
        }
        if (position == 3) {
            tvHelpTitle.text = resources.getString(R.string.help_title)
            tvHelpText.text = resources.getString(R.string.help_text_4)
            ivHelpImage.setImageResource(R.drawable.help_screen_4)
        }
    }

    override fun getItemCount(): Int = 4
}

class PagerVH(itemView : View) : RecyclerView.ViewHolder(itemView) {

}
