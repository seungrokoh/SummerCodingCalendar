package toy.project.davidoh.summercodingcalendar.util

import com.prolificinteractive.materialcalendarview.CalendarDay
import org.threeten.bp.LocalDate

fun nowLocalDate(): LocalDate
        = LocalDate.now()

fun preDay(currentDay: CalendarDay): CalendarDay
        = CalendarDay.from(currentDay.date.minusDays(1))

fun nextDay(currentDay: CalendarDay): CalendarDay
        = CalendarDay.from(currentDay.date.plusDays(1))