package com.example.witt.di

import com.example.witt.data.source.local.user_profile.ProfileDataSource
import com.example.witt.data.source.local.user_profile.ProfileDataSourceImpl
import com.example.witt.data.source.remote.duplicate_check.DuplicateEmailDataSource
import com.example.witt.data.source.remote.duplicate_check.DuplicateEmailDataSourceImpl
import com.example.witt.data.source.remote.memo.EditMemoDataSource
import com.example.witt.data.source.remote.memo.EditMemoDataSourceImpl
import com.example.witt.data.source.remote.memo.MakeMemoDataSource
import com.example.witt.data.source.remote.memo.MakeMemoDataSourceImpl
import com.example.witt.data.source.remote.plan.get_plan.GetPlanDataSource
import com.example.witt.data.source.remote.plan.get_plan.GetPlanDataSourceImpl
import com.example.witt.data.source.remote.plan.get_plan.GetPlanListDataSource
import com.example.witt.data.source.remote.plan.get_plan.GetPlanListDataSourceImpl
import com.example.witt.data.source.remote.plan.make_plan.MakePlanDataSource
import com.example.witt.data.source.remote.plan.make_plan.MakePlanDataSourceImpl
import com.example.witt.data.source.remote.profile.ProfileUploadDataSource
import com.example.witt.data.source.remote.profile.ProfileUploadDataSourceImpl
import com.example.witt.data.source.remote.signin.SignInDataSource
import com.example.witt.data.source.remote.signin.SignInDataSourceImpl
import com.example.witt.data.source.remote.signup.SignUpDataSource
import com.example.witt.data.source.remote.signup.SignUpDataSourceImpl
import com.example.witt.data.source.remote.social_signin.SocialSignInDataSource
import com.example.witt.data.source.remote.social_signin.SocialSignInDataSourceImpl
import com.example.witt.data.source.remote.token_signin.TokenSignInDataSource
import com.example.witt.data.source.remote.token_signin.TokenSignInDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun provideSignUpDataSource(
        signUpDataSourceImpl: SignUpDataSourceImpl
    ): SignUpDataSource

    @Binds
    @Singleton
    abstract fun provideSignInDataSource(
        signInDataSourceImpl: SignInDataSourceImpl
    ): SignInDataSource


    @Binds
    @Singleton
    abstract fun provideDuplicateEmailDataSource(
        duplicateEmailDataSourceImpl: DuplicateEmailDataSourceImpl
    ): DuplicateEmailDataSource

    @Binds
    @Singleton
    abstract fun provideTokenDataSource(
        TokenDataSourceImpl: TokenSignInDataSourceImpl
    ): TokenSignInDataSource

    @Binds
    @Singleton
    abstract fun provideProfileDataSource(
        profileDataSourceImpl: ProfileDataSourceImpl
    ): ProfileDataSource

    @Binds
    @Singleton
    abstract fun provideProfileUploadDataSource(
        profileUploadDataSourceImpl: ProfileUploadDataSourceImpl
    ): ProfileUploadDataSource

    @Binds
    @Singleton
    abstract fun provideSocialSignInDataSource(
        socialSignInDataSourceImpl: SocialSignInDataSourceImpl
    ): SocialSignInDataSource

    @Binds
    @Singleton
    abstract fun providesMakePlanDataSource(
        makePlanDataSourceImpl: MakePlanDataSourceImpl
    ): MakePlanDataSource

    @Binds
    @Singleton
    abstract fun provideGetPlanListDataSource(
        getPlanListDataSourceImpl: GetPlanListDataSourceImpl
    ): GetPlanListDataSource

    @Binds
    @Singleton
    abstract fun providesGetPlanDataSource(
        getPlanDataSourceImpl: GetPlanDataSourceImpl
    ): GetPlanDataSource

    @Binds
    @Singleton
    abstract fun provideMakeMemoDataSource(
        makeMemoDataSourceImpl: MakeMemoDataSourceImpl
    ): MakeMemoDataSource

    @Binds
    @Singleton
    abstract fun provideEditMemoDataSource(
        editMemoDataSourceImpl: EditMemoDataSourceImpl
    ): EditMemoDataSource

}