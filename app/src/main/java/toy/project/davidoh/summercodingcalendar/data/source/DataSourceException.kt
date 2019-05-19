package toy.project.davidoh.summercodingcalendar.data.source

open class DataSourceException(message: String? = null) : Exception(message)

class LocalDataNotFoundException : DataSourceException("일정이 존재하지 않습니다.")