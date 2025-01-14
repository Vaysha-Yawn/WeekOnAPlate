package week.on.a.plate.screens.filters.logic.crud.tagCategory

import javax.inject.Inject

class TagCategoryCRUD @Inject constructor(
    val createTagCategory: CreateTagCategory,
    val deleteTagCategory: DeleteTagCategory,
    val editTagCategory: EditTagCategory
)
