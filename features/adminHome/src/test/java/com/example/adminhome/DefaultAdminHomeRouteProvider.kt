package com.example.adminhome

import com.example.adminhome.navigation.AdminHomeRoute
import com.example.adminnavigation.AdminHomeRouteProvider
import com.example.adminnavigation.Route

class DefaultAdminHomeRouteProvider : AdminHomeRouteProvider {
    override fun route(): Route = AdminHomeRoute
}