package toy.project.davidoh.summercodingcalendar.data

import org.threeten.bp.LocalDate

data class Schedule(private var id: String,
                    private var title: String,
                    private var description: String,
                    private var date: LocalDate
)