package toy.project.davidoh.summercodingcalendar

import android.app.Application
import kotlinx.coroutines.Dispatchers
import toy.project.davidoh.summercodingcalendar.util.Injection
import toy.project.davidoh.summercodingcalendar.util.launchSilent

class AppData : Application() {

    override fun onCreate() {
        super.onCreate()
        loadSchedulesAllDay()
    }

    private fun loadSchedulesAllDay() = launchSilent(Dispatchers.Main){
        Injection.provideTaskRepository(applicationContext).getSchedulesAllDay()
    }
}