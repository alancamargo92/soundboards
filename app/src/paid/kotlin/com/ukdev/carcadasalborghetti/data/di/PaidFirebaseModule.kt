package com.ukdev.carcadasalborghetti.data.di

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PaidFirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseStorageRoot(): StorageReference = Firebase.storage.reference.root
}
