package co.edu.uniandes.misw4203.group18.backvynils.models

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

data class Band (
    override val performerId: Int,
    override val name: String,
    override val image: String,
    override val description: String,
    val creationDate: String
): Performer (performerId,name,image,description)