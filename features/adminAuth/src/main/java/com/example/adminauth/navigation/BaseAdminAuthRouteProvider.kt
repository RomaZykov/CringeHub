package com.example.adminauth.navigation

import com.example.adminnavigation.AdminAuthRouteProvider
import com.example.adminnavigation.Route

class BaseAdminAuthRouteProvider : AdminAuthRouteProvider {
    override fun route(): Route = AdminAuthRoute
}