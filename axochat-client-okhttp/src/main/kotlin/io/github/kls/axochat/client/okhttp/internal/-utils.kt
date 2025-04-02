package io.github.kls.axochat.client.okhttp.internal

import java.util.IdentityHashMap

internal inline fun <I, V> createEventMap(
    allClasses: Array<Class<out I>>,
    valueProvider: (Class<out I>) -> V
): MutableMap<Class<out I>, V> = createEventMap(allClasses.asList(), valueProvider)

internal inline fun <I, V> createEventMap(
    allClasses: Collection<Class<out I>>,
    valueProvider: (Class<out I>) -> V
): MutableMap<Class<out I>, V> = allClasses.associateWithTo(
    IdentityHashMap(allClasses.size / 2 * 3 + 1),
    valueProvider
)

