package week.on.a.plate.screens.menu.logic.useCase

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.dialogs.addPositionChoose.event.AddPositionEvent
import week.on.a.plate.dialogs.addPositionChoose.logic.AddPositionViewModel
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.menu.event.MenuEvent
import week.on.a.plate.screens.menu.logic.useCase.crudPositions.draft.CreateDraft
import week.on.a.plate.screens.menu.logic.useCase.crudPositions.ingredient.CreateIngredient
import week.on.a.plate.screens.menu.logic.useCase.crudPositions.note.CreateNote
import week.on.a.plate.screens.menu.logic.useCase.crudPositions.recipe.CreateRecipe
import javax.inject.Inject

class AddPositionUseCase @Inject constructor(
    private val createNote: CreateNote,
    private val createRecipe: CreateRecipe,
    private val createDraft: CreateDraft,
    private val createIngredient: CreateIngredient,
) {
    suspend operator fun invoke(
        selId: Long, context: Context,
        mainViewModel: MainViewModel, viewModelScope:CoroutineScope, onEvent: (MenuEvent)->Unit, updateWeek:()->Unit,
    ) {
        AddPositionViewModel.launch(mainViewModel) { event ->
            viewModelScope.launch {
            when (event) {
                AddPositionEvent.AddDraft -> createDraft(selId, mainViewModel, onEvent)

                AddPositionEvent.AddIngredient -> createIngredient(selId, mainViewModel, onEvent)

                AddPositionEvent.AddNote -> createNote(selId, mainViewModel, onEvent)

                AddPositionEvent.AddRecipe ->
                    createRecipe(
                        selId,
                        context,
                        viewModelScope,
                        mainViewModel,
                        updateWeek,
                        onEvent
                    )

                AddPositionEvent.Close -> {}
            }
        }
        }
    }
}