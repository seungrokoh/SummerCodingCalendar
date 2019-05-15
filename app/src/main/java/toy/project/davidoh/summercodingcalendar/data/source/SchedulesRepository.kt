package toy.project.davidoh.summercodingcalendar.data.source

import com.prolificinteractive.materialcalendarview.CalendarDay
import toy.project.davidoh.summercodingcalendar.data.Schedule
import toy.project.davidoh.summercodingcalendar.data.source.local.SchedulesLocalDataSource

class SchedulesRepository(private val schedulesLocalDataSource: SchedulesLocalDataSource)
    : SchedulesDataSource {

    override fun getSchedules(callback: SchedulesDataSource.LoadSchedulesCallback) {
        schedulesLocalDataSource.getSchedules(object : SchedulesDataSource.LoadSchedulesCallback {
            override fun onSchedulesLoaded(schedules: List<Schedule>) {
                callback.onSchedulesLoaded(schedules)
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }

        })
    }


    override fun addSchedule(date: CalendarDay, title: String, description: String) {
        schedulesLocalDataSource.addSchedule(date, title, description)
    }

    companion object {
        private var INSTANCE: SchedulesRepository? = null

        fun getInstance(schedulesLocalDataSource: SchedulesLocalDataSource): SchedulesRepository {
            if (INSTANCE == null) {
                synchronized(SchedulesRepository::class) {
                    INSTANCE = SchedulesRepository(schedulesLocalDataSource)
                }
            }

            return INSTANCE!!
        }
    }

}