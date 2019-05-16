package toy.project.davidoh.summercodingcalendar.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import toy.project.davidoh.summercodingcalendar.data.Schedule

@Dao interface SchedulesDao {
    @Query("SELECT * FROM schedules")
    fun getAllSchedules() : List<Schedule>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSchedule(schedule: Schedule): Long

}