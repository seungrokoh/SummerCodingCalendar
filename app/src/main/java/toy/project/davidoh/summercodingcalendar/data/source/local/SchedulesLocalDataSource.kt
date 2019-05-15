package toy.project.davidoh.summercodingcalendar.data.source.local

import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import toy.project.davidoh.summercodingcalendar.data.Schedule
import toy.project.davidoh.summercodingcalendar.data.source.SchedulesDataSource

class SchedulesLocalDataSource(private val schedulesDao: SchedulesDao) : SchedulesDataSource {

    override fun getSchedules(callback: SchedulesDataSource.LoadSchedulesCallback) {
        CoroutineScope(Dispatchers.IO).launch {
            callback.onSchedulesLoaded(schedulesDao.getAllSchedules())
        }
    }

    override fun addSchedule(date: CalendarDay, title: String, description: String) {
        CoroutineScope(Dispatchers.IO).launch {
            schedulesDao.addSchedule(Schedule(title = title, description = description, date = date))
        }
    }

    companion object {
        private var INSTANCE: SchedulesLocalDataSource? = null

        fun getInstance(schedulesDao: SchedulesDao) : SchedulesLocalDataSource {
            if (INSTANCE == null) {
                synchronized(SchedulesLocalDataSource::class) {
                    INSTANCE = SchedulesLocalDataSource(schedulesDao)
                }
            }
            return INSTANCE!!
        }
    }

}