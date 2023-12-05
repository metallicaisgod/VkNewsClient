package com.kirillmesh.vknewsclient.di

import androidx.lifecycle.ViewModel
import com.kirillmesh.vknewsclient.presentation.viewmodels.CommentsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface CommentsViewModelModule {

    @IntoMap
    @ViewModelKey(CommentsViewModel::class)
    @Binds
    fun bindCommentsViewModel(vm: CommentsViewModel) : ViewModel
}