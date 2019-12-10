package com.arpan.rxjava.chapter2

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

fun main() {
    tweetsObserverExample()
}

data class Tweet(val userId: String, val msg: String)

fun tweetsObserverExample() {
    val tweetsObservable = getTweetsObservable()

    class TweetsObserver : Observer<Tweet> {
        override fun onComplete() {
            println("No more tweets to consume")
        }

        override fun onSubscribe(d: Disposable?) {
            println("Started observing tweets")
        }

        override fun onNext(t: Tweet?) {
            println(t)
        }

        override fun onError(e: Throwable?) {
            println("Exception occurred")
            e?.printStackTrace()
        }
    }

    val tweetsObserver = TweetsObserver()
    tweetsObservable.subscribe(tweetsObserver)
}

private fun getTweetsObservable(): Observable<Tweet> {
    return Observable.create<Tweet> {
        it.onNext(Tweet("13231", "Hello"))
        Thread.sleep(1000L)
        it.onNext(Tweet("46456", "World"))
        Thread.sleep(2000L)
        it.onNext(Tweet("64656", "It is holiday"))
        Thread.sleep(500L)
        it.onNext(Tweet("6466", "Hey there"))
        Thread.sleep(4000L)
//        throw Exception("An exception occurred")
        it.onNext(Tweet("34666", "Just chillin"))
        Thread.sleep(1000L)
        it.onComplete()
    }
}



