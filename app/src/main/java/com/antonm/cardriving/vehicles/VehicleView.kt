package com.antonm.cardriving.vehicles

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.antonm.cardriving.computers.OnBoardComputer

abstract class VehicleView<T: OnBoardComputer> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0):
    FrameLayout(context, attrs, defStyleAttr), Vehicle {

    private val ENGINE_SPEED = 16L

    protected var engine: Runnable = Runnable {
        directionObserver?.onVehicleDirectionUpdated()
    }
    private var isMoving = false
    private var directionObserver: Vehicle.DirectionObserver? = null
    private var onBoardComputer: T? = null
    override fun setDirectionObserver(observer: Vehicle.DirectionObserver?) {
        directionObserver = observer
    }

    override fun isMoving(): Boolean {
        return isMoving
    }

    override fun startMoving() {
        isMoving = true
        post(engine)
    }

    override fun move(rotation: Float, acceleration: Float) {
        postDelayed(engine, ENGINE_SPEED)
    }

    override fun stopMoving() {
        removeCallbacks(engine)
        onBoardComputer?.onVehicleStopped()
        isMoving = false
    }

    override fun getOnBoardComputer(): OnBoardComputer {
        if (onBoardComputer == null)
            onBoardComputer = instantiateComputer()
        return onBoardComputer!!
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopMoving()
    }

    abstract fun instantiateComputer(): T
}