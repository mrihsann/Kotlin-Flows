package com.ihsanarslan.kotlinflows.c_flow_context

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis

/**
 * Bu dosya, Kotlin Flow'da context (bağlam) yönetimini gösteren örnekler içerir.
 * Her örnek, farklı bir context yönetimi senaryosunu gösterir.
 */

class FlowContextExample {

    /**
     * Örnek 1: flowOn Operatörü
     * Bu örnek, Flow'un çalışacağı context'i değiştirmek için flowOn operatörünü kullanır.
     */
    fun example1() = flow {
        println("Emission context: ${Thread.currentThread().name}")
        for (i in 1..3) {
            emit(i)
        }
    }.flowOn(Dispatchers.IO) // Flow'u IO dispatcher'da çalıştırıyoruz

    /**
     * Örnek 2: Context Değişikliği ve Hata
     * Bu örnek, yanlış context değişikliğinin nasıl hata oluşturabileceğini gösterir.
     */
    fun example2() = flow {
        withContext(Dispatchers.IO) {
            emit(1) // HATA: Flow builder içinde context değiştirilemez
        }
    }

    /**
     * Örnek 3: Doğru Context Değişikliği
     * Bu örnek, context değişikliğinin doğru yapılmasını gösterir.
     */
    fun example3() = flow {
        emit(1)
        emit(2)
        emit(3)
    }.flowOn(Dispatchers.IO) // Doğru kullanım

    /**
     * Örnek 4: Farklı Context'lerde İşlem
     * Bu örnek, farklı context'lerde işlem yapmanın performans etkisini gösterir.
     */
    fun example4() = flow {
        val time = measureTimeMillis {
            for (i in 1..1000) {
                emit(i)
            }
        }
        println("Emission time: $time ms")
    }.flowOn(Dispatchers.Default)

    /**
     * Örnek 5: Context ve Exception Handling
     * Bu örnek, farklı context'lerde exception handling'i gösterir.
     */
    fun example5() = flow {
        try {
            emit(1)
            throw RuntimeException("Test exception")
        } catch (e: Exception) {
            println("Exception caught in: ${Thread.currentThread().name}")
            emit(-1)
        }
    }.flowOn(Dispatchers.IO)

    /**
     * Örnek 6: Context ve Cancellation
     * Bu örnek, context değişikliğinin cancellation davranışını nasıl etkilediğini gösterir.
     */
    fun example6() = flow {
        try {
            for (i in 1..5) {
                delay(100)
                emit(i)
            }
        } finally {
            println("Flow cancelled in: ${Thread.currentThread().name}")
        }
    }.flowOn(Dispatchers.IO)

    /**
     * Örnek 7: Context ve Backpressure
     * Bu örnek, context değişikliğinin backpressure davranışını nasıl etkilediğini gösterir.
     */
    fun example7() = flow {
        for (i in 1..5) {
            delay(100)
            emit(i)
        }
    }.flowOn(Dispatchers.IO)
        .buffer() // Backpressure yönetimi için buffer ekliyoruz

    /**
     * Örnek 8: Context ve Conflate
     * Bu örnek, context değişikliği ile conflate operatörünün kullanımını gösterir.
     */
    fun example8() = flow {
        for (i in 1..5) {
            delay(100)
            emit(i)
        }
    }.flowOn(Dispatchers.IO)
        .conflate() // En son değeri tutuyoruz

    /**
     * Örnek 9: Context ve CollectLatest
     * Bu örnek, context değişikliği ile collectLatest operatörünün kullanımını gösterir.
     */
    fun example9() = flow {
        for (i in 1..5) {
            delay(100)
            emit(i)
        }
    }.flowOn(Dispatchers.IO)

    /**
     * Örnek 10: Context ve Multiple Operators
     * Bu örnek, birden fazla operatörün context değişikliği ile kullanımını gösterir.
     */
    fun example10() = flow {
        for (i in 1..5) {
            emit(i)
        }
    }.map { it * 2 }
        .flowOn(Dispatchers.IO)
        .filter { it % 3 == 0 }
        .flowOn(Dispatchers.Default)
}

/**
 * Flow context örneklerini test etmek için kullanılan fonksiyon
 */
fun main() = runBlocking {
    val example = FlowContextExample()

    // Örnek 1: flowOn Operatörü
    println("Örnek 1 - flowOn Operatörü:")
    example.example1().collect { value ->
        println("Collect context: ${Thread.currentThread().name}")
        println(value)
    }

    // Örnek 3: Doğru Context Değişikliği
    println("\nÖrnek 3 - Doğru Context Değişikliği:")
    example.example3().collect { value ->
        println("Collect context: ${Thread.currentThread().name}")
        println(value)
    }

    // Örnek 4: Farklı Context'lerde İşlem
    println("\nÖrnek 4 - Farklı Context'lerde İşlem:")
    example.example4().collect { value ->
        println("Collect context: ${Thread.currentThread().name}")
    }

    // Örnek 5: Context ve Exception Handling
    println("\nÖrnek 5 - Context ve Exception Handling:")
    example.example5().collect { value ->
        println("Collect context: ${Thread.currentThread().name}")
        println(value)
    }

    // Örnek 6: Context ve Cancellation
    println("\nÖrnek 6 - Context ve Cancellation:")
    val job = launch {
        example.example6().collect { value ->
            println("Collect context: ${Thread.currentThread().name}")
            println(value)
            if (value == 3) {
                cancel() // Flow'u iptal ediyoruz
            }
        }
    }
    job.join()

    // Örnek 7: Context ve Backpressure
    println("\nÖrnek 7 - Context ve Backpressure:")
    example.example7().collect { value ->
        println("Collect context: ${Thread.currentThread().name}")
        println(value)
        delay(200) // Yavaş tüketim
    }

    // Örnek 8: Context ve Conflate
    println("\nÖrnek 8 - Context ve Conflate:")
    example.example8().collect { value ->
        println("Collect context: ${Thread.currentThread().name}")
        println(value)
        delay(300) // Yavaş tüketim
    }

    // Örnek 9: Context ve CollectLatest
    println("\nÖrnek 9 - Context ve CollectLatest:")
    example.example9().collectLatest { value ->
        println("Collect context: ${Thread.currentThread().name}")
        println(value)
        delay(300) // Yavaş tüketim
    }

    // Örnek 10: Context ve Multiple Operators
    println("\nÖrnek 10 - Context ve Multiple Operators:")
    example.example10().collect { value ->
        println("Collect context: ${Thread.currentThread().name}")
        println(value)
    }
} 