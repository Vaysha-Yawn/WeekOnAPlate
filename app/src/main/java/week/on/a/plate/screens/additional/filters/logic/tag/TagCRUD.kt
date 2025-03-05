package week.on.a.plate.screens.additional.filters.logic.tag

import javax.inject.Inject

class TagCRUD @Inject constructor(
    val createTag: CreateTag,
    val deleteTag: DeleteTag,
    val editTag: EditTag
)
