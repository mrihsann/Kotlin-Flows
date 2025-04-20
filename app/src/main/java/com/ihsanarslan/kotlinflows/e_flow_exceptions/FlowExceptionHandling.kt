package com.ihsanarslan.kotlinflows.e_flow_exceptions

// 4. Flow Exception Handling Example
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.io.IOException

/**
 * Flow Exception Handling Examples
 * Demonstrates:
 * 1. Basic exception handling with try-catch
 * 2. catch operator
 * 3. retry operator
 * 4. retryWhen operator
 * 5. onCompletion operator
 */
class FlowExceptionHandling {
    
    // Example 1: Basic exception handling
    fun basicExceptionFlow(): Flow<Int> = flow {
        try {
            emit(1)
            throw IOException("Network error")
            emit(2) // This won't be executed
        } catch (e: Exception) {
            println("Caught exception: ${e.message}")
            emit(-1) // Emit error value
        }
    }

    // Example 2: Using catch operator
    fun catchOperatorFlow(): Flow<Int> = flow {
        emit(1)
        throw IOException("Network error")
        emit(2)
    }.catch { e ->
        println("Caught in catch operator: ${e.message}")
        emit(-1)
    }

    // Example 3: Using retry operator
    fun retryFlow(): Flow<Int> = flow {
        emit(1)
        throw IOException("Network error")
        emit(2)
    }.retry(2) { cause ->
        println("Retrying due to: ${cause.message}")
        delay(100)
        true // Always retry
    }

    // Example 4: Using retryWhen operator
    fun retryWhenFlow(): Flow<Int> = flow {
        emit(1)
        throw IOException("Network error")
        emit(2)
    }.retryWhen { cause, attempt ->
        if (cause is IOException && attempt < 3) {
            println("Retrying (attempt $attempt) due to: ${cause.message}")
            delay(100)
            true
        } else {
            false
        }
    }

    // Example 5: Using onCompletion operator
    fun completionFlow(): Flow<Int> = flow {
        emit(1)
        emit(2)
    }.onCompletion { cause ->
        if (cause != null) {
            println("Flow completed with exception: ${cause.message}")
        } else {
            println("Flow completed successfully")
        }
    }
}

// Example usage
fun main() = runBlocking {
    val example = FlowExceptionHandling()
    
    println("Basic Exception Handling:")
    example.basicExceptionFlow().collect { value ->
        println("Received: $value")
    }
    
    println("\nCatch Operator:")
    example.catchOperatorFlow().collect { value ->
        println("Received: $value")
    }
    
    println("\nRetry Operator:")
    example.retryFlow().catch { e ->
        println("Final error: ${e.message}")
    }.collect { value ->
        println("Received: $value")
    }
    
    println("\nRetryWhen Operator:")
    example.retryWhenFlow().catch { e ->
        println("Final error: ${e.message}")
    }.collect { value ->
        println("Received: $value")
    }
    
    println("\nCompletion Operator:")
    example.completionFlow().collect { value ->
        println("Received: $value")
    }
} 