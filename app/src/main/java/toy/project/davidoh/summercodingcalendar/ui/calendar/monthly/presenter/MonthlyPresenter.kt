package toy.project.davidoh.summercodingcalendar.ui.calendar.monthly.presenter

import com.prolificinteractive.materialcalendarview.CalendarDay
import toy.project.davidoh.summercodingcalendar.data.Schedule
import toy.project.davidoh.summercodingcalendar.data.source.SchedulesDataSource
import toy.project.davidoh.summercodingcalendar.data.source.SchedulesRepository
import toy.project.davidoh.summercodingcalendar.util.logE

class MonthlyPresenter(private val view: MonthlyContractor.View,
                       private val schedulesRepository: SchedulesRepository)
    : MonthlyContractor.Presenter {

    override fun loadSechedules() {
        schedulesRepository.getSchedulesAllDay(object : SchedulesDataSource.LoadSchedulesCallback {
            override fun onSchedulesLoaded(schedules: List<Schedule>) {
                addDotDecorator(schedules)
            }

            override fun onDataNotAvailable() {
                view.showInfoMessage("일정이 없습니다.")
            }
        })

    }

    private fun addDotDecorator(schedules: List<Schedule>) {
        val dateList = mutableListOf<CalendarDay>()
        for (item in schedules) {
            dateList.add(item.date)
        }
        view.showDecorateOnCalendar(dateList)
    }

}