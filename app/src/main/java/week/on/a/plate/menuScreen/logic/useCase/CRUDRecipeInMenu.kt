package week.on.a.plate.menuScreen.logic.useCase

import week.on.a.plate.core.data.recipe.IngredientInRecipeView
import week.on.a.plate.core.data.recipe.IngredientView
import week.on.a.plate.core.data.week.Position
import week.on.a.plate.menuScreen.logic.eventData.ActionDBData
import week.on.a.plate.repository.repositoriesForFeatures.menu.MenuRepository
import week.on.a.plate.repository.tables.weekOrg.position.positionDraft.PositionDraftRepository
import week.on.a.plate.repository.tables.weekOrg.position.positionIngredient.PositionIngredientRepository
import week.on.a.plate.repository.tables.weekOrg.position.positionNote.PositionNoteRepository
import week.on.a.plate.repository.tables.weekOrg.position.recipeInMenu.PositionRecipeRepository
import javax.inject.Inject

class CRUDRecipeInMenu @Inject constructor(
    val menuR: MenuRepository,
    private val noteRepository: PositionNoteRepository,
    private val ingredientRepository: PositionIngredientRepository,
    private val recipeRepository: PositionRecipeRepository,
    private val draftRepository: PositionDraftRepository,
) {
    suspend fun onEvent(event: ActionDBData, selected: List<Position>) {
        when (event) {
            is ActionDBData.AddEmptyDay -> {
                menuR.addEmptyDay(event.data)
            }

            is ActionDBData.AddIngredientDB -> {
                val ingredient = Position.PositionIngredientView(0, IngredientInRecipeView(
                    0, event.data.ingredientState.value!!, event.data.text.value, event.data.count.doubleValue
                ), event.data.selectionId)
                ingredientRepository.insert(ingredient, event.data.selectionId)
            }

            is ActionDBData.AddNoteDB -> {
                noteRepository.insert(
                    Position.PositionNoteView(0, event.data.text.value, event.data.selectionId),
                    event.data.selectionId
                )
            }

            is ActionDBData.AddRecipePositionInMenuDB -> {
                recipeRepository.insert(event.recipe, event.selId)
            }

            is ActionDBData.ChangePortionsCountDB -> {
                recipeRepository.update(
                    event.data.recipe.id,
                    event.data.recipe.recipe,
                    event.data.portionsCount.intValue,
                    event.data.recipe.selectionId
                )
            }

            is ActionDBData.Delete -> {
                when (event.position) {
                    is Position.PositionDraftView -> draftRepository.delete(event.position.idPos)
                    is Position.PositionIngredientView -> ingredientRepository.delete(event.position.idPos)
                    is Position.PositionNoteView -> noteRepository.delete(event.position.idPos)
                    is Position.PositionRecipeView -> recipeRepository.delete(event.position.idPos)
                }
            }

            ActionDBData.DeleteSelected -> {
                selected.forEach {
                    onEvent(ActionDBData.Delete(it), listOf())
                }
            }

            is ActionDBData.DoublePositionInMenuDB -> {
                val selId: Long = menuR.getSelIdOrCreate(event.dateToLocalDate, event.selection)
                when (event.position) {
                    is Position.PositionDraftView -> draftRepository.insert(event.position, selId)
                    is Position.PositionIngredientView -> ingredientRepository.insert(
                        event.position,
                        selId
                    )

                    is Position.PositionNoteView -> noteRepository.insert(event.position, selId)
                    is Position.PositionRecipeView -> recipeRepository.insert(event.position, selId)
                }
            }

            is ActionDBData.EditIngredientDB -> {
                val data = event.data
                ingredientRepository.update(
                    data.ingredient!!.idPos,
                    data.ingredientState.value!!.ingredientId,
                    data.ingredient.selectionId
                )
            }

            is ActionDBData.EditNoteDB -> {
                val note = event.data.note
                noteRepository.update(
                    note.idPos,
                    event.data.text.value, note.selectionId
                )
            }

            is ActionDBData.MovePositionInMenuDB -> {
                val selId: Long = menuR.getSelIdOrCreate(event.dateToLocalDate, event.selection)
                when (event.position) {
                    is Position.PositionDraftView -> {
                        onEvent(ActionDBData.Delete(event.position), listOf())
                        draftRepository.insert(event.position, selId)
                    }

                    is Position.PositionIngredientView -> {
                        onEvent(ActionDBData.Delete(event.position), listOf())
                        ingredientRepository.insert(event.position, selId)
                    }

                    is Position.PositionNoteView -> {
                        onEvent(ActionDBData.Delete(event.position), listOf())
                        noteRepository.insert(event.position, selId)
                    }

                    is Position.PositionRecipeView -> {
                        onEvent(ActionDBData.Delete(event.position), listOf())
                        recipeRepository.insert(event.position, selId)
                    }
                }
            }
        }
    }
}