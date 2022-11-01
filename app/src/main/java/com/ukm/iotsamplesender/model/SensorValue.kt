package com.ukm.iotsamplesender.model

import com.google.firebase.firestore.PropertyName

data class SensorValue(
    @PropertyName("sensorValue")
    val sensorValue: Double = 0.0
)
