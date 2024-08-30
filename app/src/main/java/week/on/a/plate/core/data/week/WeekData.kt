package week.on.a.plate.core.data.week

data class WeekData(
    val id:Long,
    val selection: SelectionInDayData,
    val days: MutableList<DayData>
){
    fun findDay(dayDate:Int):Pair<Int, DayData>?{
        for ((ind, day) in days.withIndex()){
            if (day.date == dayDate){
                return Pair(ind, day)
            }
        }
        return null
    }
}