package week.on.a.plate.core.dialogs

sealed class DialogType{
    data object ChooseWeek: DialogType()
    data object ChangePortionsCount: DialogType()
    data object EditIngredientPosition: DialogType()
    data object EditNote: DialogType()
    data object CreateIngredientPosition: DialogType()
    data object CreateNote: DialogType()
    data object CreatePosition: DialogType()
    data object EditPosition: DialogType()
    data object SelectedToShopList: DialogType()
    data object RecipeToShopList: DialogType()
    data object EditRecipePosition: DialogType()
    data object DeleteAsk: DialogType()
    data object RegisterToShopList: DialogType()

    data object CreateIngredient: DialogType()
    data object CreateTag: DialogType()
    data object CreateCategoryIngredient: DialogType()
    data object FindCategoryIngredient: DialogType()
    data object FindIngredient: DialogType()
    data object FindIngredients: DialogType()
    data object FindTags: DialogType()
    data object SelectedFilters: DialogType()
    data object VoiceFiltersApply: DialogType()
    data object SpecifyDate: DialogType()
}