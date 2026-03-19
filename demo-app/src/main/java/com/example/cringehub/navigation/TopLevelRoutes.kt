package com.example.cringehub.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.cringehub.R

data class TopLevelRoute<T : Any>(val name: String, val route: T, val icon: ImageVector)

class Icons {
    companion object {
        val Hub: Int = R.drawable.hub_icon
        val SeasonPass: Int = R.drawable.season_pass_icon
        val Store: Int = R.drawable.store_icon
    }
}
