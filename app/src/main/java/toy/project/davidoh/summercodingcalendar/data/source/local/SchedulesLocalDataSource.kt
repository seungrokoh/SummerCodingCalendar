package toy.project.davidoh.summercodingcalendar.data.source.local

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import toy.project.davidoh.summercodingcalendar.data.Schedule
import toy.project.davidoh.summercodingcalendar.data.source.SchedulesDataSource

class SchedulesLocalDataSource(private val schedulesDao: SchedulesDao) : SchedulesDataSource {
    override fun getSchedulesAllDay(callback: SchedulesDataSource.LoadSchedulesCallback) {
        CoroutineScope(Dispatchers.IO).launch {
            callback.onSchedulesLoaded(schedulesDao.getAllSchedules())
        }
    }

    override fun addSchedule(schedule: Schedule, callback: SchedulesDataSource.InsertScheduleCallback) {
        CoroutineScope(Dispatchers.Main).launch {
            var result: Long = -1
            CoroutineScope(Dispatchers.IO).async {
                result = schedulesDao.addSchedule(schedule)
            }.await()
            if (result > 0) {
                callback.onScheduleInserted()
            } else {
                callback.onInsertFailed()
            }
        }
    }

    override fun refreshSchedules() {
        // Nothing...
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