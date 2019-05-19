package toy.project.davidoh.summercodingcalendar.data.source

import com.prolificinteractive.materialcalendarview.CalendarDay
import toy.project.davidoh.summercodingcalendar.data.Schedule

interface SchedulesDataSource {

    interface InsertScheduleCallback {
        fun onScheduleInserted()

        fun onInsertFailed()
    }


    suspend fun getSchedulesAllDay() : Result<List<Schedule>>

    suspend fun getSchedulesOnDay(date: CalendarDay) : Result<List<Schedule>>

    fun addSchedule(schedule: Schedule, callback: InsertScheduleCallback)

    fun refreshSchedules()
}