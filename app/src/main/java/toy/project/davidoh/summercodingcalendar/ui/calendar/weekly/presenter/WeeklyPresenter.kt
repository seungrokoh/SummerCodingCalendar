package toy.project.davidoh.summercodingcalendar.ui.calendar.weekly.presenter

import com.prolificinteractive.materialcalendarview.CalendarDay
import toy.project.davidoh.summercodingcalendar.data.Schedule
import toy.project.davidoh.summercodingcalendar.data.source.SchedulesDataSource
import toy.project.davidoh.summercodingcalendar.data.source.SchedulesRepository
import toy.project.davidoh.summercodingcalendar.ui.calendar.adapter.model.ScheduleModel
import toy.project.davidoh.summercodingcalendar.util.logE

class WeeklyPresenter(private val view: WeeklyContractor.View,
                      private val scheduleModel: ScheduleModel,
                      private val schedulesRepository: SchedulesRepository)
    : WeeklyContractor.Presenter {
    init {
        scheduleModel.onclick = { position ->
            logE(scheduleModel.getItem(position).toString())
        }
    }

    override fun getSchedulesOnDay(date: CalendarDay) {
        schedulesRepository.getSchedulesOnDay(date, object : SchedulesDataSource.LoadSchedulesCallback {
            override fun onSchedulesLoaded(schedules: List<Schedule>) {
                scheduleModel.clear()
                schedules.forEach {
                    scheduleModel.addItem(it)
                }
                scheduleModel.notifyDataSetChange()
            }

            override fun onDataNotAvailable() {
                // Nothing...
            }
        })
    }

    override fun loadSchedulesAllDay() {
        schedulesRepository.getSchedulesAllDay(object : SchedulesDataSource.LoadSchedulesCallback {
            override fun onSchedulesLoaded(schedules: List<Schedule>) {
                addDotDecorator(schedules)
            }

            override fun onDataNotAvailable() {
                // Nothing...
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