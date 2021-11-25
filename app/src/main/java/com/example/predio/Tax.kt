package com.example.predio

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.Month

class Tax (val folio: Int,
           val paymentDate: LocalDate,
           val listProperties: List<Property>,
           val owner:Person){

    data class Builder(val folio: Int, val paymentDate: LocalDate, val owner: Person){
        private val properties = ArrayList<Property>()
        fun addProperties(property: Property): Builder{
            properties.add(property)
            return this
        }

        fun addAllProperties(elements: ArrayList<Property>):Builder{
            properties.addAll(elements)
            return this
        }

        fun build()= Tax(folio,paymentDate,properties,owner)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun totalTax(): Double{
        var total= listProperties.map {
            it.tax()
        }.sum()

        if (owner.getAge()>=70 || owner.singleMother){
            total = when(paymentDate.month) {
                Month.JANUARY, Month.FEBRUARY -> total * 0.30
                else -> total * 0.50
            }
        }
        else if (paymentDate.month<= Month.FEBRUARY ){
            total = total * 0.60;
        }

        return (total)

    }
}