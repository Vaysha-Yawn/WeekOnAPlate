package week.on.a.plate.search.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class SearchRoute {

    @Serializable
    data object SearchDestination : SearchRoute()

    @Serializable
    class SearchWithSelId(val selId: Long) : SearchRoute()
}