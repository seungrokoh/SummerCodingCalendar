package toy.project.davidoh.summercodingcalendar.ui.calendar.adapter.holder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_schedule_list.view.*
import toy.project.davidoh.summercodingcalendar.R
import toy.project.davidoh.summercodingcalendar.data.Schedule

class ScheduleListHolder(onclick: (Int) -> Unit, context: Context, parent: ViewGroup)
    : RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_schedule_list, parent, false)) {

    init {
        itemView.setOnClickListener {
            onclick(adapterPosition)
        }
    }

    fun onBind(item: Schedule) {
        itemView.onBind(item)
    }

    private fun View.onBind(item: Schedule) {
        tv_title.text = item.title
        tv_description.text = item.description
    }
}