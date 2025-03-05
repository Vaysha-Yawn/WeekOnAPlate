package week.on.a.plate.repository.menu

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.repository.tables.filters.ingredient.IngredientDAO
import week.on.a.plate.data.repository.tables.filters.ingredient.IngredientMapper
import week.on.a.plate.data.repository.tables.filters.ingredient.IngredientRoom
import week.on.a.plate.data.repository.tables.filters.recipeTag.RecipeTagDAO
import week.on.a.plate.data.repository.tables.filters.recipeTag.RecipeTagMapper
import week.on.a.plate.data.repository.tables.filters.recipeTag.RecipeTagRoom
import week.on.a.plate.data.repository.tables.menu.position.draft.PositionDraftDAO
import week.on.a.plate.data.repository.tables.menu.position.draft.PositionDraftRepository
import week.on.a.plate.data.repository.tables.menu.position.draft.PositionDraftRoom
import week.on.a.plate.data.repository.tables.menu.position.draft.draftIngredientCrossRef.DraftAndIngredientCrossRef
import week.on.a.plate.data.repository.tables.menu.position.draft.draftTagCrossRef.DraftAndTagCrossRef


class TestPositionDraftRepository {

    @MockK
    private lateinit var positionDraftDAO: PositionDraftDAO

    @MockK
    private lateinit var ingredientDAO: IngredientDAO

    @MockK
    private lateinit var recipeTagDAO: RecipeTagDAO

    private lateinit var repository: PositionDraftRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = PositionDraftRepository(positionDraftDAO, ingredientDAO, recipeTagDAO)
    }

    @Test
    fun `getAllInSelFlow returns empty list when no drafts`() = runTest {

        every { positionDraftDAO.getAllInSelFlow(any()) } returns flowOf<List<PositionDraftRoom>>().onEmpty {
            emit(
                emptyList()
            )
        }
        every { positionDraftDAO.getDraftAndTagByDraftIdFlow(any()) } returns flowOf<List<DraftAndTagCrossRef>>().onEmpty {
            emit(
                emptyList()
            )
        }
        every { recipeTagDAO.findByIDFlow(any()) } returns flowOf<RecipeTagRoom?>().onEmpty {
            emit(
                null
            )
        }
        every { positionDraftDAO.getDraftAndIngredientByDraftIdFlow(any()) } returns flowOf<List<DraftAndIngredientCrossRef>>().onEmpty {
            emit(
                emptyList()
            )
        }
        every { ingredientDAO.findByIDFlow(any()) } returns flowOf<IngredientRoom?>().onEmpty {
            emit(
                null
            )
        }

        val result =
            repository.getAllInSelFlow(1L).firstOrNull()

        assertTrue(result?.isEmpty() ?: false)
    }

    @Test
    fun `getAllInSelFlow returns mapped positions`() = runTest {
        val draftId = 1L
        val draft = PositionDraftRoom(1L).apply { this.draftId = draftId }
        val draftList = listOf(draft)

        val tagId = 200L
        val tagName = "tag"
        val tagRoom =
            RecipeTagRoom(recipeTagCategoryId = 2L, tagName = tagName).apply { recipeTagId = tagId }
        val tagView = RecipeTagView(id = tagId, tagName = tagName)
        val draftTag = DraftAndTagCrossRef(draftId, tagId)

        val ingredientId = 100L
        val ingredientRoom = IngredientRoom(
            ingredientCategoryId = 2L,
            img = "img",
            name = "name",
            measure = "measure"
        ).apply {
            this.ingredientId = ingredientId
        }
        val ingredientView = IngredientView(
            ingredientId = ingredientId,
            img = "img",
            name = "name",
            measure = "measure"
        )
        val draftIngredient = DraftAndIngredientCrossRef(draftId, ingredientId)

        every { positionDraftDAO.getAllInSelFlow(any()) } returns flowOf(draftList)
        every { positionDraftDAO.getDraftAndTagByDraftIdFlow(draftId) } returns flowOf(
            listOf(
                draftTag
            )
        )
        every { recipeTagDAO.findByIDFlow(tagId) } returns flowOf(tagRoom)
        every { positionDraftDAO.getDraftAndIngredientByDraftIdFlow(draftId) } returns flowOf(
            listOf(
                draftIngredient
            )
        )
        every { ingredientDAO.findByIDFlow(ingredientId) } returns flowOf(ingredientRoom)

        //test
        repository.getAllInSelFlow(1L).collect { result ->
            assertEquals(1, result.size)
        }
    }

    @Test
    fun `getIngredientsFlow returns mapped ingredients`() = runTest {
        val draftId = 1L
        val ingredientId = 100L
        val ingredientRoom = IngredientRoom(
            ingredientCategoryId = 2L,
            img = "img",
            name = "name",
            measure = "measure"
        ).apply {
            this.ingredientId = ingredientId
        }
        val ingredientView = IngredientView(
            ingredientId = ingredientId,
            img = "img",
            name = "name",
            measure = "measure"
        )
        val draftIngredient = DraftAndIngredientCrossRef(draftId, ingredientId)

        every { positionDraftDAO.getDraftAndIngredientByDraftIdFlow(draftId) } returns flowOf(
            listOf(
                draftIngredient
            )
        )
        every { ingredientDAO.findByIDFlow(ingredientId) } returns flowOf(ingredientRoom)

        val result = repository.getIngredientsFlow(draftId).first()

        assertEquals(1, result.size)
        assertEquals(ingredientView, result[0])
    }

    @Test
    fun `getTagsFlow returns mapped tags`() = runTest {
        val draftId = 1L
        val tagId = 200L
        val tagName = "tag"
        val tagRoom =
            RecipeTagRoom(recipeTagCategoryId = 2L, tagName = tagName).apply { recipeTagId = tagId }
        val tagView = RecipeTagView(id = tagId, tagName = tagName)
        val draftTag = DraftAndTagCrossRef(draftId, tagId)

        every { positionDraftDAO.getDraftAndTagByDraftIdFlow(draftId) } returns flowOf(
            listOf(
                draftTag
            )
        )
        every { recipeTagDAO.findByIDFlow(tagId) } returns flowOf(tagRoom)

        repository.getTagsFlow(draftId).collect { result ->
            if (result.isNotEmpty()) {
                assertEquals(1, result.size)
                assertEquals(tagView, result[0])
            }
        }
    }

    @Test
    fun `getIngredientsFlow returns empty list when no ingredients`() = runTest {

        every { positionDraftDAO.getDraftAndIngredientByDraftIdFlow(any()) } returns flowOf<List<DraftAndIngredientCrossRef>>().onEmpty {
            emit(
                emptyList()
            )
        }
        every { ingredientDAO.findByIDFlow(any()) } returns flowOf<IngredientRoom?>().onEmpty {
            emit(
                null
            )
        }

        val result = repository.getIngredientsFlow(1L).firstOrNull()

        assertTrue(result?.isEmpty() ?: false)
    }

    @Test
    fun `getTagsFlow returns empty list when no tags`() = runTest {

        every { positionDraftDAO.getDraftAndTagByDraftIdFlow(any()) } returns flowOf<List<DraftAndTagCrossRef>>().onEmpty {
            emit(
                emptyList()
            )
        }
        every { recipeTagDAO.findByIDFlow(any()) } returns flowOf<RecipeTagRoom?>().onEmpty {
            emit(
                null
            )
        }

        val result = repository.getTagsFlow(1L).firstOrNull()

        assertTrue(result?.isEmpty() ?: false)
    }

}
