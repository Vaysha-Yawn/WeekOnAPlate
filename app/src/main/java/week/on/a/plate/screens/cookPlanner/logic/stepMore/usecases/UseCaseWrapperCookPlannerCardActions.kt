package week.on.a.plate.screens.cookPlanner.logic.stepMore.usecases

class UseCaseWrapperCookPlannerCardActions (
    val deleteCookPlannerGroupUseCase: DeleteCookPlannerGroupUseCase,
    val changeEndRecipeTimeUseCase: ChangeEndRecipeTimeUseCase,
    val changePortionsCountUseCase: ChangePortionsCountUseCase,
    val changeStartRecipeTimeUseCase: ChangeStartRecipeTimeUseCase,
)