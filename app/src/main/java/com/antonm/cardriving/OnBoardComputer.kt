package com.antonm.cardriving

import kotlin.math.*

abstract class OnBoardComputer(initialDirection: FloatArray) {
    protected var directionVect: FloatArray = initialDirection.copyOf()

    abstract fun changeDirectory(request: DirectionChangeRequest): AppliedDirectionChange

    open fun onVehicleStopped() {
    }

    fun angleToPoint(point: FloatArray): Float {
        val dstVector = floatArrayOf(directionVect[0], directionVect[1], point[0], point[1])

        val vectorCoordinates = floatArrayOf(
            directionVect[2] - directionVect[0],
            directionVect[3] - directionVect[1]
        )

        val dstCoordinates = floatArrayOf(
            dstVector[2] - dstVector[0],
            dstVector[3] - dstVector[1]
        )

        val sign = -sign(vectorCoordinates[1] * dstCoordinates[0] - vectorCoordinates[0] * dstCoordinates[1])

        val dotProduct = (vectorCoordinates[0] * dstCoordinates[0]) + (vectorCoordinates[1] * dstCoordinates[1])

        var acosArgument = dotProduct / (vectorCoordinates.zeroBasedVectorLength() * dstCoordinates.zeroBasedVectorLength())
        if (acosArgument > 1)
            acosArgument = 1f

        val acos = acos(acosArgument)

        val result = sign * (acos / Math.PI * 180).toFloat()

        if (abs(result) == 180f) {
            return 179f * sign
        }
        return result
    }

    fun distanceToPoint(point: FloatArray): Float {
        return floatArrayOf(
            directionVect[0],
            directionVect[1],
            point[0],
            point[1]
        )
            .vectorLength()
    }

    private fun FloatArray.zeroBasedVectorLength(): Float {
        return floatArrayOf(0f, 0f, this[0], this[1]).vectorLength()
    }

    private fun FloatArray.vectorLength(): Float {
        val xabs = abs(this[2] - this[0])
        val yabs = abs(this[3] - this[1])
        return sqrt(xabs.pow(2) + yabs.pow(2))
    }

    class DirectionChangeRequest(val angle: Float, val acceleration: Float)
    class AppliedDirectionChange(val angleBy: Float, val translationXBy: Float, val translationYBy: Float)
}