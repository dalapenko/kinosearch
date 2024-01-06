package tech.dalapenko.releases.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import tech.dalapenko.releases.model.repository.ReleaseRepository
import tech.dalapenko.releases.model.repository.ReleaseRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
interface ReleasesModule {

    @Binds
    fun bindReleasesRepository(repository: ReleaseRepositoryImpl): ReleaseRepository
}