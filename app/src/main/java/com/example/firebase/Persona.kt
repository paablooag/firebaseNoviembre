package com.example.firebase

import java.io.Serializable

data class Persona(
    var nombre: String?=null,
    var apellido1: String?=null,
    var apellido2: String?=null,
    var email: String?=null,
    var telefono: String?=null,
    var edad: Int?=null
): Serializable
