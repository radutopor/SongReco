package com.radutopor.songreco.features.trending.impl

import com.radutopor.songreco.features.trending.impl.testdoubles.repository.TrendingRepositoryFake
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.TestScope
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertTrue

internal class TrendingLauncherTest {

    private val repository = TrendingRepositoryFake()
    private val featureScope = TestScope()

    private val launcher = TrendingLauncher(
        repository = repository,
        featureScope = featureScope,
        actionSink = {},
    )

    @Test
    fun `WHEN launcher init THEN repository getTrendingModel is called`() {
        featureScope.testScheduler.advanceUntilIdle()
        assertTrue(repository.getTrendingModelCalled)
    }

    @Test
    fun `GIVEN action RetryClicked WHEN launchSideEffect called THEN repository getTrendingModel is called`() {
        launcher.launchSideEffect(TrendingAction.RetryClicked)
        featureScope.testScheduler.advanceUntilIdle()
        assertTrue(repository.getTrendingModelCalled)
    }

    @AfterTest
    fun tearDown() {
        featureScope.cancel()
    }
}
