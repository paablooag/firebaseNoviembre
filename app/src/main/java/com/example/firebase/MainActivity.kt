package com.example.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var datos:TextView
    private lateinit var crear:Button
    private lateinit var modificar:Button
    private lateinit var eliminar:Button
    private lateinit var ver:Button


    private lateinit var nueva_persona:Persona
    private lateinit var referencia:DatabaseReference
    private lateinit var identificador:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        datos=findViewById(R.id.datos) as TextView
        crear=findViewById(R.id.botonCrear) as Button
        modificar=findViewById(R.id.botonModificar) as Button
        eliminar=findViewById(R.id.botonEliminar) as Button
        ver = findViewById(R.id.botonVer) as Button

        referencia=FirebaseDatabase.getInstance().getReference()

        crear.setOnClickListener(View.OnClickListener { view:View? ->
            datos.setText("Datos creados")
            identificador= referencia.child("personas").push().key!!
            nueva_persona = Persona("Pepe", "Chochito", "Pitorro", "pepe@gmail.com", "666183910", 45)

            referencia.child("personas").child(identificador).setValue(nueva_persona)

        })

        eliminar.setOnClickListener(View.OnClickListener { view:View? ->
            datos.setText("Datos borrados")
//            referencia.child("personas").child("-NjrXHtWL3fXKi4ITQMj").removeValue()
            referencia.child("personas").child(identificador).removeValue()

        })
        modificar.setOnClickListener(View.OnClickListener { view: View? ->
            datos.setText("Datos modificados")

            //Primera forma
            referencia.child("personas").child(identificador).child("nombre").setValue("Perico")
            referencia.child("personas").child(identificador).child("telefono").setValue("671283182")
            //Segunda forma
            nueva_persona.nombre="Maria"
        })

        ver.setOnClickListener { view: View? ->
            var res:String = ""
            referencia.child("personas").addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach { hijo: DataSnapshot? ->
                        val pojo_persona = hijo?.getValue(Persona::class.java)
                        res+="\n"+
                                pojo_persona?.nombre+ "\n" +
                                pojo_persona?.apellido1 + "\n" +
                                pojo_persona?.apellido2 + "\n" +
                                pojo_persona?.email+ "\n" + pojo_persona?.edad + "\n" +
                                pojo_persona?.telefono

                    }
                    datos.setText("Los datos son: "+res)
                }

                override fun onCancelled(error: DatabaseError) {
                    println(error.message)
                }

            })
            datos.setText("")
        }

    }

}
