package week.on.a.plate.core.utils

import week.on.a.plate.data.dataView.example.Measure

fun getIngredientCountAndMeasure1000(count:Int, measureStd:String): Pair<String, String> {
    val measure = if (count >= 1000) {
        if (measureStd == Measure.Grams.small) {
            Measure.Grams.big
        } else {
            Measure.Milliliters.big
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