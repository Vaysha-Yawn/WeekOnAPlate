package week.on.a.plate.menuScreen.view.dialogs.dialogFullScreen

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import java.util.Locale

fun Long.dateToString(): String {
    val dates = Date(this)
    val formattedDate = SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault()).format(dates)
    return formattedDate
}

fun Long.dateToLocalDate(): LocalDate {
    val dates = Date(this)
    val localDate = dates.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    return localDate
}