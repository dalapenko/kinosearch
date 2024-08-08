package tech.dalapenko.core.database.dbo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import tech.dalapenko.core.database.Table

@Entity(tableName = Table.COUNTRIES)
data class CountryDbo(
    @PrimaryKey @ColumnInfo("country") val name: String
)