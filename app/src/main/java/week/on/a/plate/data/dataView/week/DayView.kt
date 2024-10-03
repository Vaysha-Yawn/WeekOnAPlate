package week.on.a.plate.data.dataView.week


import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

data class DayView (
    val date:LocalDate,
    val selections:List<SelectionView>
){
    fun getDyInWeekShort(local: Locale):String{
        return date.dayOfWeek.getDisplayName(TextStyle.SHORT, local)
    }
    fun getDyInWeekFull(local: Locale):String{
        return date.dayOfWeek.getDisplayName(TextStyle.FULL, local).capitalize()
    }
}