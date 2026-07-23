package com.pawmap.app.ui.common

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.pawmap.app.R
import kotlin.random.Random

/**
 * A stylized, non-interactive stand-in for a real map.
 *
 * The place data in this app is local sample data, so instead of pulling in a
 * Google/Kakao map SDK (which needs an API key) we draw a dark map-like backdrop
 * with "roads", building blocks and category-colored paw markers. Swap this out
 * for a real MapView later without touching the screens that host it.
 */
class PlaceholderMapView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    data class Marker(
        val xFraction: Float,   // 0f..1f horizontal position
        val yFraction: Float,   // 0f..1f vertical position
        val color: Int,
        val number: Int? = null,
        val id: Long = -1L      // place id for tap handling
    )

    private var onMarkerClick: ((Long) -> Unit)? = null

    fun setOnMarkerClickListener(listener: (Long) -> Unit) {
        onMarkerClick = listener
    }

    private val bgColor = ContextCompat.getColor(context, R.color.map_bg)
    private val roadColor = ContextCompat.getColor(context, R.color.map_road)
    private val blockColor = ContextCompat.getColor(context, R.color.map_block)

    private val roadPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = roadColor
        style = Paint.Style.STROKE
        strokeWidth = 6f
    }
    private val blockPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = blockColor }
    private val markerPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val whitePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.WHITE }
    private val numberPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textAlign = Paint.Align.CENTER
        isFakeBoldText = true
    }

    private var markers: List<Marker> = emptyList()

    fun setMarkers(markers: List<Marker>) {
        this.markers = markers
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val w = width.toFloat()
        val h = height.toFloat()

        // Background
        canvas.drawColor(bgColor)

        // Roads: a few diagonal + horizontal lines (deterministic layout)
        canvas.drawLine(w * 0.30f, 0f, w * 0.42f, h, roadPaint)
        canvas.drawLine(0f, h * 0.28f, w, h * 0.42f, roadPaint)
        canvas.drawLine(0f, h * 0.70f, w, h * 0.60f, roadPaint)
        canvas.drawLine(w * 0.72f, 0f, w * 0.80f, h, roadPaint)

        // Building blocks
        val rnd = Random(42)
        repeat(5) {
            val bx = w * (0.1f + 0.8f * rnd.nextFloat())
            val by = h * (0.1f + 0.7f * rnd.nextFloat())
            val bw = w * (0.08f + 0.06f * rnd.nextFloat())
            val bh = h * (0.06f + 0.05f * rnd.nextFloat())
            canvas.drawRoundRect(RectF(bx, by, bx + bw, by + bh), 8f, 8f, blockPaint)
        }

        // Markers (paw-cluster pins)
        val markerW = dp(30f)
        for (m in markers) {
            val cx = w * m.xFraction
            val cy = h * m.yFraction
            drawMarker(canvas, cx, cy, markerW, m.color, m.number)
        }
    }

    private fun drawMarker(canvas: Canvas, cx: Float, cy: Float, size: Float, color: Int, number: Int?) {
        markerPaint.color = color
        val r = size / 2f
        // Paw "flower" cluster above the pin: 4 small pads + center
        val pad = r * 0.42f
        val top = cy - size * 1.15f
        canvas.drawCircle(cx - r * 0.55f, top - r * 0.15f, pad, markerPaint)
        canvas.drawCircle(cx + r * 0.55f, top - r * 0.15f, pad, markerPaint)
        canvas.drawCircle(cx - r * 0.95f, top + r * 0.55f, pad * 0.9f, markerPaint)
        canvas.drawCircle(cx + r * 0.95f, top + r * 0.55f, pad * 0.9f, markerPaint)

        // Pin body (teardrop)
        val path = Path().apply {
            moveTo(cx, cy)
            cubicTo(cx - r, cy - size * 0.7f, cx - r, cy - size * 0.2f, cx, cy - size * 0.35f)
            cubicTo(cx + r, cy - size * 0.2f, cx + r, cy - size * 0.7f, cx, cy)
        }
        canvas.drawCircle(cx, cy - size * 0.55f, r, markerPaint)
        canvas.drawPath(path, markerPaint)

        // Inner white circle with number (or a dot)
        val innerR = r * 0.55f
        canvas.drawCircle(cx, cy - size * 0.6f, innerR, whitePaint)
        if (number != null) {
            markerPaint.color = color
            numberPaint.color = color
            numberPaint.textSize = innerR * 1.3f
            val ty = cy - size * 0.6f - (numberPaint.descent() + numberPaint.ascent()) / 2f
            canvas.drawText(number.toString(), cx, ty, numberPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP && onMarkerClick != null) {
            val w = width.toFloat()
            val h = height.toFloat()
            val threshold = dp(28f)
            var best: Marker? = null
            var bestDist = Float.MAX_VALUE
            for (m in markers) {
                if (m.id < 0) continue
                val cx = w * m.xFraction
                val cy = h * m.yFraction
                val dist = kotlin.math.hypot(event.x - cx, event.y - cy)
                if (dist < threshold && dist < bestDist) {
                    bestDist = dist
                    best = m
                }
            }
            best?.let {
                performClick()
                onMarkerClick?.invoke(it.id)
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean = super.performClick()

    private fun dp(v: Float): Float = v * resources.displayMetrics.density
}
