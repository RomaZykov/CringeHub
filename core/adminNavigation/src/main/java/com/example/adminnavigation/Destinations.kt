package com.example.adminnavigation

interface AdminAuthRouteProvider {
    fun route(): Route
}

interface AdminHomeRouteProvider {
    fun route(): Route
}

interface GuideCreationRouteProvider {
    fun route(): Route
}

interface DraftRouteProvider {
    fun route(id: String): Route
}