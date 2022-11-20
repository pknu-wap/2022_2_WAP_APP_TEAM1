package com.example.witt.di

import android.content.SharedPreferences
import com.example.witt.data.repository.AuthRepositoryImpl
import com.example.witt.data.repository.PlanRepositoryImpl
import com.example.witt.data.repository.UserRepositoryImpl
import com.example.witt.data.source.local.user_profile.ProfileDataSource
import com.example.witt.data.source.remote.duplicate_check.DuplicateEmailDataSource
import com.example.witt.data.source.remote.memo.MakeMemoDataSource
import com.example.witt.data.source.remote.plan.get_plan.GetPlanDataSource
import com.example.witt.data.source.remote.plan.get_plan.GetPlanListDataSource
import com.example.witt.data.source.remote.plan.make_plan.MakePlanDataSource
import com.example.witt.data.source.remote.profile.ProfileUploadDataSource
import com.example.witt.data.source.remote.signin.SignInDataSource
import com.example.witt.data.source.remote.signup.SignUpDataSource
import com.example.witt.data.source.remote.social_signin.SocialSignInDataSource
import com.example.witt.data.source.remote.token_signin.TokenSignInDataSource
import com.example.witt.domain.repository.AuthRepository
import com.example.witt.domain.repository.PlanRepository
import com.example.witt.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(signInDataSource : SignInDataSource,
                              signUpDataSource: SignUpDataSource,
                              tokenSignInDataSource: TokenSignInDataSource,
                              socialSignInDataSource: SocialSignInDataSource,
                              prefs: SharedPreferences): AuthRepository{
        return AuthRepositoryImpl(signInDataSource, signUpDataSource,tokenSignInDataSource,
            socialSignInDataSource, prefs)
    }

    @Provides
    @Singleton
    fun provideUserRepository(duplicateEmailDataSource: DuplicateEmailDataSource,
                              profileDataSource: ProfileDataSource,
                              profileUploadDataSource: ProfileUploadDataSource
    ): UserRepository{
        return UserRepositoryImpl(duplicateEmailDataSource, profileDataSource,profileUploadDataSource)
    }

    @Provides
    @Singleton
    fun providePlanRepository(makePlanDataSource: MakePlanDataSource,
                              getPlanListDataSource: GetPlanListDataSource,
                              getPlanDataSource: GetPlanDataSource,
                              makeMemoDataSource: MakeMemoDataSource
    ): PlanRepository{
        return PlanRepositoryImpl(makePlanDataSource, getPlanListDataSource, getPlanDataSource,makeMemoDataSource)
    }

}