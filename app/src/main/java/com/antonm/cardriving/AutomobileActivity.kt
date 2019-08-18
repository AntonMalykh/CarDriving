package com.antonm.cardriving

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import kotlinx.android.synthetic.main.activity_automobile.*

class AutomobileActivity : AppCompatActivity() {

    private lateinit var driver: Driver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_automobile)

        driver = Driver(100).apply {
            setVehicle(vehicle)
            setOnDestinationReachedCallback { map.clearMark() }
        }
        reset.setOnClickListener {
            startActivity(Intent(this, AutomobileActivity::class.java))
            finish()
        }

        change_vehicle.setOnClickListener {
            startActivity(Intent(this, TankActivity::class.java))
            finish()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return event?.let {
            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (driver.onDestinationPointSelected(event.x, event.y))
                        map.setMark(event.x, event.y)
                    true
                }
                else -> super.onTouchEvent(event)
            }
        }
            ?: super.onTouchEvent(event)
    }
}
