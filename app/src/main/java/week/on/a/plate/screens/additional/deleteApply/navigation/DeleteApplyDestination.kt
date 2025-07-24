package week.on.a.plate.screens.additional.deleteApply.navigation

import kotlinx.serialization.Serializable

@Serializable
data class DeleteApplyDestination(
    val title: String? = null,
    val message: String,
)