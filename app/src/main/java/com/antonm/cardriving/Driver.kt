package com.antonm.cardriving

import com.antonm.cardriving.vehicles.Vehicle
import kotlin.math.abs


class Driver(private var destinationReachedThreshold: Int) : Vehicle.DirectionObserver {

    private var vehicle: Vehicle? = null
    private var destinationReachedCallback: (() -> Unit)? = null
    private var destinationPoint: FloatArray? = null

    override fun onVehicleDirectionUpdated() {
        driveVehicleToDestination(vehicle!!, destinationPoint!!)
    }

    fun setVehicle(vehicle: Vehicle) {
        if (this.vehicle === vehicle)
            return
        if (this.vehicle?.isMoving() == true)
            throw UnsupportedOperationException("I'm not a stuntman!")

        this.vehicle?.setDirectionObserver(null)
        this.vehicle = vehicle
        vehicle.setDirectionObserver(this)
    }

    fun setOnDestinationReachedCallback(callback: (() -> Unit)?) {
        this.destinationReachedCallback = callback
    }

    fun onDestinationPointSelected(x: Float, y: Float): Boolean {
        if (vehicle == null)
            throw UnsupportedOperationException("I'm not a pedestrian!")

        vehicle?.let { veh ->
            if (veh.isMoving())
                return false
            destinationPoint = floatArrayOf(x, y)
            destinationPoint?.let {
                driveVehicleToDestination(veh, it)
            }
            return true
        }
        return false
    }

    private fun driveVehicleToDestination(vehicle: Vehicle, destinationPoint: FloatArray) {
        if (!vehicle.isMoving()) {
            vehicle.startMoving()
            return
        }

        val currentDirection = vehicle.getOnBoardComputer()

        val distanceTo = abs(currentDirection.distanceToPoint(destinationPoint))
        if (distanceTo <= destinationReachedThreshold) {
            vehicle.stopMoving()
            return
        }

        val angle = currentDirection.angleToPoint(destinationPoint)

        vehicle.move(angle, distanceTo/destinationReachedThreshold/10)
    }
}