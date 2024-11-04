package week.on.a.plate.screens.tutorial.logic

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import week.on.a.plate.screens.tutorial.event.TutorialEvent
import week.on.a.plate.screens.tutorial.getFromAssetUtils.tutorialMap
import week.on.a.plate.screens.tutorial.state.TutorialDestination
import week.on.a.plate.screens.tutorial.state.TutorialStateUI

//todo
@HiltViewModel
class TutorialViewModel : ViewModel() {

    lateinit var stateUI: TutorialStateUI

    fun onEvent(event: TutorialEvent) {
        when (event) {
            TutorialEvent.Done -> done()
            TutorialEvent.LastPage -> lastPage()
            TutorialEvent.NextPage -> nextPage()
            TutorialEvent.Skip -> done()
        }
    }

    private fun nextPage() {
        if (stateUI.listPages.size - 1 < stateUI.activePageInd.intValue) {
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
        stateUI = TutorialStateUI(dest, mutableIntStateOf(0), tutorialMap[dest]!!)
    }
}