package toy.project.davidoh.summercodingcalendar.data.source

import toy.project.davidoh.summercodingcalendar.data.Schedule
import toy.project.davidoh.summercodingcalendar.data.source.local.SchedulesLocalDataSource
import toy.project.davidoh.summercodingcalendar.util.logE

class SchedulesRepository(private val schedulesLocalDataSource: SchedulesLocalDataSource)
    : SchedulesDataSource {
    var cachedSchedules: LinkedHashMap<String, Schedule> = LinkedHashMap()

    var cacheIsDirty = false

    override fun getSchedulesAllDay(callback: SchedulesDataSource.LoadSchedulesCallback) {
        if (cachedSchedules.isNotEmpty() && !cacheIsDirty) {
            logE("캐시가 변하지 않음")
            callback.onSchedulesLoaded(ArrayList(cachedSchedules.values))
            return
        }

        if (cacheIsDirty) {
            logE("캐시가 변함")
            schedulesLocalDataSource.getSchedulesAllDay(object : SchedulesDataSource.LoadSchedulesCallback {
                override fun onSchedulesLoaded(schedules: List<Schedule>) {
                    refreshCache(schedules)
                    callback.onSchedulesLoaded(ArrayList(cachedSchedules.values))
                }

                override fun onDataNotAvailable() {
                    callback.onDataNotAvailable()
                }

            })
        }

    }

    override fun addSchedule(schedule: Schedule, callback: SchedulesDataSource.InsertScheduleCallback) {
        cacheAndPerform(schedule) {
            schedulesLocalDataSource.addSchedule(it, object : SchedulesDataSource.InsertScheduleCallback {
                override fun onScheduleInserted() {
                    refreshSchedules()
                    callback.onScheduleInserted()
                }

                override fun onInsertFailed() {
                    deleteScheduleWithId(it.id)
                    callback.onInsertFailed()
                }

            })
        }
    }

    private inline fun cacheAndPerform(schedule: Schedule, perform: (Schedule) -> Unit) {
        cachedSchedules[schedule.id] = schedule
        perform(schedule)
    }

    override fun refreshSchedules() {
        cacheIsDirty = true
    }

    private fun refreshCache(schedules: List<Schedule>) {
        cachedSchedules.clear()
        schedules.forEach {
            cacheAndPerform(it){}
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