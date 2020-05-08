package com.bonioctavianus.android.shopping_app.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bonioctavianus.android.shopping_app.R
import com.bonioctavianus.android.shopping_app.repository.auth.GoogleAuthService.Companion.GOOGLE_SIGN_IN_RC
import com.bonioctavianus.android.shopping_app.ui.Navigator
import com.bonioctavianus.android.shopping_app.ui.base.BaseFragment
import com.bonioctavianus.android.shopping_app.utils.SingleLiveEvent
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_auth.*
import timber.log.Timber
import javax.inject.Inject


class AuthFragment : BaseFragment<AuthIntent, AuthViewState>() {

    @Inject
    lateinit var mNavigator: Navigator
    @Inject
    lateinit var mViewModel: AuthViewModel

    private val mSubject: PublishSubject<AuthIntent> = PublishSubject.create()

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component_auth.mFragment = this
        component_auth.mSignInSuccessHandler = { mNavigator.navigateToMainFragment(this) }
    }

    override fun intents(): Observable<AuthIntent> {
        return Observable.merge(
            component_auth.intents(),
            mSubject.hide()
        )
    }

    override fun bindIntent(intent: Observable<AuthIntent>) {
        mViewModel.bindIntent(intent)
    }

    override fun observeState(state: MutableLiveData<AuthViewState>) {
        state.observe(viewLifecycleOwner, Observer { value ->
            Timber.d("Received Value: $value")
            component_auth.renderState(value)
        })
    }

    override fun observeEvent(event: SingleLiveEvent<AuthViewState>?) {
        event?.observe(viewLifecycleOwner, Observer { value ->
            Timber.d("Received Event: $value")
            component_auth.renderEvent(value)
        })
    }

    override fun state(): MutableLiveData<AuthViewState> = mViewModel.state()

    override fun event(): SingleLiveEvent<AuthViewState>? = mViewModel.event()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN_RC) {
            mSubject.onNext(
                AuthIntent.HandleGoogleSignInResult(data)
            )
        } else {
            mSubject.onNext(
                AuthIntent.HandleFacebookSignInResult(requestCode, resultCode, data)
            )
        }
    }

    //    val RC_SIGN_IN = 100
//
//    private lateinit var mFacebookAuthService: FacebookAuthService
//    private lateinit var mGoogleAuthService: GoogleAuthService
//
////    private lateinit var mGoogleSignInClient: GoogleSignInClient
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_auth, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
////        button_facebook_sign_in.fragment = this
//
//        mFacebookAuthService = FacebookAuthService(CallbackManager.Factory.create())
//
//        button_facebook_sign_in.setOnClickListener {
//            mFacebookAuthService.signOut()
//            mFacebookAuthService.signIn(this)
//        }
//
//        val gso =
//            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build()
//
//        mGoogleAuthService = GoogleAuthService(
//            GoogleSignIn.getClient(
//                requireContext(), gso
//            )
//        )
//
//        button_google_sign_in.setOnClickListener {
//            mGoogleAuthService.signOut()
//            val signInIntent = mGoogleAuthService.getSignInIntent()
//            startActivityForResult(signInIntent, RC_SIGN_IN)
//        }
//    }
//
//    override fun onStart() {
//        super.onStart()
//        val token = AccessToken.getCurrentAccessToken()
//        val isSignedIn = token != null && !token.isExpired
//        Timber.d("facebook user is signed in: $isSignedIn")
//
//        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
//        val isLogIn = account != null
//        Timber.d("google user is signed in: $isLogIn")
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        mFacebookAuthService.getCallbackManager().onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == RC_SIGN_IN) {
//            mGoogleAuthService.handleResultIntent(data)
//        }
//    }
}