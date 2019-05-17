package toy.project.davidoh.summercodingcalendar.ui.calendar.daily.presenter

import com.prolificinteractive.materialcalendarview.CalendarDay
import toy.project.davidoh.summercodingcalendar.Global.cachedSelectedDate
import toy.project.davidoh.summercodingcalendar.data.Schedule
import toy.project.davidoh.summercodingcalendar.data.source.SchedulesDataSource
import toy.project.davidoh.summercodingcalendar.data.source.SchedulesRepository
import toy.project.davidoh.summercodingcalendar.ui.calendar.adapter.model.ScheduleModel
import toy.project.davidoh.summercodingcalendar.util.nextDay
import toy.project.davidoh.summercodingcalendar.util.preDay

class DailyPresenter(private val view: DailyContractor.View,
                     private val schedulesModel: ScheduleModel,
                     private val schedulesRepository: SchedulesRepository)
    : DailyContractor.Presenter {
    init {
        schedulesModel.onclick = {position ->

        }
    }

    override fun getSchedulesOnDay() {
        schedulesRepository.getSchedulesOnDay(cachedSelectedDate!!, object : SchedulesDataSource.LoadSchedulesCallback {
            override fun onSchedulesLoaded(schedules: List<Schedule>) {
                schedulesModel.clear()
                schedules.forEach {
                    schedulesModel.addItem(it)
                }
                schedulesModel.notifyDataSetChange()
            }

            override fun onDataNotAvailable() {
                // Nothing...
            }
        })
    }

    override fun movePreDay() {
        cachedSelectedDate = preDay(cachedSelectedDate!!)
        getSchedulesOnDay()
        view.showCurrentDate()
    }

    override fun moveNextDay() {
        cachedSelectedDate = nextDay(cachedSelectedDate!!)
        getSchedulesOnDay()
        view.showCurrentDate()
    }


}