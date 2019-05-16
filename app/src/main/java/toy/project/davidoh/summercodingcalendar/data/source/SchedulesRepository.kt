package toy.project.davidoh.summercodingcalendar.data.source

import com.prolificinteractive.materialcalendarview.CalendarDay
import toy.project.davidoh.summercodingcalendar.data.Schedule
import toy.project.davidoh.summercodingcalendar.data.source.local.SchedulesLocalDataSource
import toy.project.davidoh.summercodingcalendar.util.logE

class SchedulesRepository(private val schedulesLocalDataSource: SchedulesLocalDataSource)
    : SchedulesDataSource {
    var cachedSchedules: LinkedHashMap<String, Schedule> = LinkedHashMap()

    var cacheIsDirty = false

    override fun getSchedulesAllDay(callback: SchedulesDataSource.LoadSchedulesCallback) {
        logE("getSchedulesAllDay -> cachedSchedules : ${cachedSchedules.values}")
        if (cachedSchedules.isNotEmpty() && !cacheIsDirty) {
            callback.onSchedulesLoaded(ArrayList(cachedSchedules.values))
        } else {
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

    override fun getScheduleOnDay(date: CalendarDay, callback: SchedulesDataSource.LoadSchedulesCallback) {
        if (cachedSchedules.isNotEmpty() && !cacheIsDirty) {
            callback.onSchedulesLoaded(cachedSchedules.values.filter { it.date == date }.toList())
        } else {
            schedulesLocalDataSource.getScheduleOnDay(date, object : SchedulesDataSource.LoadSchedulesCallback {
                override fun onSchedulesLoaded(schedules: List<Schedule>) {
                    refreshCache(schedules)
                    callback.onSchedulesLoaded(cachedSchedules.values.filter { it.date == date }.toList())
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