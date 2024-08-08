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

@Entity(tableName = Table.RELEASES)
@TypeConverters(DateConverter::class)
data class ReleaseDbo(
    @PrimaryKey @ColumnInfo(name="release_id") val id: Int,
    @ColumnInfo(name = "name_ru") val nameRu: String?,
    @ColumnInfo(name = "name_en") val nameEn: String?,
    @ColumnInfo(name = "year") val year: Int?,
    @ColumnInfo(name = "poster_url") val posterUrl: String?,
    @ColumnInfo(name = "poster_url_preview") val posterUrlPreview: String?,
    @ColumnInfo(name = "rating") val rating: Float?,
    @ColumnInfo(name = "rating_vote_count") val ratingVoteCount: Int?,
    @ColumnInfo(name = "expectations_rating") val expectationsRating: Float?,
    @ColumnInfo(name = "expectations_rating_vote_count") val expectationsRatingVoteCount: Int?,
    @ColumnInfo(name = "duration") val duration: Int?,
    @ColumnInfo(name = "releaseDate") val releaseDate: Date?
)

@Entity(
    tableName = Table.RELEASES_COUNTRIES,
    primaryKeys = ["release_id", "country"],
    foreignKeys = [
        ForeignKey(
            entity = ReleaseDbo::class,
            parentColumns = ["release_id"],
            childColumns = ["release_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CountryDbo::class,
            parentColumns = ["country"],
            childColumns = ["country"]
        )
    ]
)
data class ReleaseCountryDbo(
    @ColumnInfo(name = "release_id") val releaseId: Int,
    @ColumnInfo(name = "country") val country: String
)

@Entity(
    tableName = Table.RELEASES_GENRES,
    primaryKeys = ["release_id", "genre"],
    foreignKeys = [
        ForeignKey(
            entity = ReleaseDbo::class,
            parentColumns = ["release_id"],
            childColumns = ["release_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = GenreDbo::class,
            parentColumns = ["genre"],
            childColumns = ["genre"]
        )
    ]
)
data class ReleaseGenreDbo(
    @ColumnInfo(name = "release_id") val releaseId: Int,
    @ColumnInfo(name = "genre") val genre: String
)

data class FullReleaseDataDbo(
    @Embedded val release: ReleaseDbo,
    @Relation(
        parentColumn = "release_id",
        entityColumn = "genre",
        associateBy = Junction(value = ReleaseGenreDbo::class)
    )
    val genresList : List<GenreDbo>,
    @Relation(
        parentColumn = "release_id",
        entityColumn = "country",
        associateBy = Junction(value = ReleaseCountryDbo::class)
    )
    val countriesList : List<CountryDbo>
)