package week.on.a.plate.core.fullScereenDialog.categoriesSearch.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

class CategoriesSearchUIState(){
    val allNames: MutableState<List<String>> =  mutableStateOf(listOf())
    val resultSearch: MutableState<List<String>> =  mutableStateOf(listOf())
    val searchText: MutableState<String> = mutableStateOf("")
}


