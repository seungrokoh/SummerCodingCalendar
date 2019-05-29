package toy.project.davidoh.summercodingcalendar.data.source

import com.prolificinteractive.materialcalendarview.CalendarDay
import toy.project.davidoh.summercodingcalendar.data.Schedule

interface SchedulesDataSource {

    suspend fun getSchedulesAllDay() : Result<List<Schedule>>

    suspend fun getSchedulesOnDay(date: CalendarDay) : Result<List<Schedule>>

    suspend fun addSchedule(schedule: Schedule): Long

    suspend fun deleteSchedule(schedule: Schedule) : Int

    fun refreshSchedules()
}