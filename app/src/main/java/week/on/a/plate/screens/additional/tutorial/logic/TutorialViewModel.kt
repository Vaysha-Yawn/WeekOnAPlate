package week.on.a.plate.screens.additional.tutorial.logic

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.additional.tutorial.event.TutorialEvent
import week.on.a.plate.screens.additional.tutorial.state.TutorialDestination
import week.on.a.plate.screens.additional.tutorial.state.TutorialPage
import week.on.a.plate.screens.additional.tutorial.state.TutorialStateUI
import javax.inject.Inject

//todo
@HiltViewModel
class TutorialViewModel @Inject constructor() : ViewModel() {

    var targetDestination: TutorialDestination = TutorialDestination.Menu
    private val listPages: List<TutorialPage> = listOf()

    val stateUI = TutorialStateUI()

    fun onEvent(event: TutorialEvent) {
        when (event) {
            TutorialEvent.Done -> done()
            TutorialEvent.LastPage -> lastPage()
            TutorialEvent.NextPage -> nextPage()
            TutorialEvent.Skip -> done()
            is TutorialEvent.SelectPage -> TODO()
        }
    }

    private fun nextPage() {
        if (listPages.size - 1 < stateUI.activePageInd.intValue) {
            stateUI.activePageInd.intValue += 1
        }
    }

    private fun lastPage() {
        if (stateUI.activePageInd.intValue > 1) {
            stateUI.activePageInd.intValue -= 1
        }
    }

    fun done() {
        // save done in preference
        // navigate to target
        // delete tutorial from backStack
    }

    fun launch(dest: TutorialDestination) {
        //dest, mutableIntStateOf(0), tutorialMap[dest]!!
    }
}