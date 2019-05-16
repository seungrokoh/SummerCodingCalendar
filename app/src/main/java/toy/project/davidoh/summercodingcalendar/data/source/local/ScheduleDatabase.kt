package toy.project.davidoh.summercodingcalendar.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import toy.project.davidoh.summercodingcalendar.data.Schedule
import toy.project.davidoh.summercodingcalendar.util.Converters
import toy.project.davidoh.summercodingcalendar.util.logE

@Database(entities = [Schedule::class], version = 2)
@TypeConverters(Converters::class)
abstract class ScheduleDatabase : RoomDatabase() {
    abstract fun SchedulesDao(): SchedulesDao

    companion object {
        private var INSTANCE: ScheduleDatabase? = null

        fun getInstance(context: Context) : ScheduleDatabase {
            if (INSTANCE == null) {
                logE("새로운 db 인스턴스 생성")
                synchronized(ScheduleDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        ScheduleDatabase::class.java,
                        "Schedule.db")
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}