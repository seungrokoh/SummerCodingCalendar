package toy.project.davidoh.summercodingcalendar.ui.calendar.weekly.presenter

import com.prolificinteractive.materialcalendarview.CalendarDay
import toy.project.davidoh.summercodingcalendar.data.source.SchedulesRepository
import toy.project.davidoh.summercodingcalendar.util.logE

class WeeklyPresenter(private val view: WeeklyContractor.View,
                      private val schedulesRepository: SchedulesRepository)
    : WeeklyContractor.Presenter {

    override fun loadScheduleOnDay(date: CalendarDay) {
        logE(date.toString())
    }


}