package com.arpan.rxjava.chapter1

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

fun main() {
    observablesAreLazy()
}

fun reactiveHelloWorld() {
    Observable.create<String> { s ->
        s.onNext("Hello World")
        s.onComplete()
    }.subscribe { msg ->
        println(msg)
    }
}

fun observableStream() {
    val intStream = Observable.create<Int> { s ->
        s.onNext(1)
        s.onNext(2)
        s.onNext(3)
        s.onComplete()
    }

    intStream
        .map { num -> "Number: $num" }
        .subscribe { msg -> println(msg) }
}

fun observablesAreLazy() {
    val observable = Observable.create<Data> { s ->
        getDataFromServer { data ->
            s.onNext(data)
            s.onComplete()
        }
    }
    println("No server call is made yet.")
    observable.subscribe { data -> println(data) }
}

fun observablesCanBeReused() {
    val observable = Observable.create<String> { s ->
        s.onNext("Hello")
        s.onComplete()
    }

    observable.subscribe { msg -> println("Subscriber 1: $msg") }
    observable.subscribe { msg -> println("Subscriber 2: $msg") }
}

fun single() {
    val single1 = Single.just("Data A").subscribeOn(Schedulers.io())
    val single2 = Single.just("Data B").subscribeOn(Schedulers.io())

    val merged = Single.merge(single1, single2)

    merged.subscribe { msg -> println(msg) }
}

/**
 * Helpers
 */

data class Data(val msg: String)

fun getDataFromServer(callBack: (data: Data) -> Unit) {
    println("Getting data from server ...")
    Thread.sleep(1000L)
    println("Data received.")
    callBack(Data("Some data"))
}

