package week.on.a.plate.screens.cookPlanner.logic.stepMore.usecases

import javax.inject.Inject

class UseCaseWrapperCookPlannerCardActions @Inject constructor(
    val deleteCookPlannerGroupUseCase: DeleteCookPlannerGroupUseCase,
    val changeEndRecipeTimeUseCase: ChangeEndRecipeTimeUseCase,
    val changePortionsCountUseCase: ChangePortionsCountUseCase,
    val changeStartRecipeTimeUseCase: ChangeStartRecipeTimeUseCase,
)