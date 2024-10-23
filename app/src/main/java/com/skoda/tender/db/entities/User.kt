package com.skoda.tender.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class User {
    @PrimaryKey
    var id: Int = 0
    var name: String = ""
    var status: String = ""
}
