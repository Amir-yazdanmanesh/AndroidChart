package com.github.mikephil.charting.compose.animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

/**
 * Remembers and runs a chart animation that progresses from 0f to 1f.
 *
 * Returns a [State] containing the current animation progress. Pass this value
 * to the `animationProgress` parameter of any chart composable.
 *
 * @param durationMillis Duration of the animation in milliseconds
 * @param easing Easing function for the animation curve
 * @param delayMillis Delay before starting the animation
 * @param key Optional key that restarts the animation when it changes
 * @return State containing the current animation progress (0f to 1f)
 *
 * Example:
 * ```
 * val progress by rememberChartAnimation(durationMillis = 1000)
 * LineChart(data = data, animationProgress = progress)
 * ```
 */
@Composable
fun rememberChartAnimation(
    durationMillis: Int = 800,
    easing: Easing = ChartEasing.EaseOutCubic,
    delayMillis: Int = 0,
    key: Any? = Unit
): State<Float> {
    val animatable = remember(key) { Animatable(0f) }

    LaunchedEffect(key) {
        animatable.snapTo(0f)
        animatable.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = durationMillis,
                delayMillis = delayMillis,
                easing = easing
            )
        )
    }

    return animatable.asState()
}

/**
 * Remembers and runs a chart animation with a custom [AnimationSpec].
 *
 * @param animationSpec The animation specification to use
 * @param key Optional key that restarts the animation when it changes
 * @return State containing the current animation progress (0f to 1f)
 */
@Composable
fun rememberChartAnimation(
    animationSpec: AnimationSpec<Float>,
    key: Any? = Unit
): State<Float> {
    val animatable = remember(key) { Animatable(0f) }

    LaunchedEffect(key) {
        animatable.snapTo(0f)
        animatable.animateTo(
            targetValue = 1f,
            animationSpec = animationSpec
        )
    }

    return animatable.asState()
}

/**
 * State holder for independent X and Y chart animations.
 *
 * Allows different durations and easing functions for X and Y axes,
 * matching the original MPAndroidChart `animateXY()` API.
 *
 * @property phaseX Current X-axis animation progress (0f to 1f)
 * @property phaseY Current Y-axis animation progress (0f to 1f)
 * @property combined Combined animation progress (phaseX * phaseY). Use this when
 *  you want a single animationProgress value that incorporates both axes.
 */
@Stable
class ChartAnimationState internal constructor() {
    var phaseX by mutableFloatStateOf(0f)
        internal set
    var phaseY by mutableFloatStateOf(0f)
        internal set

    val combined: Float get() = phaseX * phaseY
}

/**
 * Remembers and runs independent X and Y chart animations.
 *
 * This matches the original `ChartAnimator.animateXY()` API, allowing different
 * durations and easing functions for each axis.
 *
 * @param durationMillisX Duration of X-axis animation in milliseconds
 * @param durationMillisY Duration of Y-axis animation in milliseconds
 * @param easingX Easing function for X-axis animation
 * @param easingY Easing function for Y-axis animation
 * @param delayMillis Delay before starting animations
 * @param key Optional key that restarts animations when it changes
 * @return [ChartAnimationState] containing phaseX and phaseY values
 *
 * Example:
 * ```
 * val animState = rememberChartAnimationXY(
 *     durationMillisX = 1000,
 *     durationMillisY = 1500,
 *     easingX = ChartEasing.EaseInOutQuad,
 *     easingY = ChartEasing.EaseInOutCubic
 * )
 * LineChart(data = data, animationProgress = animState.phaseY)
 * ```
 */
@Composable
fun rememberChartAnimationXY(
    durationMillisX: Int = 800,
    durationMillisY: Int = 800,
    easingX: Easing = ChartEasing.EaseOutCubic,
    easingY: Easing = ChartEasing.EaseOutCubic,
    delayMillis: Int = 0,
    key: Any? = Unit
): ChartAnimationState {
    val state = remember(key) { ChartAnimationState() }
    val animatableX = remember(key) { Animatable(0f) }
    val animatableY = remember(key) { Animatable(0f) }

    LaunchedEffect(key) {
        animatableX.snapTo(0f)
        animatableX.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = durationMillisX,
                delayMillis = delayMillis,
                easing = easingX
            )
        )
    }

    LaunchedEffect(key) {
        animatableY.snapTo(0f)
        animatableY.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = durationMillisY,
                delayMillis = delayMillis,
                easing = easingY
            )
        )
    }

    state.phaseX = animatableX.value
    state.phaseY = animatableY.value

    return state
}

/**
 * Remembers and runs only an X-axis animation.
 *
 * @param durationMillis Duration of the animation in milliseconds
 * @param easing Easing function for the animation
 * @param delayMillis Delay before starting the animation
 * @param key Optional key that restarts the animation when it changes
 * @return [ChartAnimationState] with phaseX animated, phaseY = 1f
 */
@Composable
fun rememberChartAnimationX(
    durationMillis: Int = 800,
    easing: Easing = ChartEasing.EaseOutCubic,
    delayMillis: Int = 0,
    key: Any? = Unit
): ChartAnimationState {
    val state = remember(key) { ChartAnimationState() }
    val animatable = remember(key) { Animatable(0f) }

    LaunchedEffect(key) {
        animatable.snapTo(0f)
        animatable.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = durationMillis,
                delayMillis = delayMillis,
                easing = easing
            )
        )
    }

    state.phaseX = animatable.value
    state.phaseY = 1f

    return state
}

/**
 * Remembers and runs only a Y-axis animation.
 *
 * @param durationMillis Duration of the animation in milliseconds
 * @param easing Easing function for the animation
 * @param delayMillis Delay before starting the animation
 * @param key Optional key that restarts the animation when it changes
 * @return [ChartAnimationState] with phaseY animated, phaseX = 1f
 */
@Composable
fun rememberChartAnimationY(
    durationMillis: Int = 800,
    easing: Easing = ChartEasing.EaseOutCubic,
    delayMillis: Int = 0,
    key: Any? = Unit
): ChartAnimationState {
    val state = remember(key) { ChartAnimationState() }
    val animatable = remember(key) { Animatable(0f) }

    LaunchedEffect(key) {
        animatable.snapTo(0f)
        animatable.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = durationMillis,
                delayMillis = delayMillis,
                easing = easing
            )
        )
    }

    state.phaseX = 1f
    state.phaseY = animatable.value

    return state
}
