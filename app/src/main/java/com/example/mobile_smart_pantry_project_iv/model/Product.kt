package com.example.mobile_smart_pantry_project_iv.model
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: String,
    val nazwa: String,
    val ilosc: Int,
    val kategoria: String,
    val zdjecie: String
)
