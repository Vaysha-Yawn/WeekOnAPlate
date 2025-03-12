package week.on.a.plate.screens.base.menu.domain.dbusecase

import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.repository.room.menu.position.positionIngredient.PositionIngredientRepository
import javax.inject.Inject

class AddIngredientPositionToDBUseCase @Inject constructor(
    private val positionIngredientRepository: PositionIngredientRepository
) {
    suspend operator fun invoke(
        updatedPosition: Position.PositionIngredientView,
        selId: Long,
    ) {
        positionIngredientRepository.insert(updatedPosition, selId)
    }
}

class UpdateIngredientPositionInDBUseCase @Inject constructor(
    private val positionIngredientRepository: PositionIngredientRepository
) {
    suspend operator fun invoke(data: Position.PositionIngredientView) {
        positionIngredientRepository.update(
            data.id,
            data.ingredient.id,
            data.ingredient.ingredientView.ingredientId,
            data.selectionId,
            data.ingredient.description,
            data.ingredient.count.toDouble(),
        )
    }
}

class DeleteIngredientPositionInDBUseCase @Inject constructor(
    private val positionIngredientRepository: PositionIngredientRepository
) {
    suspend operator fun invoke(position: Position.PositionIngredientView) {
        positionIngredientRepository.delete(position.idPos)
    }
}