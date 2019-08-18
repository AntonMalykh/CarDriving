package com.antonm.cardriving.computers

import android.graphics.Matrix
import java.lang.Math.toRadians
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.withSign


class AutomobileOnBoardComputer(initialDirection: FloatArray,
                                private val minAcceleration: Float,
                                private val maxWheelRotation: Float) : OnBoardComputer(initialDirection) {

    private var acceleration = minAcceleration

    override fun changeDirectory(request: DirectionChangeRequest): AppliedDirectionChange {
        val ang = min(abs(request.angle), maxWheelRotation).withSign(request.angle)
        acceleration += request.acceleration * Math.cos(toRadians(ang.toDouble())).toFloat()
        val angle = processRotation(ang)
        val translations = processTranslation(acceleration)
        return AppliedDirectionChange(
            angle,
            translations[0],
            translations[1]
        )
    }


    override fun onVehicleStopped() {
        super.onVehicleStopped()
        acceleration = minAcceleration
    }

    private fun processRotation(angle: Float): Float {
        val m = Matrix()
        m.setRotate(angle, directionVect[0], directionVect[1])
        m.mapPoints(directionVect)
        return angle
    }

    private fun processTranslation(acceleration: Float): FloatArray{
        val dx = (directionVect[2] - directionVect[0]) / 50f * acceleration
        val dy = (directionVect[3] - directionVect[1]) / 50f * acceleration
        val m = Matrix()
        m.setTranslate(dx, dy)
        m.mapPoints(directionVect)
        return floatArrayOf(dx, dy)
    }


}
