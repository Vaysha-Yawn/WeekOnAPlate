package week.on.a.plate.repository.tables.weekOrg.selectionInDay

import androidx.room.Embedded
import androidx.room.Relation
import week.on.a.plate.repository.tables.weekOrg.recipeInMenu.RecipeInMenu


data class SelectionAndRecipesInMenu(
    @Embedded val selectionInDay: SelectionInDay,
    @Relation(
         parentColumn = "selectionId",
         entityColumn = "selectionId"
    )
    val recipeInMenu: List<RecipeInMenu>
)