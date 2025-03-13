package com.example.adminhome.navigation

import com.example.adminnavigation.AdminHomeRouteProvider
import com.example.adminnavigation.Route

class BaseAdminAuthRouteProvider : AdminHomeRouteProvider {
    override fun route(): Route = AdminHomeRoute
}