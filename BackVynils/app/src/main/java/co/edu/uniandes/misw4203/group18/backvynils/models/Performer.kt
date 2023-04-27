package co.edu.uniandes.misw4203.group18.backvynils.models

abstract class Performer(
    open val performerId: Int,
    open val name: String,
    open val image: String,
    open val description: String
)