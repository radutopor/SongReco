package com.radutopor.songreco.features.trending.impl

import com.radutopor.songreco.features.trending.impl.repository.TrendingModel

internal sealed class TrendingAction {

    data class TrendingModelAvailable(val model: TrendingModel) : TrendingAction()

    object RetryClicked : TrendingAction()
}
