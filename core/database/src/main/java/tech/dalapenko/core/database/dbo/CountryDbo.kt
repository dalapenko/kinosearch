package tech.dalapenko.core.database.dbo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class CountryDbo(
    @PrimaryKey @ColumnInfo("country") val name: String
)