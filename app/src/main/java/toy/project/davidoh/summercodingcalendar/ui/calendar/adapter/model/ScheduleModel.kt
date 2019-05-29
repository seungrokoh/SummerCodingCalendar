package toy.project.davidoh.summercodingcalendar.ui.calendar.adapter.model

import toy.project.davidoh.summercodingcalendar.data.Schedule

interface ScheduleModel {
    fun addItem(item: Schedule)

    fun getItem(position: Int): Schedule

    fun getItemCount(): Int

    fun removeItem(position: Int)

    fun notifyDataSetChange()

    fun isEmpty() : Boolean

    fun clear()

    var onclick: (Int) -> Unit
}