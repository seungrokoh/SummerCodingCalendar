package toy.project.davidoh.summercodingcalendar.ui.calendar.weekly.presenter

import com.prolificinteractive.materialcalendarview.CalendarDay

interface WeeklyContractor {
    interface View {

    }

    interface Presenter {
        fun loadScheduleOnDay(date: CalendarDay)
    }
}