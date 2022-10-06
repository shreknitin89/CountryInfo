package com.example.countryinfo.presentation

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


/**
 * Created by Nitin Dasari on 10/6/22.
 */
/**
 * Executes the flow and performs action when the Activity life cycle event is at least started
 * This is the safest way to use flows in the main thread because we are not leaking anything and be aware of the lifecycle events at the same time
 * Execution is similar to observing a `LiveData` but eliminates the need to use it
 * @param flow - flow that needs to be executed
 * @param action - execution block
 */
fun <T> AppCompatActivity.safeCollectFlow(flow: Flow<T>, action: suspend (T) -> Unit) =
    lifecycleScope.launchWhenStarted {
        flow.onEach(action)
            .launchIn(this)
    }

/**
 * Executes the flow and performs action when the Fragment life cycle event is at least started.
 * This is the safest way to use flows in the main thread because we are not leaking anything and be aware of the lifecycle events at the same time
 * Execution is similar to observing a `LiveData` but eliminates the need to use it
 * @param flow - flow that needs to be executed
 * @param action - execution block
 */
fun <T> Fragment.safeCollectFlow(flow: Flow<T>, action: suspend (T) -> Unit) =
    viewLifecycleOwner.lifecycleScope.launchWhenStarted {
        flow.onEach(action)
            .launchIn(this)
    }
