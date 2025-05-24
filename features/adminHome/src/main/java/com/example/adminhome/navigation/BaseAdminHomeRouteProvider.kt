package com.example.adminhome.navigation

import com.example.adminnavigation.AdminHomeRouteProvider
import com.example.adminnavigation.Route

class BaseAdminHomeRouteProvider : AdminHomeRouteProvider {
    override fun route(): Route = AdminHomeRoute
}