package com.bonioctavianus.android.shopping_app.di.repository

import com.bonioctavianus.android.shopping_app.ShopApplication
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AuthRepositoryModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideFacebookCallbackManager(): CallbackManager {
        return CallbackManager.Factory.create()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideGoogleSignInOptions(): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideGoogleSignInClient(
        application: ShopApplication,
        signInOptions: GoogleSignInOptions
    ): GoogleSignInClient {
        return GoogleSignIn.getClient(application, signInOptions)
    }
}