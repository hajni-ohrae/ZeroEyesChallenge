package biz.ohrae.challenge_repo.di

import biz.ohrae.challenge_repo.data.remote.ApiService
import biz.ohrae.challenge_repo.data.remote.ApiServiceForBase
import biz.ohrae.challenge_repo.ui.challengers.ChallengersRepo
import biz.ohrae.challenge_repo.ui.detail.ChallengeDetailRepo
import biz.ohrae.challenge_repo.ui.login.LoginRepo
import biz.ohrae.challenge_repo.ui.main.ChallengeMainRepo
import biz.ohrae.challenge_repo.ui.main.UserRepo
import biz.ohrae.challenge_repo.ui.mychallenge.MyChallengeRepo
import biz.ohrae.challenge_repo.ui.participation.ParticipationRepo
import biz.ohrae.challenge_repo.ui.register.RegisterRepo
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

    @Provides
    @ViewModelScoped
    fun provideRegisterRepo(
        apiService: ApiService,
        gson: Gson,
        prefs: SharedPreference
    ): RegisterRepo {
        return RegisterRepo(apiService, gson, prefs)
    }

    @Provides
    @ViewModelScoped
    fun provideChallengeDetailRepo(
        apiService: ApiService,
        gson: Gson,
        prefs: SharedPreference
    ): ChallengeDetailRepo {
        return ChallengeDetailRepo(apiService, gson, prefs)
    }

    @Provides
    @ViewModelScoped
    fun provideParticipationRepo(
        apiService: ApiService,
        gson: Gson,
        prefs: SharedPreference
    ): ParticipationRepo {
        return ParticipationRepo(apiService, gson, prefs)
    }

    @Provides
    @ViewModelScoped
    fun provideChallengersRepo(
        apiService: ApiService,
        gson: Gson,
        prefs: SharedPreference
    ): ChallengersRepo {
        return ChallengersRepo(apiService, gson, prefs)
    }

    @Provides
    @ViewModelScoped
    fun provideMyChallengeRepo(
        apiService: ApiService,
        gson: Gson,
        prefs: SharedPreference
    ): MyChallengeRepo {
        return MyChallengeRepo(apiService, gson, prefs)
    }

    @Provides
    @ViewModelScoped
    fun provideLoginRepo(
        apiService: ApiServiceForBase,
        gson: Gson,
        prefs: SharedPreference
    ): LoginRepo {
        return LoginRepo(apiService, gson, prefs)
    }
}
