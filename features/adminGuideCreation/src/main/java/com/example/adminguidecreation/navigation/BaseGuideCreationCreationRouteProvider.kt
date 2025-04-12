package com.example.adminguidecreation.navigation

import com.example.adminnavigation.GuideCreationRouteProvider
import com.example.adminnavigation.Route

class BaseGuideCreationCreationRouteProvider : GuideCreationRouteProvider {
    override fun route(): Route = GuideCreationRoute
}