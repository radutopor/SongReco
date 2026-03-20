package com.radutopor.songreco.features.trending.impl

import com.radutopor.songreco.core.arch.stringvalue.StringValue
import com.radutopor.songreco.features.trending.impl.repository.TrendingModel
import com.radutopor.songreco.features.trending.impl.testdoubles.repository.trendingModelErrorApiStub
import com.radutopor.songreco.features.trending.impl.testdoubles.repository.trendingModelLoadedStub
import songreco.core.designsystem.generated.resources.msg_err_generic
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import songreco.core.designsystem.generated.resources.Res as CoreRes

internal class TrendingReducerTest {

    private var actionSent: TrendingAction? = null

    private val reducer = TrendingReducer(
        actionSink = { actionSent = it },
    )

    @Test
    fun `WHEN initState retrieved THEN state is Loading`() {
        val initState = reducer.initState
        assertIs<TrendingState.Loading>(initState)
    }

    @Test
    fun `GIVEN action TrendingModelAvailable AND TrendingModel Loaded WHEN reduce THEN state is Loaded mapped from TrendingModel`() {
        val trendingModelLoaded = trendingModelLoadedStub.copy(
            items = listOf("item1", "item2")
        )
        val action = TrendingAction.TrendingModelAvailable(trendingModelLoaded)
        val oldState = TrendingState.Loading
        val newState = reducer.run { oldState.reduce(action) }

        assertIs<TrendingState.Loaded>(newState)
        assertEquals(trendingModelLoaded.items, newState.items)
    }

    @Test
    fun `GIVEN action TrendingModelAvailable AND TrendingModel Error Api WHEN reduce THEN state is Error AND message is from api`() {
        val trendingModelError = trendingModelErrorApiStub.copy(
            message = "api error message"
        )
        val action = TrendingAction.TrendingModelAvailable(trendingModelError)
        val oldState = TrendingState.Loading
        val newState = reducer.run { oldState.reduce(action) }

        assertIs<TrendingState.Error>(newState)
        assertIs<StringValue.Str>(newState.message)
        assertEquals(trendingModelError.message, newState.message.value)
        assertNull(newState.onRetryClick)
    }

    @Test
    fun `GIVEN action TrendingModelAvailable AND TrendingModel Error Generic WHEN reduce THEN state is Error AND message is generic`() {
        val trendingModelError = TrendingModel.Error.Generic
        val action = TrendingAction.TrendingModelAvailable(trendingModelError)
        val oldState = TrendingState.Loading
        val newState = reducer.run { oldState.reduce(action) }

        assertIs<TrendingState.Error>(newState)
        assertIs<StringValue.Res>(newState.message)
        assertEquals(CoreRes.string.msg_err_generic, newState.message.value)
        assertNotNull(newState.onRetryClick)
    }

    @Test
    fun `GIVEN Error with retry WHEN onRetryClick invoked THEN action RetryClicked is sent`() {
        val action = TrendingAction.TrendingModelAvailable(TrendingModel.Error.Generic)
        val oldState = TrendingState.Loading
        val newState = reducer.run { oldState.reduce(action) }

        (newState as TrendingState.Error).onRetryClick?.invoke()
        assertEquals(TrendingAction.RetryClicked, actionSent)
    }

    @Test
    fun `GIVEN action RetryClicked WHEN reduce THEN state is Loading`() {
        val action = TrendingAction.RetryClicked
        val oldState = TrendingState.Error(StringValue.Str(""), null)
        val newState = reducer.run { oldState.reduce(action) }

        assertEquals(TrendingState.Loading, newState)
    }
}
