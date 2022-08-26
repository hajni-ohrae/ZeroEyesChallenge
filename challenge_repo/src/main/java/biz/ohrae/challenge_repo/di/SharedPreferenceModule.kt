package biz.ohrae.challenge_repo.di

import android.content.Context
import android.content.SharedPreferences
import biz.ohrae.challenge_repo.util.prefs.SharedPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferenceModule {
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences? {
        return context.getSharedPreferences(SharedPreference.NAME, Context.MODE_PRIVATE)
    }
}