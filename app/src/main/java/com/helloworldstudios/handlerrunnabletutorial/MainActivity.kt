package com.helloworldstudios.handlerrunnabletutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.helloworldstudios.handlerrunnabletutorial.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var milliseconds = 0
    private var isRunning = false
    private var lastStartTime = 0L
    private var runnable: Runnable = Runnable{}
    private var handler: Handler = Handler(Looper.myLooper()!!)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handler = Handler(Looper.getMainLooper())
        runnable = object : Runnable{
            override fun run() {
                if (isRunning){
                    milliseconds += 10
                    updateChronometer()
                }
                handler.postDelayed(runnable, 10)
            }
        }

        binding.btnStartStop.setOnClickListener {
            if (isRunning) {
                isRunning = false
                handler.removeCallbacks(runnable) // Stop the currently running runnable
                binding.btnStartStop.text = "Start"
                binding.btnReset.isEnabled = true
            } else {
                isRunning = true
                lastStartTime = System.currentTimeMillis() - milliseconds
                handler.post(runnable)
                binding.btnStartStop.text = "Stop"
                binding.btnReset.isEnabled = false
            }
        }

        binding.btnReset.setOnClickListener {
            isRunning = false
            milliseconds = 0
            updateChronometer()
            binding.btnStartStop.isEnabled = true
        }

    }

    private fun updateChronometer() {
        val currentTime = System.currentTimeMillis()
        //milliseconds = (currentTime - lastStartTime).toInt()

        val hours = milliseconds / 3600000
        val minutes = (milliseconds % 3600000) / 60000
        val seconds = (milliseconds % 60000) / 1000
        val millis = milliseconds % 1000
        val timeString = String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, millis)
        binding.tvTime.text = "Time : $timeString"
    }
}