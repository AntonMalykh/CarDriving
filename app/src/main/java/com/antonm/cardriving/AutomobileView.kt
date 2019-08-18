package com.antonm.cardriving

import android.content.Context
import android.util.AttributeSet


class AutomobileView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0)
    : VehicleView<AutomobileOnBoardComputer>(context, attrs, defStyleAttr) {

    private val WHEELS_MAX_ANGLE = 10f
    private val MIN_ACCELERATION = 10f

    private val ACCELERATION_THROTTTLE_COUNT = 5000

    private var accelerationThrottle = 1f

    override fun move(rotation: Float, acceleration: Float) {
        var actualAcceleration = acceleration
        if (accelerationThrottle <= ACCELERATION_THROTTTLE_COUNT) {
            actualAcceleration *= (accelerationThrottle / ACCELERATION_THROTTTLE_COUNT).toFloat()
            accelerationThrottle *= 1.5f
        }

        val applied = getOnBoardComputer().changeDirectory(
            OnBoardComputer.DirectionChangeRequest(rotation, actualAcceleration)
        )

        this.rotation += applied.angleBy
        translationX += applied.translationXBy
        translationY += applied.translationYBy


        super.move(rotation, acceleration)
    }

    override fun stopMoving() {
        super.stopMoving()
        accelerationThrottle = 1f
        getOnBoardComputer().onVehicleStopped()
    }

    override fun instantiateComputer(): AutomobileOnBoardComputer {
        return AutomobileOnBoardComputer(
            floatArrayOf(
                left + (right - left) / 2f,
                top + (bottom - top) / 2f,
                left + (right - left) / 2f,
                top.toFloat()
            ),
            MIN_ACCELERATION,
            WHEELS_MAX_ANGLE
        )
    }

}