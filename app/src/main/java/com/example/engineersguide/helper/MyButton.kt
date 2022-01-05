package com.example.engineersguide.helper

import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.engineersguide.listener.MyButtonClickListener


class MyButton(
    private val context: FragmentActivity, private val text: String,
    private val textSize: Int,
    private val imageResId: Int,
    private val color: Int,
    private val listener: MyButtonClickListener
) {
    private var pos: Int = 0
    private var clickRegion: RectF? = null
    private val resources: Resources = context.resources

    fun onClick(x: Float, y: Float): Boolean {
        if (clickRegion != null && clickRegion!!.contains(x, y)) {
            listener.onClick(pos)
            return true
        }
        return false
    }

    fun onDraw(c: Canvas, rectF: RectF, pos: Int) {
        val p = Paint()
        p.color = color
        c.drawRect(rectF, p)

        //Text
        p.color = Color.WHITE
        p.textSize = textSize.toFloat()

        val r = Rect()
        val cHeight = rectF.height()
        val cWigth = rectF.width()
        p.textAlign = Paint.Align.LEFT
        p.getTextBounds(text, 0, text.length, r)
        val x: Float
        val y: Float
        if (imageResId == 0) {
            x = cWigth / 2f - r.width() / 2f - r.left.toFloat()
            y = cHeight / 1.5f - r.height() / 1.5f - r.bottom.toFloat()
            c.drawText(text, rectF.left + x, rectF.top + y, p)
        } else {
            val d = ContextCompat.getDrawable(context,imageResId)
            val bitmap = drwableToBitmap(d)
            c.drawBitmap(bitmap, (rectF.left + rectF.right) / 2.05f, (rectF.top + rectF.bottom) / 2, p)
        }

        clickRegion = rectF
        this.pos = pos
    }

    private fun drwableToBitmap(d: Drawable?): Bitmap {
        if (d is BitmapDrawable) return d.bitmap
        val bitmap =
            Bitmap.createBitmap(d!!.intrinsicWidth, d.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        d.setBounds(0, 0, canvas.width, canvas.height)
        d.draw(canvas)
        return bitmap
    }


}