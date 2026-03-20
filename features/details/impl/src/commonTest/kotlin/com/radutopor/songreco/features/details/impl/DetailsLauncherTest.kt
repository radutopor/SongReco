package com.radutopor.songreco.features.details.impl

import com.radutopor.songreco.features.details.impl.testdoubles.detailsArgsStub
import com.radutopor.songreco.features.details.impl.testdoubles.repository.DetailsRepositoryFake
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.TestScope
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertNotNull

internal class DetailsLauncherTest {

    private val repository = DetailsRepositoryFake()
    private val featureScope = TestScope()

    private val launcher = DetailsLauncher(
        repository = repository,
        featureScope = featureScope,
        args = detailsArgsStub,
        actionSink = {},
    )

    @Test
    fun `WHEN launcher init THEN repository getDetailsModel is called`() {
        featureScope.testScheduler.advanceUntilIdle()
        assertNotNull(repository.getDetailsModelArg)
    }

    @Test
    fun `GIVEN action RetryClicked WHEN launchSideEffect called THEN repository getDetailsModel is called`() {
        launcher.launchSideEffect(DetailsAction.RetryClicked)
        featureScope.testScheduler.advanceUntilIdle()
        assertNotNull(repository.getDetailsModelArg)
    }

    @AfterTest
    fun tearDown() {
        featureScope.cancel()
    }
}
