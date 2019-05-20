package toy.project.davidoh.summercodingcalendar.data.source.local

import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.coroutines.*
import toy.project.davidoh.summercodingcalendar.data.Schedule
import toy.project.davidoh.summercodingcalendar.data.source.LocalDataNotFoundException
import toy.project.davidoh.summercodingcalendar.data.source.Result
import toy.project.davidoh.summercodingcalendar.data.source.SchedulesDataSource
import toy.project.davidoh.summercodingcalendar.util.AppExecutors

class SchedulesLocalDataSource(private val appExecutors: AppExecutors,
                               private val schedulesDao: SchedulesDao) : SchedulesDataSource {

    override suspend fun getSchedulesAllDay(): Result<List<Schedule>> = withContext(appExecutors.ioContext) {
        val list = async { schedulesDao.getAllSchedules() }
        try {
            val result = list.await()
            if (result.isNotEmpty()) {
                Result.Success(result)
            } else {
                Result.Error(LocalDataNotFoundException())
            }
        } catch (ex: Throwable) {
            Result.Error(LocalDataNotFoundException())
        }
    }

    override suspend fun getSchedulesOnDay(date: CalendarDay): Result<List<Schedule>> = withContext(appExecutors.ioContext) {
        val list = async { schedulesDao.getSchedulesOnDay(date) }
        try {
            val result = list.await()
            if (result.isNotEmpty()) {
                Result.Success(result)
            } else {
                Result.Error(LocalDataNotFoundException())
            }
        } catch (ex: Throwable) {
            Result.Error(LocalDataNotFoundException())
        }

    }

    override suspend fun addSchedule(schedule: Schedule) = withContext(appExecutors.ioContext) {
        schedulesDao.addSchedule(schedule)
    }

    override fun refreshSchedules() {
        // Nothing...
    }

    companion object {
        private var INSTANCE: SchedulesLocalDataSource? = null

        fun getInstance(appExecutors: AppExecutors, schedulesDao: SchedulesDao) : SchedulesLocalDataSource {
            if (INSTANCE == null) {
                synchronized(SchedulesLocalDataSource::class) {
                    INSTANCE = SchedulesLocalDataSource(appExecutors, schedulesDao)
                }
            }
            return INSTANCE!!
        }
    }

}