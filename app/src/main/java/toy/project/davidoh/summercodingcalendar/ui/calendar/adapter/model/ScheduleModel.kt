package toy.project.davidoh.summercodingcalendar.ui.calendar.adapter.model

import toy.project.davidoh.summercodingcalendar.data.Schedule

interface ScheduleModel {
    fun addItem(item: Schedule)

    fun getItem(position: Int): Schedule

    fun getItemCount(): Int

    fun notifyDataSetChange()

    fun clear()

    var onclick: (Int) -> Unit
}