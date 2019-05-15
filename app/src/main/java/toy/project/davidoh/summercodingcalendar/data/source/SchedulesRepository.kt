package toy.project.davidoh.summercodingcalendar.data.source

import com.prolificinteractive.materialcalendarview.CalendarDay
import toy.project.davidoh.summercodingcalendar.data.source.local.SchedulesLocalDataSource

object SchedulesRepository : SchedulesDataSource {

    private val schedulesDataSource = SchedulesLocalDataSource()

    override fun getSchedules(callback: SchedulesDataSource.LoadSchedulesCallback) {
        schedulesDataSource.getSchedules(object : SchedulesDataSource.LoadSchedulesCallback {
            override fun onSchedulesLoaded(scheduls: ArrayList<CalendarDay>) {
                callback.onSchedulesLoaded(scheduls)
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }

        })
    }

}