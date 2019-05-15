package toy.project.davidoh.summercodingcalendar.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.util.*

@Entity(tableName = "schedules")
data class Schedule(
    @PrimaryKey @ColumnInfo(name = "scheduleId") val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "date") var date: CalendarDay
)