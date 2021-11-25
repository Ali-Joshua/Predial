package com.example.predio

import java.time.LocalDate

class Person (val fullName:String,
              val birthDate: LocalDate,
              val genre: String,
              val singleMother:Boolean){

    fun getAge(): Int {
        var age= LocalDate.now().year - this.birthDate.year
        if ( (LocalDate.now().month==this.birthDate.month &&
                    LocalDate.now().dayOfMonth <this.birthDate.dayOfMonth) ||
            LocalDate.now().month<this.birthDate.month)
            age = age -1;
        return (age)
    }
}