package tech.dalapenko.core.database.dbo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres")
data class GenreDbo(
    @PrimaryKey @ColumnInfo("genre") val name: String
)