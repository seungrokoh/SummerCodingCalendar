package toy.project.davidoh.summercodingcalendar.data.source

import com.prolificinteractive.materialcalendarview.CalendarDay

interface SchedulesDataSource {
    interface LoadSchedulesCallback {
        fun onSchedulesLoaded(scheduls: ArrayList<CalendarDay>)

        fun onDataNotAvailable()

    }

    fun getSchedules(callback: LoadSchedulesCallback)
}