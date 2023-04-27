package co.edu.uniandes.misw4203.group18.backvynils.models

import java.util.Date

data class Musician (
    override val performerId: Int,
    override val name: String,
    override val image: String,
    override val description: String,
    val birthDate: String
): Performer (performerId,name,image,description)