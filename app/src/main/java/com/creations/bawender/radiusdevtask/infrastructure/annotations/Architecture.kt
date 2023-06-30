package com.creations.bawender.radiusdevtask.infrastructure.annotations

/**
 * Marker annotation to improve the readability of the code.
 * These annotations on their own do nothing but can improve
 * the visibility in a larger project
 */

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class State


@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY)
annotation class Computed