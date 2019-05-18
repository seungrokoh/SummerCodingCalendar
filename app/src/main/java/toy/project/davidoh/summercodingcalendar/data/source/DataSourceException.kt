package toy.project.davidoh.summercodingcalendar.data.source

open class DataSourceException(message: String? = null) : Exception(message)

class LocalDataNotFoundException : DataSourceException("Data not found in local data source")