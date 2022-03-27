package com.example.jaydean.tutorial.adapter
import android.widget.TextView
import com.example.jaydean.tutorial.model.Affirmation
import com.example.jaydean.tutorial.R
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
/**
 * Created by jaydean on 2022/3/27.
 */
// 从xml解析需要的列表适配类
class ItemAdapter(
        private val context: Context,
        private val dataset: List<Affirmation>
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    // Create a new view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        //  一个viewholder代表列表的一个item，parent是RecyclerView，LayoutInflater解析xml，
        val adapterLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.textView.text =  context.resources.getString(item.stringResourceId) // holder里目前只有textView
    }

    override fun getItemCount() = dataset.size

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.item_title) as TextView
    }
}