package com.kirillmesh.vknewsclient.di

import androidx.lifecycle.ViewModel
import com.kirillmesh.vknewsclient.presentation.viewmodels.FeedPostsViewModel
import com.kirillmesh.vknewsclient.presentation.viewmodels.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(FeedPostsViewModel::class)
    @Binds
    fun bindFeedPostsViewModel(vm: FeedPostsViewModel): ViewModel

    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindMainViewModel(vm: MainViewModel): ViewModel
}