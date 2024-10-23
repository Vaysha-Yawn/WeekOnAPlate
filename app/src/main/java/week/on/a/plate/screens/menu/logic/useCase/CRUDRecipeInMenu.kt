package week.on.a.plate.screens.menu.logic.useCase


import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.repository.tables.menu.position.draft.PositionDraftRepository
import week.on.a.plate.data.repository.tables.menu.position.note.PositionNoteRepository
import week.on.a.plate.data.repository.tables.menu.position.positionIngredient.PositionIngredientRepository
import week.on.a.plate.data.repository.tables.menu.position.positionRecipe.PositionRecipeRepository
import week.on.a.plate.data.repository.tables.menu.selection.WeekRepository
import week.on.a.plate.screens.menu.event.ActionWeekMenuDB
import javax.inject.Inject

class CRUDRecipeInMenu @Inject constructor(
    val menuR: WeekRepository,
    private val noteRepository: PositionNoteRepository,
    private val positionIngredientRepository: PositionIngredientRepository,
    private val recipeRepository: PositionRecipeRepository,
    private val draftRepository: PositionDraftRepository,
) {
    suspend fun onEvent(event: ActionWeekMenuDB) {
        when (event) {
            is ActionWeekMenuDB.AddIngredientPositionDB -> {
                val ingredient = event.updatedPosition
                positionIngredientRepository.insert(ingredient, event.selId)
            }

            is ActionWeekMenuDB.AddNoteDB -> {
                noteRepository.insert(
                    Position.PositionNoteView(0, event.text, event.selId),
                    event.selId
                )
            }

            is ActionWeekMenuDB.AddRecipePositionInMenuDB -> {
                recipeRepository.insert(event.recipe, event.selId)
            }

            is ActionWeekMenuDB.ChangePortionsCountDB -> {
                recipeRepository.update(
                    event.recipe.id,
                    event.recipe.recipe,
                    event.count,
                    event.recipe.selectionId
                )
            }

            is ActionWeekMenuDB.Delete -> {
                when (event.position) {
                    is Position.PositionDraftView -> draftRepository.delete(event.position.idPos)
                    is Position.PositionIngredientView -> positionIngredientRepository.delete(event.position.idPos)
                    is Position.PositionNoteView -> noteRepository.delete(event.position.idPos)
                    is Position.PositionRecipeView -> recipeRepository.delete(event.position.idPos)
                }
            }

            is ActionWeekMenuDB.DoublePositionInMenuDB -> {
                val selId: Long = event.selId
                when (event.position) {
                    is Position.PositionDraftView -> draftRepository.insert(event.position, selId)
                    is Position.PositionIngredientView -> positionIngredientRepository.insert(
                        event.position,
                        selId
                    )

                    is Position.PositionNoteView -> noteRepository.insert(event.position, selId)
                    is Position.PositionRecipeView -> recipeRepository.insert(event.position, selId)
                }
            }

            is ActionWeekMenuDB.EditIngredientPositionDB -> {
                val data = event.updatedPosition
                positionIngredientRepository.update(
                    data.id,
                    data.ingredient.id,
                    data.ingredient.ingredientView.ingredientId,
                    data.selectionId,
                    data.ingredient.description,
                    data.ingredient.count.toDouble(),
                )
            }

            is ActionWeekMenuDB.EditNoteDB -> {
                val note = event.data
                noteRepository.update(
                    note.idPos,
                    note.note, note.selectionId
                )
            }

            is ActionWeekMenuDB.MovePositionInMenuDB -> {
                val selId: Long = event.selId
                when (event.position) {
                    is Position.PositionDraftView -> {
                        onEvent(ActionWeekMenuDB.Delete(event.position))
                        draftRepository.insert(event.position, selId)
                    }

                    is Position.PositionIngredientView -> {
                        onEvent(ActionWeekMenuDB.Delete(event.position))
                        positionIngredientRepository.insert(event.position, selId)
                    }

                    is Position.PositionNoteView -> {
                        onEvent(ActionWeekMenuDB.Delete(event.position))
                        noteRepository.insert(event.position, selId)
                    }

                    is Position.PositionRecipeView -> {
                        onEvent(ActionWeekMenuDB.Delete(event.position))
                        recipeRepository.insert(event.position, selId)
                    }
                }
            }

            is ActionWeekMenuDB.AddDraft -> {
                draftRepository.insert(event.draft, event.draft.selectionId)
            }

            is ActionWeekMenuDB.EditDraft -> {
                draftRepository.update(event.oldDraft, event.filters)
            }

            is ActionWeekMenuDB.DeleteSelection -> {
                menuR.deleteSelection(event.sel)
            }

            is ActionWeekMenuDB.EditSelection -> {
                menuR.editSelection(event.sel, event.newName, event.time)
            }

            is ActionWeekMenuDB.CreateSelection -> {
                menuR.createSelection(
                    event.date, event.newName, event.locale,
                    event.isForWeek, event.time
                )
            }
        }
    }
}