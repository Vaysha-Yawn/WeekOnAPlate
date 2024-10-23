package week.on.a.plate.data.dataView.week


import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

data class DayView (
    val date:LocalDate,
    var selections:List<SelectionView>
){
    fun getDyInWeekFull(local: Locale):String{
        return date.dayOfWeek.getDisplayName(TextStyle.FULL, local)
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    }
}