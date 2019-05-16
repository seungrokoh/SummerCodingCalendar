package toy.project.davidoh.summercodingcalendar.util

import android.content.Context
import toy.project.davidoh.summercodingcalendar.data.source.SchedulesRepository
import toy.project.davidoh.summercodingcalendar.data.source.local.ScheduleDatabase
import toy.project.davidoh.summercodingcalendar.data.source.local.SchedulesLocalDataSource

object Injection {
    fun provideTaskRepository(context: Context): SchedulesRepository {
        val database = ScheduleDatabase.getInstance(context)
        return SchedulesRepository.getInstance(SchedulesLocalDataSource.getInstance(database.SchedulesDao()))
    }
}