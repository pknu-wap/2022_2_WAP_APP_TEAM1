package com.example.witt.di

import com.example.witt.data.source.local.user_profile.ProfileDataSource
import com.example.witt.data.source.local.user_profile.ProfileDataSourceImpl
import com.example.witt.data.source.remote.signup.duplicate_check.DuplicateEmailDataSource
import com.example.witt.data.source.remote.signup.duplicate_check.DuplicateEmailDataSourceImpl
import com.example.witt.data.source.remote.detail_plan.memo.EditMemoDataSource
import com.example.witt.data.source.remote.detail_plan.memo.EditMemoDataSourceImpl
import com.example.witt.data.source.remote.detail_plan.memo.MakeMemoDataSource
import com.example.witt.data.source.remote.detail_plan.memo.MakeMemoDataSourceImpl
import com.example.witt.data.source.remote.detail_plan.place.AddPlaceDataSource
import com.example.witt.data.source.remote.detail_plan.place.AddPlaceDataSourceImpl
import com.example.witt.data.source.remote.plan.get_plan.GetPlanDataSource
import com.example.witt.data.source.remote.plan.get_plan.GetPlanDataSourceImpl
import com.example.witt.data.source.remote.plan.get_plan.GetPlanListDataSource
import com.example.witt.data.source.remote.plan.get_plan.GetPlanListDataSourceImpl
import com.example.witt.data.source.remote.plan.make_plan.MakePlanDataSource
import com.example.witt.data.source.remote.plan.make_plan.MakePlanDataSourceImpl
import com.example.witt.data.source.remote.plan.join_plan.JoinPlanDataSource
import com.example.witt.data.source.remote.plan.join_plan.JoinPlanDataSourceImpl
import com.example.witt.data.source.remote.plan.join_plan.OutPlanDataSource
import com.example.witt.data.source.remote.plan.join_plan.OutPlanDataSourceImpl
import com.example.witt.data.source.remote.user.profile.ProfileUploadDataSource
import com.example.witt.data.source.remote.user.profile.ProfileUploadDataSourceImpl
import com.example.witt.data.source.remote.signin.SignInDataSource
import com.example.witt.data.source.remote.signin.SignInDataSourceImpl
import com.example.witt.data.source.remote.signup.SignUpDataSource
import com.example.witt.data.source.remote.signup.SignUpDataSourceImpl
import com.example.witt.data.source.remote.signin.social_signin.SocialSignInDataSource
import com.example.witt.data.source.remote.signin.social_signin.SocialSignInDataSourceImpl
import com.example.witt.data.source.remote.signin.token_signin.TokenSignInDataSource
import com.example.witt.data.source.remote.signin.token_signin.TokenSignInDataSourceImpl
import com.example.witt.data.source.remote.user.GetUserInfoDataSource
import com.example.witt.data.source.remote.user.GetUserInfoDataSourceImpl
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


    @Binds
    @Singleton
    abstract fun provideJoinPlanDataSource(
        joinPlanDataSourceImpl: JoinPlanDataSourceImpl
    ): JoinPlanDataSource

    @Binds
    @Singleton
    abstract fun provideOutPlanDataSource(
        outPlanDataSourceImpl: OutPlanDataSourceImpl
    ): OutPlanDataSource

    @Binds
    @Singleton
    abstract fun provideAddPlaceDataSource(
        addPlaceDataSourceImpl : AddPlaceDataSourceImpl
    ): AddPlaceDataSource


    @Binds
    @Singleton
    abstract fun provideGetUserInfoDataSource(
        getUserInfoDataSourceImpl: GetUserInfoDataSourceImpl
    ): GetUserInfoDataSource
}