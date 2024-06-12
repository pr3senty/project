package com.example.strelka

class Action{
    private val observers : MutableList<() -> Unit> = mutableListOf()

    operator fun plusAssign(observer: () -> Unit) {
        observers.add(observer)
    }

    operator fun minusAssign(observer: () -> Unit) {
        observers.remove(observer)
    }

    operator fun invoke() {
        for (observer in observers)
            observer()
    }
}


class Event<T> {
    private val observers : MutableList<(T) -> Unit> = mutableListOf()

    operator fun plusAssign(observer: (T) -> Unit) {
        observers.add(observer)
    }

    operator fun minusAssign(observer: (T) -> Unit) {
        observers.remove(observer)
    }

    operator fun invoke(arg: T) {
        for (observer in observers)
            observer(arg)
    }
}