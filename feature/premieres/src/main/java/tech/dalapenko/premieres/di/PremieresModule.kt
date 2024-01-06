package tech.dalapenko.premieres.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import tech.dalapenko.premieres.model.repository.PremiereRepository
import tech.dalapenko.premieres.model.repository.PremiereRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
interface PremieresModule {

    @Binds
    fun bindPremiereRepository(repository: PremiereRepositoryImpl): PremiereRepository
}