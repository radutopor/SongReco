package com.radutopor.songreco.features.details.impl

import com.radutopor.songreco.features.details.impl.repository.DetailsModel

internal sealed class DetailsAction {

    data class DetailsModelAvailable(val model: DetailsModel) : DetailsAction()

    object SimilarClicked : DetailsAction()

    object RetryClicked : DetailsAction()

    object BackClicked : DetailsAction()

    object UpwardRequestConsumed : DetailsAction()
}