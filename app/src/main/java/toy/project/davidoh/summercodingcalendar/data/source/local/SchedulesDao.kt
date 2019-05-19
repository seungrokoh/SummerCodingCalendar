package toy.project.davidoh.summercodingcalendar.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.prolificinteractive.materialcalendarview.CalendarDay
import toy.project.davidoh.summercodingcalendar.data.Schedule

@Dao interface SchedulesDao {
    @Query("SELECT * FROM schedules")
    fun getAllSchedules() : List<Schedule>

    @Query("SELECT * FROM schedules WHERE date = :date")
    fun getSchedulesOnDay(date: CalendarDay) : List<Schedule>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSchedule(schedule: Schedule): Long
}