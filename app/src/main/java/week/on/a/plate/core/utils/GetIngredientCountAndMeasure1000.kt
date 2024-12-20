package week.on.a.plate.core.utils

import android.content.Context
import week.on.a.plate.data.dataView.example.Measure

fun getIngredientCountAndMeasure1000(context:Context, count:Int, measureStd:String): Pair<String, String> {
    val measure = if (count >= 1000) {
        if (measureStd == context.getString(Measure.Grams.small) ) {
            context.getString(Measure.Grams.big)
        } else {
            context.getString(Measure.Milliliters.big)
        }
    } else if (count > 0) {
        measureStd
    }else{
        ""
    }

    val value = if (count >= 1000) {
        count.toDouble() / 1000
    } else if (count > 0) {
        count
    } else{
        ""
    }

    return Pair(value.toString(), measure)
}