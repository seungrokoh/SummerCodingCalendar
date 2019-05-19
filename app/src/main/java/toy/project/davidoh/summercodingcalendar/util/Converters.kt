package toy.project.davidoh.summercodingcalendar.util

import androidx.room.TypeConverter
import com.prolificinteractive.materialcalendarview.CalendarDay
import org.threeten.bp.LocalDate

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?) : CalendarDay? {
        return value?.let { CalendarDay.from(LocalDate.ofEpochDay(value)) }
    }

    @TypeConverter
    fun dateToCalendarDay(date: CalendarDay?) : Long? {
        return date?.date?.toEpochDay()
    }
}