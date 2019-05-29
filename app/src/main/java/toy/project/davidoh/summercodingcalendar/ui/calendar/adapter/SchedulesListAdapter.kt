package toy.project.davidoh.summercodingcalendar.ui.calendar.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import toy.project.davidoh.summercodingcalendar.data.Schedule
import toy.project.davidoh.summercodingcalendar.ui.calendar.adapter.holder.ScheduleListHolder
import toy.project.davidoh.summercodingcalendar.ui.calendar.adapter.model.ScheduleModel

class SchedulesListAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), ScheduleModel {
    private val scheduleList = mutableListOf<Schedule>()

    override lateinit var onclick: (Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ScheduleListHolder(onclick, context, parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? ScheduleListHolder)?.onBind(scheduleList[position])
    }

    override fun addItem(item: Schedule) {
        scheduleList.add(item)
    }

    override fun getItem(position: Int): Schedule {
        return scheduleList[position]
    }

    override fun getItemCount(): Int {
        return scheduleList.size
    }

    override fun removeItem(position: Int) {
        scheduleList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun notifyDataSetChange() {
        notifyDataSetChanged()
    }

    override fun isEmpty(): Boolean {
        return scheduleList.isEmpty()
    }

    override fun clear() {
        scheduleList.clear()
    }


}