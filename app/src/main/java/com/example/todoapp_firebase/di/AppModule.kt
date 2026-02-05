package com.example.todoapp_firebase.di

import com.example.todoapp_firebase.data.repository.AuthRepository
import com.example.todoapp_firebase.data.repository.AuthRepositoryImpl
import com.example.todoapp_firebase.data.repository.TaskRepository
import com.example.todoapp_firebase.data.repository.TaskRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/** Gemini início
 *
 * Prompt: Crie um objeto AppModule anotado com @Module e @InstallIn(SingletonComponent::class) para prover as dependências do Hilt.
 * Crie funções @Provides @Singleton para retornar instâncias de FirebaseAuth e FirebaseFirestore.
 * Crie funções @Provides @Singleton para retornar as interfaces AuthRepository e TaskRepository, recebendo suas implementações (AuthRepositoryImpl e TaskRepositoryImpl).
 *
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

    @Provides
    @Singleton
    fun provideTaskRepository(impl: TaskRepositoryImpl): TaskRepository = impl
}
/** Gemini final */