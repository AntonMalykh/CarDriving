package com.antonm.cardriving.vehicles

import android.content.Context
import android.util.AttributeSet
import com.antonm.cardriving.computers.OnBoardComputer
import com.antonm.cardriving.computers.TankOnBoardComputer

class TankView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0)
    : VehicleView<TankOnBoardComputer>(context, attrs, defStyleAttr) {

    private val MAX_ROTATION_ANGLE = 3f

    override fun move(rotation: Float, acceleration: Float) {
        val result = getOnBoardComputer().changeDirectory(
            OnBoardComputer.DirectionChangeRequest(rotation, acceleration)
        )

        this.rotation += result.angleBy
        translationX += result.translationXBy
        translationY += result.translationYBy

        super.move(rotation, acceleration)
    }

    override fun instantiateComputer(): TankOnBoardComputer {
        return TankOnBoardComputer(
            floatArrayOf(
                left + (right - left) / 2f,
                top + (bottom - top) / 2f,
                left + (right - left) / 2f,
                top.toFloat()
            ),
            MAX_ROTATION_ANGLE
        )
    }

}