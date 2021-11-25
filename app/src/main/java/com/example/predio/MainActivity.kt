package com.example.predio

import android.os.Build
import android.os.Build.VERSION_CODES
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.predio.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.text.DecimalFormat
import java.time.LocalDate

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    var predios = ArrayList<Property>()
    var sexo: String = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val js = resources.openRawResource(R.raw.zonas)
        val jsonText =
            js.use { js ->
                js.bufferedReader().use(BufferedReader::readText)
            }
        val gson = Gson()
        val tipoZona = object : TypeToken<List<Zona>>() {}.type
        var zonas = gson.fromJson<List<Zona>>(jsonText, tipoZona)
        val adapter = ArrayAdapter(
            this, android.R.layout.simple_dropdown_item_1line, zonas
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spZonas.setSelection(0, true)
        binding.spZonas.setAdapter(adapter)

        binding.btnCalcular.setEnabled(false)
        binding.btnAgregarPredio.setOnClickListener({
            val zona: Zona = binding.spZonas.selectedItem as Zona
            val predio = Property(zone= zona,
                extension = binding.etExtensionPredio.text.toString().toDouble())
            predios.add(predio)
            Toast.makeText(this, "Predio agregado", Toast.LENGTH_LONG).show()
            binding.etExtensionPredio.setText("0")
            binding.etExtensionPredio.requestFocus()

            binding.predios.setText(predios.toList().toString())
            binding.btnCalcular.setEnabled(true)
            //binding.btnCalcular.setOnClickListener {
            val person = Person(
                fullName = binding.etNombre.text.toString(),
                birthDate = LocalDate.parse(binding.etFechaNacimiento.text.toString()),
                genre = sexo,
                singleMother = binding.chkMadreSoltera.isChecked
            )
            val tax = Tax.Builder(folio = 1, paymentDate = LocalDate.now(), owner = person)
                .addAllProperties(predios)
                .build()

            val decimalFormat = DecimalFormat("#,###.00")

            Toast.makeText(
                this,
                "El impuesto a pagar de ${person.fullName}" +
                        "es de: $${decimalFormat.format(tax.totalTax())}", Toast.LENGTH_LONG
            ).show()
            predios.clear()
        })
    }
}
/*
    lateinit var sexo:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.gender.setOnCheckedChangeListener(
            object: RadioGroup.OnCheckedChangeListener{
                override fun onCheckedChanged(group: RadioGroup, checkedId:Int){

                    sexo= when (checkedId) {
                        2131296756 -> "F"
                        else ->"M"
                    }
                    Log.e("radio", checkedId.toString())
                }
            })
        Toast.makeText(this,sexo.toString(),Toast.LENGTH_LONG).show()
        binding.btnPagoTotal.setOnClickListener {
            val name=binding.etFullName.text.toString()
            Toast.makeText(this,name, Toast.LENGTH_LONG).show()
            val date=binding.etDate.text.toString()
            Toast.makeText(this,date,Toast.LENGTH_LONG).show()


            val single = binding.sMom.isChecked
            Toast.makeText(this,single.toString(),Toast.LENGTH_LONG).show()
            val zone=binding.lZone .isActivated
            Toast.makeText(this,zone.toString(),Toast.LENGTH_LONG).show()


        }
    }
Se desea diseñar una aplicación que permita calcular el importe total que una persona
debe pagar por el impuesto predial, considerando que una persona puede tener varios
predios. El costo de cada predio está en función a la zona de ubicación, y para ello
se cuenta con un catálogo de zonas.
El gobierno municipal está implementando el siguiente programa:
Para las personas mayores o iguales de 70 años o madre solteras tiene un 70% de
descuento si los pagos se realizan en los meses de Enero y Febrero, y de un 50% en los
siguientes meses.
Para el resto de la población hay un descuento del 40% en los meses de Enero y Febrero.
 */