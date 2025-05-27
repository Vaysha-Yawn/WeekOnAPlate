package week.on.a.plate.screens.additional.ppAndTermsOfUse.logic

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.core.Event
import week.on.a.plate.screens.additional.ppAndTermsOfUse.event.DocumentsWebEvent
import week.on.a.plate.screens.additional.ppAndTermsOfUse.state.DocumentsWebUIState
import javax.inject.Inject


@HiltViewModel
class DocumentsWebViewModel @Inject constructor() : ViewModel() {

    val state = DocumentsWebUIState()
    val mainEvent = mutableStateOf<MainEvent?>(null)

    fun onEvent(event: Event) {
        when (event) {
            is DocumentsWebEvent -> onEvent(event)
            is MainEvent -> mainEvent.value = event
        }
    }

    fun onEvent(event: DocumentsWebEvent) {
        when (event) {
            DocumentsWebEvent.Back -> mainEvent.value = MainEvent.NavigateBack
        }
    }

    fun launch(isForPP:Boolean){
        if (isForPP) {
            //state.url.value = "https://doc-hosting.flycricket.io/week-on-a-plate-privacy-policy/e45d7ef4-b7c6-4644-af13-9fe7a30ce8d6/privacy"
            state.url.value = "https://docs.google.com/document/d/1tC-KGOy9NMy2xWv8P4ka3qGmvtSrC-LlmYbCTd4bjmY/edit?usp=sharing"
        }else{
            state.url.value = "https://doc-hosting.flycricket.io/week-on-a-plate-terms-of-use/56f5b45b-7a67-43d8-a06c-6822c10a1d34/terms"
        }
        state.isForPP.value = isForPP
    }

}