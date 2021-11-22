package com.ivy.wallet.compose.scenario

import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.ivy.wallet.compose.IvyComposeTest
import com.ivy.wallet.compose.helpers.*
import com.ivy.wallet.ui.theme.Blue
import com.ivy.wallet.ui.theme.Blue2
import com.ivy.wallet.ui.theme.Ivy
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test

@HiltAndroidTest
class CategoriesTest : IvyComposeTest() {
    private val onboardingFlow = OnboardingFlow(composeTestRule)
    private val homeMoreMenu = HomeMoreMenu(composeTestRule)
    private val categoryModal = CategoryModal(composeTestRule)
    private val categoryScreen = CategoryScreen(composeTestRule)
    private val itemStatisticScreen = ItemStatisticScreen(composeTestRule)
    private val deleteConfirmationModal = DeleteConfirmationModal(composeTestRule)

    @Test
    fun CreateCategory() {
        onboardingFlow.onboardWith1AccountAnd1Category()

        homeMoreMenu.clickOpenCloseArrow()

        composeTestRule.onNodeWithText("Categories")
            .performClick()

        composeTestRule.onNodeWithText("Add category")
            .performClick()

        categoryModal.apply {
            enterTitle("Fun")
            chooseIconFlow.chooseIcon("game")
            colorPicker.chooseColor(Blue)

            clickAdd()
        }

        categoryScreen.assertCategory("Fun")
    }

    @Test
    fun AddSeveralCategories() {
        onboardingFlow.onboardWith1AccountAnd1Category()
        homeMoreMenu.clickOpenCloseArrow()
        composeTestRule.onNodeWithText("Categories")
            .performClick()

        categoryScreen.addCategory(
            categoryName = "Entertainment"
        )
        categoryScreen.addCategory(
            categoryName = "Bills",
            color = Blue2
        )
        categoryScreen.addCategory(
            categoryName = "Ivy",
            icon = "star"
        )
    }

    @Test
    fun EditCategory() {
        onboardingFlow.onboardWith1AccountAnd1Category()
        homeMoreMenu.clickOpenCloseArrow()
        composeTestRule.onNodeWithText("Categories")
            .performClick()

        categoryScreen.clickCategory(
            categoryName = "Food & Drinks"
        )

        itemStatisticScreen.clickEdit()
        categoryModal.apply {
            enterTitle(
                title = "Eating"
            )
            chooseIconFlow.chooseIcon(
                icon = "restaurant"
            )
            colorPicker.chooseColor(Ivy)

            clickSave()
        }
        itemStatisticScreen.clickClose()

        categoryScreen.assertCategory(
            categoryName = "Eating"
        )
    }

    @Test
    fun DeleteCategory() {
        onboardingFlow.onboardWith1AccountAnd1Category()
        homeMoreMenu.clickOpenCloseArrow()
        composeTestRule.onNodeWithText("Categories")
            .performClick()

        categoryScreen.clickCategory(
            categoryName = "Food & Drinks"
        )

        itemStatisticScreen.clickDelete()
        deleteConfirmationModal.confirmDelete()

        categoryScreen.assertCategoryNotExists(
            categoryName = "Food & Drinks"
        )
    }
}