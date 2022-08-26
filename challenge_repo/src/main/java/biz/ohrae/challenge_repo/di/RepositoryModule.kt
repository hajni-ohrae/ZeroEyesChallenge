package biz.ohrae.challenge_repo.di

import biz.ohrae.challenge_repo.data.remote.ApiService
import biz.ohrae.challenge_repo.ui.main.ChallengeMainRepo
import biz.ohrae.challenge_repo.ui.main.UserRepo
import biz.ohrae.challenge_repo.util.prefs.SharedPreference
import com.google.gson.Gson
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
        gson: Gson,
        prefs: SharedPreference
    ): ChallengeMainRepo {
        return ChallengeMainRepo(apiService, gson, prefs)
    }

    @Provides
    @ViewModelScoped
    fun provideUserRepo(
        apiService: ApiService,
        gson: Gson,
        prefs: SharedPreference
    ): UserRepo {
        return UserRepo(apiService, gson, prefs)
    }
}
