package week.on.a.plate.screens.additional.tutorial.state

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import week.on.a.plate.R

class TutorialStateUI() {
    val activePageInd: MutableIntState = mutableIntStateOf(0)
    val tutorialEnum = mutableStateOf(TutorialEnum.Menu)
}

data class TutorialPage(
    val title: String,
    val img: Int,
    val text: AnnotatedString,
    val inlineContent: Map<String, InlineTextContent> = mapOf()
)

enum class TutorialEnum(val pages: List<TutorialPage>) {
    Menu(
        listOf(
            TutorialPage(
                "Составление меню", R.drawable.menu_tutorial_1,
                buildAnnotatedString {
                    append("Чтобы добавить ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("позицию в меню")
                    }
                    append(" (например рецепт), нажмите на + рядом с приемом пищи.")
                }
            ),
            TutorialPage(
                "Составление меню", R.drawable.menu_tutorial_2,
                buildAnnotatedString {
                    append(
                        "В меню можно добавить не только рецепт, но и отдельные ингредиенты, заметки и наброски.\n" +
                                "Давайте добавим рецепт."
                    )
                }
            ),
            TutorialPage(
                "Составление меню", R.drawable.menu_tutorial_3,
                buildAnnotatedString {
                    append(
                        "В поиске рецептов найдите или создайте нужный рецепт и нажмите + рядом с ним.\n" +
                                "Готово!"
                    )
                }
            ),
        )),

}