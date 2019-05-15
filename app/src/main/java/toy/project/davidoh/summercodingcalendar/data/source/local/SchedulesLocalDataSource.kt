package toy.project.davidoh.summercodingcalendar.data.source.local

import com.prolificinteractive.materialcalendarview.CalendarDay
import org.threeten.bp.LocalDate
import toy.project.davidoh.summercodingcalendar.data.Schedule
import toy.project.davidoh.summercodingcalendar.data.source.SchedulesDataSource

class SchedulesLocalDataSource : SchedulesDataSource {
//    val dummy = arrayListOf(
//        Schedule("1", "title1", "desc1", LocalDate.of(2019,5,14)),
//        Schedule("2", "title2", "desc2", LocalDate.of(2019,5,14)),
//        Schedule("3", "title3", "desc3", LocalDate.of(2019,5,12)),
//        Schedule("4", "title4", "desc4", LocalDate.of(2019,5,23)),
//        Schedule("5", "title5", "desc5", LocalDate.of(2019,5,21)))

    val dummy = arrayListOf<CalendarDay>(
        CalendarDay.from(LocalDate.of(2019,5,14)),
        CalendarDay.from(LocalDate.of(2019,5,3)),
        CalendarDay.from(LocalDate.of(2019,5,12)),
        CalendarDay.from(LocalDate.of(2019,5,23)),
        CalendarDay.from(LocalDate.of(2019,5,21))
    )

    override fun getSchedules(callback: SchedulesDataSource.LoadSchedulesCallback) {
        callback.onSchedulesLoaded(dummy)
    }

}