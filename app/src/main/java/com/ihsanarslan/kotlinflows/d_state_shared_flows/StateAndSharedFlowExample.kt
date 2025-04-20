package com.ihsanarslan.kotlinflows.d_state_shared_flows

// 3. StateFlow and SharedFlow Example
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * StateFlow and SharedFlow Examples
 * Demonstrates:
 * 1. StateFlow basics
 * 2. SharedFlow basics
 * 3. StateFlow vs SharedFlow differences
 * 4. StateFlow in UI state management
 * 5. SharedFlow for events
 */
class StateAndSharedFlowExample {
    
    // Example 1: Basic StateFlow
    private val _counter = MutableStateFlow(0)
    val counter: StateFlow<Int> = _counter.asStateFlow()

    fun incrementCounter() {
        _counter.value++
    }

    // Example 2: StateFlow with complex state
    data class UserState(
        val name: String = "",
        val age: Int = 0,
        val isLoggedIn: Boolean = false
    )

    private val _userState = MutableStateFlow(UserState())
    val userState: StateFlow<UserState> = _userState.asStateFlow()

    fun updateUser(name: String, age: Int) {
        _userState.value = _userState.value.copy(
            name = name,
            age = age,
            isLoggedIn = true
        )
    }

    // Example 3: SharedFlow for events
    private val _events = MutableSharedFlow<String>()
    val events: SharedFlow<String> = _events.asSharedFlow()

    suspend fun emitEvent(event: String) {
        _events.emit(event)
    }
}

// Example usage
fun main() = runBlocking {
    val example = StateAndSharedFlowExample()
    
    // Example 1: Counter StateFlow
    println("Counter StateFlow Example:")
    val job1 = launch {
        example.counter.collect { value ->
            println("Counter value: $value")
        }
    }
    
    repeat(3) {
        example.incrementCounter()
        delay(100)
    }
    job1.cancel()
    
    // Example 2: User StateFlow
    println("\nUser StateFlow Example:")
    val job2 = launch {
        example.userState.collect { state ->
            println("User state: $state")
        }
    }
    
    example.updateUser("John", 30)
    delay(100)
    job2.cancel()
    
    // Example 3: Events SharedFlow
    println("\nEvents SharedFlow Example:")
    val job3 = launch {
        example.events.collect { event ->
            println("Event received: $event")
        }
    }
    
    example.emitEvent("User logged in")
    example.emitEvent("Data loaded")
    delay(100)
    job3.cancel()
} 