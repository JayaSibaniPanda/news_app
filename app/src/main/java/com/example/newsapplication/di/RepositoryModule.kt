package com.example.newsapplication.di

import com.example.newsapplication.repository.RemoteDS
import com.example.newsapplication.repository.RemoteDSImpl
import com.example.newsapplication.repository.Repo
import com.example.newsapplication.repository.RepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepoModule {
    @Binds
    abstract fun bindRemoteDS(remoteDsImpl: RemoteDSImpl): RemoteDS

    @Binds
    abstract fun bindRepo(repoImpl: RepoImpl): Repo
}