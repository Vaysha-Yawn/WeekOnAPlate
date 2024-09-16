package week.on.a.plate.core.navigation.destiations

import kotlinx.serialization.Serializable
import week.on.a.plate.core.data.week.Position
import week.on.a.plate.core.tools.DateSerializer
import java.time.LocalDate

@Serializable
sealed class FullScreenDialogRoute{
    @Serializable
    data class AddPositionToMenuDialog(
        val position: Position.PositionRecipeView,
        @Serializable(with = DateSerializer::class)
        val date: LocalDate,
        val category: String,
    ):FullScreenDialogRoute()

    @Serializable
    data class MovePositionToMenuDialog(
        val position: Position
    ):FullScreenDialogRoute()

    @Serializable
    data class DoublePositionToMenuDialog(
        val position: Position
    ):FullScreenDialogRoute()

    @Serializable
    data object SpecifyDateDialog : FullScreenDialogRoute()
    //after return sell id long
    //eventAfter
}