package com.example.feature.onboarding

class OnBoardingItems(
    val image: Int,
    val title: Int,
    val desc: Int
) {
    companion object {
        fun getData(): List<OnBoardingItems> {
            return listOf(
                OnBoardingItems(
                    R.drawable.onboarding_1,
                    R.string.onboarding_title_1,
                    R.string.onboarding_desc_1
                ),
                OnBoardingItems(
                    R.drawable.onboarding_2,
                    R.string.onboarding_title_2,
                    R.string.onboarding_desc_2
                ),
                OnBoardingItems(
                    R.drawable.onboarding_3,
                    R.string.onboarding_title_3,
                    R.string.onboarding_desc_3
                ),
                OnBoardingItems(
                    R.drawable.onboarding_4,
                    R.string.onboarding_title_4,
                    R.string.onboarding_desc_4
                )
            )
        }
    }
}