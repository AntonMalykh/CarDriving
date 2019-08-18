package com.antonm.cardriving.vehicles

import com.antonm.cardriving.computers.OnBoardComputer

interface Vehicle {
    fun isMoving(): Boolean
    fun startMoving()
    fun setDirectionObserver(observer: DirectionObserver?)
    fun move(rotation: Float, acceleration: Float)
    fun getOnBoardComputer(): OnBoardComputer
    fun stopMoving()

    interface DirectionObserver {
        fun onVehicleDirectionUpdated()
    }
}