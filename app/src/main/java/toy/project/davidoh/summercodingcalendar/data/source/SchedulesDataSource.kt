package toy.project.davidoh.summercodingcalendar.data.source

import com.prolificinteractive.materialcalendarview.CalendarDay
import toy.project.davidoh.summercodingcalendar.data.Schedule

interface SchedulesDataSource {
    interface LoadSchedulesCallback {
        fun onSchedulesLoaded(schedules: List<Schedule>)

        fun onDataNotAvailable()
    }

    interface InsertScheduleCallback {
        fun onScheduleInserted()

        fun onInsertFailed()
    }

    fun getSchedulesAllDay(callback: LoadSchedulesCallback)

    fun getSchedulesOnDay(date: CalendarDay, callback: LoadSchedulesCallback)

    fun addSchedule(schedule: Schedule, callback: InsertScheduleCallback)

    fun refreshSchedules()
}