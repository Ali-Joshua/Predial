package com.example.predio

class Property (
                val extension: Double,
                val zone: Zona
) {

    fun tax():Double {
        return (extension * this.zone.cost)
    }
}