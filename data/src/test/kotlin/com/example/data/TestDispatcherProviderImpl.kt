package com.example.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher

/**
 * Created by Nitin Dasari on 10/6/22.
 */
@OptIn(ExperimentalCoroutinesApi::class)
object TestDispatcherProviderImpl : DispatcherProvider {
    private val scheduler = TestCoroutineScheduler()
    private val testCoroutineDispatcher = UnconfinedTestDispatcher(scheduler)
    override val default: CoroutineDispatcher
        get() = testCoroutineDispatcher
    override val io: CoroutineDispatcher
        get() = testCoroutineDispatcher
    override val main: CoroutineDispatcher
        get() = testCoroutineDispatcher
}
