package com.radutopor.songreco.features.root.impl

internal sealed class RootAction {

    data class NewContentAvailable(val content: RootState.Content) : RootAction()

    object SearchTabClicked : RootAction()

    object TrendingTabClicked : RootAction()

    object BackNavIntercepted : RootAction()
}