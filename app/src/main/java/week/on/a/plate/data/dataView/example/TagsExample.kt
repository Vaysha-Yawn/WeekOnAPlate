package week.on.a.plate.data.dataView.example

import android.content.Context
import week.on.a.plate.R
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.dataView.recipe.TagCategoryView
import week.on.a.plate.data.repository.tables.filters.recipeTagCategory.startCategoryName

fun getTags(context:Context): List<TagCategoryView> {
    return listOf(
        TagCategoryView(
            1,
            context.getString(R.string.tag_category_by_meal),
            listOf(
                RecipeTagView(1, context.getString(R.string.tag_breakfast)),
                RecipeTagView(2, context.getString(R.string.tag_lunch)),
                RecipeTagView(3, context.getString(R.string.tag_dinner))
            )
        ),
        TagCategoryView(
            2,
            context.getString(R.string.tag_category_by_cuisines_world),
            listOf(
                RecipeTagView(4, context.getString(R.string.tag_russian)),
                RecipeTagView(5, context.getString(R.string.tag_american)),
                RecipeTagView(6, context.getString(R.string.tag_asian))
            )
        ),
        TagCategoryView(
            3,
            context.getString(R.string.tag_category_by_advantages),
            listOf(
                RecipeTagView(7, context.getString(R.string.tag_light)),
                RecipeTagView(8, context.getString(R.string.tag_fast)),
                RecipeTagView(9, context.getString(R.string.tag_inexpensive))
            )
        ),
        TagCategoryView(
            4,
            context.getString(R.string.tag_category_by_cooking_method),
            listOf(
                RecipeTagView(10, context.getString(R.string.tag_in_oven)),
                RecipeTagView(11, context.getString(R.string.tag_griddle)),
                RecipeTagView(12, context.getString(R.string.tag_in_multicooker)),
                RecipeTagView(13, context.getString(R.string.tag_for_a_couple)),
            )
        ),
        TagCategoryView(
            5,
            context.getString(R.string.tag_category_types_dishes),
            listOf(
                RecipeTagView(14, context.getString(R.string.tag_first_course)),
                RecipeTagView(15, context.getString(R.string.tag_second_course)),
                RecipeTagView(18, context.getString(R.string.tag_drink)),
                RecipeTagView(19, context.getString(R.string.tag_food_preparations)),
                RecipeTagView(20, context.getString(R.string.tag_bakery)),
                RecipeTagView(21, context.getString(R.string.tag_sauce)),
            )
        ),
    )
}