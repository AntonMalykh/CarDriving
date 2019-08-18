package com.antonm.cardriving

import android.graphics.Matrix
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.withSign

class TankOnBoardComputer(initialDirection: FloatArray,
                          private val maxRotationAngle: Float): OnBoardComputer(initialDirection) {

    override fun changeDirectory(request: DirectionChangeRequest): AppliedDirectionChange {
        if (abs(request.angle) > 1)
            return AppliedDirectionChange(
                processRotation(min(abs(request.angle), maxRotationAngle).withSign(request.angle)),
                0f,
                0f
            )

        val translation = processTranslation()
        return AppliedDirectionChange(0f, translation[0], translation[1])
    }

    private fun processRotation(angle: Float): Float {
        val m = Matrix()
        m.setRotate(angle, directionVect[0], directionVect[1])
        m.mapPoints(directionVect)
        return angle
    }

    private fun processTranslation(): FloatArray{
        val ddx = (directionVect[2] - directionVect[0]) / 10f
        val ddy = (directionVect[3] - directionVect[1]) / 10f
        val m = Matrix()
        m.setTranslate(ddx, ddy)
        m.mapPoints(directionVect)
        return floatArrayOf(ddx, ddy)
    }
}