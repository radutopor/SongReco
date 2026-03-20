package com.radutopor.songreco.features.list.impl

import com.radutopor.songreco.features.list.impl.testdoubles.listArgsSimilarStub
import com.radutopor.songreco.features.list.impl.testdoubles.repository.ListRepositoryFake
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.TestScope
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertNotNull

internal class ListLauncherTest {

    private val repository = ListRepositoryFake()
    private val featureScope = TestScope()

    private val launcher = ListLauncher(
        repository = repository,
        featureScope = featureScope,
        args = listArgsSimilarStub,
        actionSink = {},
    )

    @Test
    fun `WHEN launcher init THEN repository getListModel is called`() {
        featureScope.testScheduler.advanceUntilIdle()
        assertNotNull(repository.getListModelArg)
    }

    @Test
    fun `GIVEN action RetryClicked WHEN launchSideEffect called THEN repository getListModel is called`() {
        launcher.launchSideEffect(ListAction.RetryClicked)
        featureScope.testScheduler.advanceUntilIdle()
        assertNotNull(repository.getListModelArg)
    }

    @AfterTest
    fun tearDown() {
        featureScope.cancel()
    }
}