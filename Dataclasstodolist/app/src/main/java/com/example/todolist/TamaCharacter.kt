package com.example.todolist

import android.graphics.drawable.AnimationDrawable
import android.view.ViewGroup
import android.widget.ImageView
import com.example.todolist.R

abstract class TamaCharacter {

    var state = intArrayOf(0, 0, 0) // level, experience, fail

    abstract fun getDrawable(): Int


    open fun lvUp() {
        if (state[1] >= 100) {
            state[1] = 0
            state[0]++
            if (state[2] >= 3) {

                evolve()
            }
        } else {

            state[2]++
        }
    }


    fun feed() {
        state[1] += 10
        if (state[1] >= 100) {
            state[1] = 100
            lvUp()
        }
    }

    open fun evolve() {
        val evolvedChar = when (this) {
            is Whale -> Whale()
            else -> null
        }

        evolvedChar?.let {
            state[0] = it.state[0]
            state[1] = it.state[1]
            state[2] = it.state[2]
        }
    }
}

class Whale : TamaCharacter() {

    companion object {
        const val ID = 1
        const val DRAWABLE_ID = R.drawable.png_ggam
        val state = intArrayOf(1, 0, 0) // level, experience, fail
    }

    private var animationDrawable: AnimationDrawable? = null
    private lateinit var imageView: ImageView
    private var directionX = 0 // -1, 0, 1 중 랜덤 값
    private var directionY = 0 // -1, 0, 1 중 랜덤 값
    private var isMoving = false
    private var moveStopTime = 0L
    private var moveStopDuration = 500L

    override fun getDrawable(): Int {
        return DRAWABLE_ID
    }

    fun move() {
        val curX = imageView.x
        val curY = imageView.y
        val w = imageView.width ?: imageView.measuredWidth
        val h = imageView.height ?: imageView.measuredHeight
        val parentWidth = (imageView.parent as ViewGroup).width
        val parentHeight = (imageView.parent as ViewGroup).height
        val rotationY = if (directionX > 0) 0f else 180f
        imageView.rotationY = rotationY

        if (!isMoving && System.currentTimeMillis() >= moveStopTime) {
            isMoving = true
            moveStopTime = System.currentTimeMillis() + moveStopDuration
            directionX = (-1..1).random()
            directionY = (-1..1).random()
        }

        if (isMoving) {

            val newX = curX + directionX * (Math.random() * 3 + 1).toFloat()
            val newY = curY + directionY * (Math.random() * 3 - 1).toFloat()

            if (newX < -w || newX > parentWidth) {
                directionX = -directionX
            }
            if (newX < 0) {
                imageView.scaleX = -Math.signum(newX)
                imageView.x = 0f
                directionX = 1
            } else if (newX > parentWidth - w) {
                imageView.x = (parentWidth - w).toFloat()
                directionX = -1
            } else {
                imageView.x = newX
            }

            if (newY < -h || newY > parentHeight) {
                directionY = -directionY
            }
            if (newY < 0) {
                imageView.y = 0f
                directionY = 1
            } else if (newY > parentHeight - h) {
                imageView.y = (parentHeight - h).toFloat()
                directionY = -1
            } else {
                imageView.y = newY
            }

        }
    }

    fun runAnimation(targetImageView: ImageView) {
        imageView = targetImageView

        animationDrawable = AnimationDrawable()


        val frame1 = imageView?.resources?.getDrawable(R.drawable.png_ggam)
        val frame2 = imageView?.resources?.getDrawable(R.drawable.png_ggam2)

        frame1?.let { animationDrawable?.addFrame(it, 400) }
        frame2?.let { animationDrawable?.addFrame(it, 400) }

        animationDrawable?.isOneShot = false
        frame1?.let { animationDrawable?.setBounds(0, 0, it.intrinsicWidth, it.intrinsicHeight) } // 이미지 크기 설정

        imageView?.setImageDrawable(animationDrawable)

        animationDrawable?.start()
        Thread {
            while (true) {

                directionX = (-1..1).random()
                directionY = (-1..1).random()
                Thread.sleep(2000)
            }
        }.start()

        Thread {
            while (true) {
                move()
                Thread.sleep(10)
            }
        }.start()
    }
}

