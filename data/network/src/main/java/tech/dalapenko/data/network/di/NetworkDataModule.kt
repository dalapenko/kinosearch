package tech.dalapenko.data.network.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import tech.dalapenko.core.basepresentation.network.NetworkConnectivityObserver
import tech.dalapenko.core.basepresentation.network.NetworkConnectivityObserverImpl

@Module
@InstallIn(SingletonComponent::class)
object NetworkDataModule {

    @Provides
    fun provideNetworkStatusObserver(
        @ApplicationContext context: Context
    ): NetworkConnectivityObserver {
        return NetworkConnectivityObserverImpl(context)
    }
}