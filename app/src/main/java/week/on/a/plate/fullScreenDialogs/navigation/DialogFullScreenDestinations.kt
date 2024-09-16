package week.on.a.plate.fullScreenDialogs.navigation

import kotlinx.serialization.Serializable
import week.on.a.plate.core.data.week.Position

@Serializable
sealed class FullScreenDialogRoute {
    @Serializable
    data class AddPositionToMenuDialog(
        val position: Position.PositionRecipeView,
        val dateFromEpochDay: Long,
        val category: String,
    ) : FullScreenDialogRoute()

    @Serializable
    data class MovePositionToMenuDialog(
        val position: Position
    ) : FullScreenDialogRoute()

    @Serializable
    data class DoublePositionToMenuDialog(
        val position: Position
    ) : FullScreenDialogRoute()

    @Serializable
    data object SpecifyDateDialog : FullScreenDialogRoute()
}