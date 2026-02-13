package com.github.mikephil.charting.compose.animation

import androidx.compose.animation.core.Easing
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * Collection of easing functions for chart animations.
 *
 * Provides 30+ easing functions matching the original MPAndroidChart easing types.
 * Each function maps an input fraction (0-1) to an output fraction (0-1) with
 * various acceleration/deceleration curves.
 *
 * Usage with Compose:
 * ```
 * val progress by rememberChartAnimation(
 *     easing = ChartEasing.EaseInOutCubic
 * )
 * LineChart(data = data, animationProgress = progress)
 * ```
 */
object ChartEasing {

    // region Linear

    val Linear: Easing = Easing { it }

    // endregion

    // region Quadratic

    val EaseInQuad: Easing = Easing { t -> t * t }

    val EaseOutQuad: Easing = Easing { t -> -t * (t - 2f) }

    val EaseInOutQuad: Easing = Easing { t ->
        val x = t * 2f
        if (x < 1f) 0.5f * x * x
        else -0.5f * ((x - 1f) * (x - 3f) - 1f)
    }

    // endregion

    // region Cubic

    val EaseInCubic: Easing = Easing { t -> t * t * t }

    val EaseOutCubic: Easing = Easing { t ->
        val x = t - 1f
        x * x * x + 1f
    }

    val EaseInOutCubic: Easing = Easing { t ->
        val x = t * 2f
        if (x < 1f) 0.5f * x * x * x
        else {
            val y = x - 2f
            0.5f * (y * y * y + 2f)
        }
    }

    // endregion

    // region Quartic

    val EaseInQuart: Easing = Easing { t -> t * t * t * t }

    val EaseOutQuart: Easing = Easing { t ->
        val x = t - 1f
        -(x * x * x * x - 1f)
    }

    val EaseInOutQuart: Easing = Easing { t ->
        val x = t * 2f
        if (x < 1f) 0.5f * x * x * x * x
        else {
            val y = x - 2f
            -0.5f * (y * y * y * y - 2f)
        }
    }

    // endregion

    // region Sinusoidal

    val EaseInSine: Easing = Easing { t ->
        -cos(t * HALF_PI) + 1f
    }

    val EaseOutSine: Easing = Easing { t ->
        sin(t * HALF_PI)
    }

    val EaseInOutSine: Easing = Easing { t ->
        -0.5f * (cos(PI * t) - 1f)
    }

    // endregion

    // region Exponential

    val EaseInExpo: Easing = Easing { t ->
        if (t == 0f) 0f else 2f.pow(10f * (t - 1f))
    }

    val EaseOutExpo: Easing = Easing { t ->
        if (t == 1f) 1f else -(2f.pow(-10f * t)) + 1f
    }

    val EaseInOutExpo: Easing = Easing { t ->
        when {
            t == 0f -> 0f
            t == 1f -> 1f
            else -> {
                val x = t * 2f
                if (x < 1f) 0.5f * 2f.pow(10f * (x - 1f))
                else 0.5f * (-(2f.pow(-10f * (x - 1f))) + 2f)
            }
        }
    }

    // endregion

    // region Circular

    val EaseInCirc: Easing = Easing { t ->
        -(sqrt(1f - t * t) - 1f)
    }

    val EaseOutCirc: Easing = Easing { t ->
        val x = t - 1f
        sqrt(1f - x * x)
    }

    val EaseInOutCirc: Easing = Easing { t ->
        val x = t * 2f
        if (x < 1f) -0.5f * (sqrt(1f - x * x) - 1f)
        else {
            val y = x - 2f
            0.5f * (sqrt(1f - y * y) + 1f)
        }
    }

    // endregion

    // region Elastic

    val EaseInElastic: Easing = Easing { t ->
        if (t == 0f || t == 1f) t
        else {
            val x = t - 1f
            -(2f.pow(10f * x) * sin((x - 0.075f) * TWO_PI / 0.3f))
        }
    }

    val EaseOutElastic: Easing = Easing { t ->
        if (t == 0f || t == 1f) t
        else {
            2f.pow(-10f * t) * sin((t - 0.075f) * TWO_PI / 0.3f) + 1f
        }
    }

    val EaseInOutElastic: Easing = Easing { t ->
        when {
            t == 0f || t == 1f -> t
            else -> {
                val x = t * 2f - 1f
                if (x < 0f) {
                    -0.5f * (2f.pow(10f * x) * sin((x - 0.1125f) * TWO_PI / 0.45f))
                } else {
                    2f.pow(-10f * x) * sin((x - 0.1125f) * TWO_PI / 0.45f) * 0.5f + 1f
                }
            }
        }
    }

    // endregion

    // region Back

    private const val BACK_S = 1.70158f
    private const val BACK_S2 = BACK_S * 1.525f

    val EaseInBack: Easing = Easing { t ->
        t * t * ((BACK_S + 1f) * t - BACK_S)
    }

    val EaseOutBack: Easing = Easing { t ->
        val x = t - 1f
        x * x * ((BACK_S + 1f) * x + BACK_S) + 1f
    }

    val EaseInOutBack: Easing = Easing { t ->
        val x = t * 2f
        if (x < 1f) {
            0.5f * (x * x * ((BACK_S2 + 1f) * x - BACK_S2))
        } else {
            val y = x - 2f
            0.5f * (y * y * ((BACK_S2 + 1f) * y + BACK_S2) + 2f)
        }
    }

    // endregion

    // region Bounce

    val EaseOutBounce: Easing = Easing { t ->
        when {
            t < 1f / 2.75f -> 7.5625f * t * t
            t < 2f / 2.75f -> {
                val x = t - 1.5f / 2.75f
                7.5625f * x * x + 0.75f
            }
            t < 2.5f / 2.75f -> {
                val x = t - 2.25f / 2.75f
                7.5625f * x * x + 0.9375f
            }
            else -> {
                val x = t - 2.625f / 2.75f
                7.5625f * x * x + 0.984375f
            }
        }
    }

    val EaseInBounce: Easing = Easing { t ->
        1f - EaseOutBounce.transform(1f - t)
    }

    val EaseInOutBounce: Easing = Easing { t ->
        if (t < 0.5f) {
            EaseInBounce.transform(t * 2f) * 0.5f
        } else {
            EaseOutBounce.transform(t * 2f - 1f) * 0.5f + 0.5f
        }
    }

    // endregion

    private const val PI = Math.PI.toFloat()
    private const val HALF_PI = PI / 2f
    private const val TWO_PI = PI * 2f
}
