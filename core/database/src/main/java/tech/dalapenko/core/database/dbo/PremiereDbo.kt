package tech.dalapenko.core.database.dbo

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import androidx.room.TypeConverters
import tech.dalapenko.core.database.Table
import tech.dalapenko.core.database.converter.DateConverter
import java.util.Date

@Entity(tableName = Table.PREMIERES)
@TypeConverters(DateConverter::class)
data class PremiereDbo(
    @PrimaryKey @ColumnInfo(name="premiere_id") val id: Int,
    @ColumnInfo(name = "name_ru") val nameRu: String?,
    @ColumnInfo(name = "name_en") val nameEn: String?,
    @ColumnInfo(name = "year") val year: Int?,
    @ColumnInfo(name = "poster_url") val posterUrl: String?,
    @ColumnInfo(name = "poster_url_preview") val posterUrlPreview: String?,
    @ColumnInfo(name = "duration") val duration: Int?,
    @ColumnInfo(name = "premiereDate") val premiereDate: Date?
)

@Entity(
    tableName = Table.PREMIERES_COUNTRIES,
    primaryKeys = ["premiere_id", "country"],
    foreignKeys = [
        ForeignKey(
            entity = PremiereDbo::class,
            parentColumns = ["premiere_id"],
            childColumns = ["premiere_id"]
        ),
        ForeignKey(
            entity = CountryDbo::class,
            parentColumns = ["country"],
            childColumns = ["country"]
        )
    ]
)
data class PremiereCountryDbo(
    @ColumnInfo(name = "premiere_id") val premiereId: Int,
    @ColumnInfo(name = "country") val country: String
)

@Entity(
    tableName = Table.PREMIERES_GENRES,
    primaryKeys = ["premiere_id", "genre"],
    foreignKeys = [
        ForeignKey(
            entity = PremiereDbo::class,
            parentColumns = ["premiere_id"],
            childColumns = ["premiere_id"]
        ),
        ForeignKey(
            entity = GenreDbo::class,
            parentColumns = ["genre"],
            childColumns = ["genre"]
        )
    ]
)
data class PremiereGenreDbo(
    @ColumnInfo(name = "premiere_id") val premierId: Int,
    @ColumnInfo(name = "genre") val genre: String
)

data class FullPremiereDataDbo(
    @Embedded val premiere: PremiereDbo,
    @Relation(
        parentColumn = "premiere_id",
        entityColumn = "genre",
        associateBy = Junction(value = PremiereGenreDbo::class)
    )
    val genresList : List<GenreDbo>,
    @Relation(
        parentColumn = "premiere_id",
        entityColumn = "country",
        associateBy = Junction(value = PremiereCountryDbo::class)
    )
    val countriesList : List<CountryDbo>
)