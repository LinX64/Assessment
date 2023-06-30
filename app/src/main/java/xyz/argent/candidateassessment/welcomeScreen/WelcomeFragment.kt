package xyz.argent.candidateassessment.welcomeScreen

import android.os.Bundle
import android.view.View
import xyz.argent.candidateassessment.app.Constants
import xyz.argent.candidateassessment.basePresentation.BaseFragment
import xyz.argent.candidateassessment.databinding.FragmentWelcomeBinding
import xyz.argent.candidateassessment.tokensScreen.TokensFragment

class WelcomeFragment : BaseFragment() {

    private val views by viewBinding(FragmentWelcomeBinding::inflate)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        views.walletAddress.text = Constants.walletAddress
        views.tokensButton.setOnClickListener {
            mainActivity!!.pushFragment(TokensFragment())
        }
    }
}