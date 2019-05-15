package toy.project.davidoh.summercodingcalendar.data.source

import com.prolificinteractive.materialcalendarview.CalendarDay
import toy.project.davidoh.summercodingcalendar.data.Schedule

interface SchedulesDataSource {
    interface LoadSchedulesCallback {
        fun onSchedulesLoaded(schedules: List<Schedule>)

        fun onDataNotAvailable()
    }

    fun getSchedules(callback: LoadSchedulesCallback)

    fun addSchedule(date: CalendarDay, title: String, description: String)
}