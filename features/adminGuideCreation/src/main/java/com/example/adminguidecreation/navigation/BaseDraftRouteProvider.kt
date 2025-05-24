package com.example.adminguidecreation.navigation

import com.example.adminnavigation.DraftRouteProvider
import com.example.adminnavigation.Route

class BaseDraftRouteProvider : DraftRouteProvider {
    override fun route(id: String): Route = GuideCreationRoute(id)
}