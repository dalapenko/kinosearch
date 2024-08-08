package tech.dalapenko.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import tech.dalapenko.core.database.dao.KinoDao
import tech.dalapenko.core.database.dbo.CountryDbo
import tech.dalapenko.core.database.dbo.GenreDbo
import tech.dalapenko.core.database.dbo.PremiereCountryDbo
import tech.dalapenko.core.database.dbo.PremiereDbo
import tech.dalapenko.core.database.dbo.PremiereGenreDbo
import tech.dalapenko.core.database.dbo.ReleaseCountryDbo
import tech.dalapenko.core.database.dbo.ReleaseDbo
import tech.dalapenko.core.database.dbo.ReleaseGenreDbo

@Database(
    entities = [
        ReleaseDbo::class,
        ReleaseCountryDbo::class,
        ReleaseGenreDbo::class,
        PremiereDbo::class,
        PremiereCountryDbo::class,
        PremiereGenreDbo::class,
        CountryDbo::class,
        GenreDbo::class
    ],
    version = 1,
    exportSchema = false
)
abstract class KinoDatabase : RoomDatabase() {

    abstract fun getKinoDao(): KinoDao

    companion object {

        @Volatile
        private var instance: KinoDatabase? = null

        fun getInstance(context: Context): KinoDatabase {
            if (instance == null) {
                synchronized(this) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        KinoDatabase::class.java,
                        DATABASE_NAME
                    ).build()
                }
            }

            return instance!!
        }
    }
}

private const val DATABASE_NAME = "kinosearch_database"
