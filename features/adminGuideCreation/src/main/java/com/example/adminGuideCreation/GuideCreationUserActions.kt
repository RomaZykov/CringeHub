package com.example.adminGuideCreation

interface GuideCreationUserActions {

    fun popBackStack()

    fun onPublishClicked()

    object ForPreview : GuideCreationUserActions {
        override fun onPublishClicked() {}
        override fun popBackStack() {}
    }
}