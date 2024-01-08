package tech.dalapenko.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import tech.dalapenko.database.dao.KinoDao
import tech.dalapenko.database.dbo.CountryDbo
import tech.dalapenko.database.dbo.GenreDbo
import tech.dalapenko.database.dbo.PremiereCountryDbo
import tech.dalapenko.database.dbo.PremiereDbo
import tech.dalapenko.database.dbo.PremiereGenreDbo
import tech.dalapenko.database.dbo.ReleaseCountryDbo
import tech.dalapenko.database.dbo.ReleaseDbo
import tech.dalapenko.database.dbo.ReleaseGenreDbo

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
                        "kinosearch_database"
                    ).build()
                }
            }

            return instance!!
        }
    }
}