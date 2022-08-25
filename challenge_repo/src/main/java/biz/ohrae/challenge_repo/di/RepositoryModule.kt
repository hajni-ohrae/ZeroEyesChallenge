package biz.ohrae.challenge_repo.di

import biz.ohrae.challenge_repo.data.remote.ApiService
import biz.ohrae.challenge_repo.ui.main.ChallengeMainRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    @ViewModelScoped
    fun provideChallengeMainRepo(
        apiService: ApiService,
    ): ChallengeMainRepo {
        return ChallengeMainRepo(apiService)
    }
}
