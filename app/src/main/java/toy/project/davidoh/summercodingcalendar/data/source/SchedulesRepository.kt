package toy.project.davidoh.summercodingcalendar.data.source

import com.prolificinteractive.materialcalendarview.CalendarDay
import toy.project.davidoh.summercodingcalendar.data.Schedule
import toy.project.davidoh.summercodingcalendar.data.source.local.SchedulesLocalDataSource

class SchedulesRepository(private val schedulesLocalDataSource: SchedulesLocalDataSource)
    : SchedulesDataSource {

    var cachedSchedules: LinkedHashMap<String, Schedule> = LinkedHashMap()

    var cacheIsDirty = false

    override suspend fun getSchedulesAllDay(): Result<List<Schedule>> {
        if (cachedSchedules.isNotEmpty() && !cacheIsDirty) {
            return Result.Success(cachedSchedules.values.toList())
        }

        val result = schedulesLocalDataSource.getSchedulesAllDay()
        return when(result) {
            is Result.Success -> {
                refreshCache(result.data)
                Result.Success(result.data)
            }
            is Result.Error -> {
                Result.Error(LocalDataNotFoundException())
            }
        }
    }

    override suspend fun getSchedulesOnDay(date: CalendarDay): Result<List<Schedule>> {
        if (cachedSchedules.isNotEmpty() && !cacheIsDirty) {
            return Result.Success(cachedSchedules.values.filter { it.date == date }.toList())
        }
        val result = schedulesLocalDataSource.getSchedulesOnDay(date)
        return when(result) {
            is Result.Success -> {
                Result.Success(cachedSchedules.values.filter { it.date == date }.toList())
            }
            is Result.Error -> {
                Result.Error(LocalDataNotFoundException())
            }
        }
    }

    override suspend fun addSchedule(schedule: Schedule) : Long {
        cacheSchedule(schedule).let {
            val result = schedulesLocalDataSource.addSchedule(it)
            if (result > 0) {
                refreshSchedules()
            } else {
                deleteScheduleWithId(it.id)
            }
            return result
        }
    }

    private fun cacheSchedule(schedule: Schedule) : Schedule {
        cachedSchedules[schedule.id] = schedule
        return schedule
    }

    override fun refreshSchedules() {
        cacheIsDirty = true
    }

    private fun refreshCache(schedules: List<Schedule>) {
        cachedSchedules.clear()
        schedules.forEach {
            cacheSchedule(it)
        }
        cacheIsDirty = false
    }

    private fun deleteScheduleWithId(id: String) {
        cachedSchedules.remove(id)
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