package com.ihsanarslan.kotlinflows.a_basic_flows

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * Bu dosya, Kotlin Flow'un temel kavramlarını ve kullanımını gösteren örnekler içerir.
 * Her örnek, farklı bir Flow özelliğini veya kullanım senaryosunu gösterir.
 */

class SimpleFlowExample {

    /**
     * Örnek 1: Basit Flow Oluşturma ve Kullanma
     * Bu örnek, en temel Flow oluşturma ve kullanma yöntemini gösterir.
     */
    fun example1() = flow {
        // Flow builder kullanarak değerler yayınlıyoruz
        emit(1)
        emit(2)
        emit(3)
    }

    /**
     * Örnek 2: Flow Builder ile Dizi Dönüşümü
     * Bu örnek, bir diziyi Flow'a dönüştürmeyi gösterir.
     */
    fun example2() = flow {
        val numbers = listOf(1, 2, 3, 4, 5)
        numbers.forEach { emit(it) }
    }

    /**
     * Örnek 3: Flow Builder ile Asenkron İşlemler
     * Bu örnek, Flow içinde asenkron işlemlerin nasıl yapılabileceğini gösterir.
     */
    fun example3() = flow {
        for (i in 1..3) {
            delay(100) // Asenkron işlem simülasyonu
            emit(i)
        }
    }

    /**
     * Örnek 4: Flow Builder ile Koşullu Yayın
     * Bu örnek, belirli koşullara göre değer yayınlamayı gösterir.
     */
    fun example4() = flow {
        for (i in 1..10) {
            if (i % 2 == 0) {
                emit(i)
            }
        }
    }

    /**
     * Örnek 5: Flow Builder ile Hata Yönetimi
     * Bu örnek, Flow içinde hata yönetimini gösterir.
     */
    fun example5() = flow {
        try {
            emit(1)
            throw RuntimeException("Bir hata oluştu!")
            emit(2) // Bu satıra asla ulaşılmayacak
        } catch (e: Exception) {
            // Hata durumunda özel bir değer yayınlayabiliriz
            emit(-1)
        }
    }

    /**
     * Örnek 6: Flow Builder ile Sonsuz Akış
     * Bu örnek, sonsuz bir akış oluşturmayı gösterir.
     */
    fun example6() = flow {
        var i = 0
        while (true) {
            emit(i++)
            delay(1000)
        }
    }

    /**
     * Örnek 7: Flow Builder ile Özel Veri Yapısı
     * Bu örnek, özel bir veri yapısını Flow'a dönüştürmeyi gösterir.
     */
    data class User(val id: Int, val name: String)

    fun example7() = flow {
        val users = listOf(
            User(1, "Ahmet"),
            User(2, "Mehmet"),
            User(3, "Ayşe")
        )
        users.forEach { emit(it) }
    }

    /**
     * Örnek 8: Flow Builder ile Transformasyon
     * Bu örnek, Flow içinde veri dönüşümlerini gösterir.
     */
    fun example8() = flow {
        for (i in 1..5) {
            emit(i * i) // Sayıların karesini yayınlıyoruz
        }
    }

    /**
     * Örnek 9: Flow Builder ile Filtreleme
     * Bu örnek, Flow içinde filtreleme işlemlerini gösterir.
     */
    fun example9() = flow {
        for (i in 1..10) {
            if (i > 5) {
                emit(i)
            }
        }
    }

    /**
     * Örnek 10: Flow Builder ile Birleştirme
     * Bu örnek, birden fazla Flow'u birleştirmeyi gösterir.
     */
    fun example10() = flow {
        val flow1 = flowOf(1, 2, 3)
        val flow2 = flowOf(4, 5, 6)
        
        flow1.collect { emit(it) }
        flow2.collect { emit(it) }
    }
}

/**
 * Flow'ları test etmek için kullanılan fonksiyon
 */
fun main() = runBlocking {
    val example = SimpleFlowExample()

    // Örnek 1: Basit Flow
    println("Örnek 1 - Basit Flow:")
    example.example1().collect { value ->
        println(value)
    }

    // Örnek 2: Dizi Dönüşümü
    println("\nÖrnek 2 - Dizi Dönüşümü:")
    example.example2().collect { value ->
        println(value)
    }

    // Örnek 3: Asenkron İşlemler
    println("\nÖrnek 3 - Asenkron İşlemler:")
    example.example3().collect { value ->
        println(value)
    }

    // Örnek 4: Koşullu Yayın
    println("\nÖrnek 4 - Koşullu Yayın:")
    example.example4().collect { value ->
        println(value)
    }

    // Örnek 5: Hata Yönetimi
    println("\nÖrnek 5 - Hata Yönetimi:")
    example.example5().collect { value ->
        println(value)
    }

    // Örnek 6: Sonsuz Akış (5 saniye sonra iptal edilecek)
    println("\nÖrnek 6 - Sonsuz Akış:")
    val job = launch {
        example.example6().collect { value ->
            println(value)
        }
    }
    delay(5000)
    job.cancel()

    // Örnek 7: Özel Veri Yapısı
    println("\nÖrnek 7 - Özel Veri Yapısı:")
    example.example7().collect { user ->
        println("${user.id}: ${user.name}")
    }

    // Örnek 8: Transformasyon
    println("\nÖrnek 8 - Transformasyon:")
    example.example8().collect { value ->
        println(value)
    }

    // Örnek 9: Filtreleme
    println("\nÖrnek 9 - Filtreleme:")
    example.example9().collect { value ->
        println(value)
    }

    // Örnek 10: Birleştirme
    println("\nÖrnek 10 - Birleştirme:")
    example.example10().collect { value ->
        println(value)
    }
} 