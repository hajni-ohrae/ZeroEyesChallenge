package biz.ohrae.challenge.di

import biz.ohrae.challenge.glideutils.MyGlideModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GlideModule {
    @Provides
    @Singleton
    fun provideMyAppGlideModule(): MyGlideModule {
        return MyGlideModule()
    }
}