package week.on.a.plate.core.utils

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun Long.dateToString(): String {
    val dates = Date(this)
    val formattedDate = SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault()).format(dates)
    return formattedDate
}

fun LocalDate.dateToString(): String {
    val formattedDate = this.format(DateTimeFormatter.ofPattern("dd MMMM, yyyy"))
    return formattedDate
}

fun Long.dateToLocalDate(): LocalDate {
    val dates = Date(this)
    val localDate = dates.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    return localDate
}

fun Int.timeToString(): String {
    val sec = this
    val min = (sec / 60)
    val hour = min / 60
    val minView = min-hour*60
    val hourTxt = if (hour>0){
        "$hour ч "
    }else ""
    val minuteTxt = if (minView>0){
        "$minView мин"
    }else ""
    return hourTxt+minuteTxt
}