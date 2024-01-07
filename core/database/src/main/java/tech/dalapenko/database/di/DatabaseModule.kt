package tech.dalapenko.database.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import tech.dalapenko.database.KinoDatabase
import tech.dalapenko.database.dao.KinoDao

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): KinoDatabase {
        return KinoDatabase.getInstance(context)
    }

    @Provides
    fun provideKinoDao(
        database: KinoDatabase
    ): KinoDao {
        return database.getKinoDao()
    }

}