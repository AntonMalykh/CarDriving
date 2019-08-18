package com.antonm.cardriving.computers

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
        return AppliedDirectionChange(
            0f,
            translation[0],
            translation[1]
        )
    }

    private fun processRotation(angle: Float): Float {
        val m = Matrix()
        m.setRotate(angle, directionVect[0], directionVect[1])
        m.mapPoints(directionVect)
        return angle
    }

    private fun processTranslation(): FloatArray{
        val dx = (directionVect[2] - directionVect[0]) / 10f
        val dy = (directionVect[3] - directionVect[1]) / 10f
        val m = Matrix()
        m.setTranslate(dx, dy)
        m.mapPoints(directionVect)
        return floatArrayOf(dx, dy)
    }
}