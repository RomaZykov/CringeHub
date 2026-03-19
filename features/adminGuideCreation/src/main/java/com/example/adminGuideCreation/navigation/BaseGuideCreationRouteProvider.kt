package com.example.adminGuideCreation.navigation

import com.example.adminnavigation.GuideCreationRouteProvider
import com.example.adminnavigation.Route

class BaseGuideCreationRouteProvider : GuideCreationRouteProvider {
    override fun route(): Route = GuideCreationRoute()
}