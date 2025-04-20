package com.ihsanarslan.kotlinflows.flow_operators

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * Bu dosya, Kotlin Flow operatörlerinin kullanımını gösteren örnekler içerir.
 * Her örnek, farklı bir Flow operatörünü veya operatör kombinasyonunu gösterir.
 */

class FlowOperatorsExample {

    /**
     * Örnek 1: map Operatörü
     * Bu örnek, Flow'daki her elemanı dönüştürmek için map operatörünü kullanır.
     */
    fun example1() = flow {
        emit(1)
        emit(2)
        emit(3)
    }.map { it * 2 } // Her elemanı 2 ile çarpıyoruz

    /**
     * Örnek 2: filter Operatörü
     * Bu örnek, Flow'daki elemanları filtrelemek için filter operatörünü kullanır.
     */
    fun example2() = flow {
        for (i in 1..10) {
            emit(i)
        }
    }.filter { it % 2 == 0 } // Sadece çift sayıları geçiriyoruz

    /**
     * Örnek 3: transform Operatörü
     * Bu örnek, Flow'daki elemanları özelleştirilmiş bir şekilde dönüştürmek için transform operatörünü kullanır.
     */
    fun example3() = flow {
        emit(1)
        emit(2)
        emit(3)
    }.transform { value ->
        emit("Sayı: $value")
        emit("Karesi: ${value * value}")
    }

    /**
     * Örnek 4: take Operatörü
     * Bu örnek, Flow'dan belirli sayıda eleman almak için take operatörünü kullanır.
     */
    fun example4() = flow {
        for (i in 1..10) {
            emit(i)
        }
    }.take(3) // İlk 3 elemanı alıyoruz

    /**
     * Örnek 5: drop Operatörü
     * Bu örnek, Flow'dan belirli sayıda eleman atlamak için drop operatörünü kullanır.
     */
    fun example5() = flow {
        for (i in 1..10) {
            emit(i)
        }
    }.drop(5) // İlk 5 elemanı atlıyoruz

    /**
     * Örnek 6: reduce Operatörü
     * Bu örnek, Flow'daki elemanları birleştirmek için reduce operatörünü kullanır.
     */
    suspend fun example6() = flow {
        for (i in 1..5) {
            emit(i)
        }
    }.reduce { accumulator, value ->
        accumulator + value // Tüm sayıları topluyoruz
    }

    /**
     * Örnek 7: fold Operatörü
     * Bu örnek, Flow'daki elemanları başlangıç değeriyle birleştirmek için fold operatörünü kullanır.
     */
    suspend fun example7() = flow {
        for (i in 1..5) {
            emit(i)
        }
    }.fold(10) { accumulator, value ->
        accumulator + value // 10'dan başlayarak tüm sayıları topluyoruz
    }

    /**
     * Örnek 8: flatMapConcat Operatörü
     * Bu örnek, Flow'daki her elemanı yeni bir Flow'a dönüştürmek için flatMapConcat operatörünü kullanır.
     */
    fun example8() = flow {
        emit(1)
        emit(2)
        emit(3)
    }.flatMapConcat { value ->
        flow {
            emit(value)
            emit(value * 2)
            emit(value * 3)
        }
    }

    /**
     * Örnek 9: zip Operatörü
     * Bu örnek, iki Flow'u birleştirmek için zip operatörünü kullanır.
     */
    fun example9() = flow {
        emit(1)
        emit(2)
        emit(3)
    }.zip(flow {
        emit("A")
        emit("B")
        emit("C")
    }) { number, letter ->
        "$number$letter"
    }

    /**
     * Örnek 10: combine Operatörü
     * Bu örnek, iki Flow'u birleştirmek ve her değişiklikte yeni bir değer üretmek için combine operatörünü kullanır.
     */
    fun example10() = flow {
        emit(1)
        delay(100)
        emit(2)
        delay(100)
        emit(3)
    }.combine(flow {
        emit("A")
        delay(150)
        emit("B")
        delay(150)
        emit("C")
    }) { number, letter ->
        "$number$letter"
    }
}

/**
 * Flow operatörlerini test etmek için kullanılan fonksiyon
 */
fun main() = runBlocking {
    val example = FlowOperatorsExample()

    // Örnek 1: map Operatörü
    println("Örnek 1 - map Operatörü:")
    example.example1().collect { value ->
        println(value)
    }

    // Örnek 2: filter Operatörü
    println("\nÖrnek 2 - filter Operatörü:")
    example.example2().collect { value ->
        println(value)
    }

    // Örnek 3: transform Operatörü
    println("\nÖrnek 3 - transform Operatörü:")
    example.example3().collect { value ->
        println(value)
    }

    // Örnek 4: take Operatörü
    println("\nÖrnek 4 - take Operatörü:")
    example.example4().collect { value ->
        println(value)
    }

    // Örnek 5: drop Operatörü
    println("\nÖrnek 5 - drop Operatörü:")
    example.example5().collect { value ->
        println(value)
    }

    // Örnek 6: reduce Operatörü
    println("\nÖrnek 6 - reduce Operatörü:")
    val result6 = example.example6()
    println("Toplam: $result6")

    // Örnek 7: fold Operatörü
    println("\nÖrnek 7 - fold Operatörü:")
    val result7 = example.example7()
    println("Toplam: $result7")

    // Örnek 8: flatMapConcat Operatörü
    println("\nÖrnek 8 - flatMapConcat Operatörü:")
    example.example8().collect { value ->
        println(value)
    }

    // Örnek 9: zip Operatörü
    println("\nÖrnek 9 - zip Operatörü:")
    example.example9().collect { value ->
        println(value)
    }

    // Örnek 10: combine Operatörü
    println("\nÖrnek 10 - combine Operatörü:")
    example.example10().collect { value ->
        println(value)
    }
} 