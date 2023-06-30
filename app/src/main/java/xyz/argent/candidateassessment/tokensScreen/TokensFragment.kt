package xyz.argent.candidateassessment.tokensScreen

import xyz.argent.candidateassessment.basePresentation.BaseFragment
import xyz.argent.candidateassessment.basePresentation.KeyboardUtils.showKeyboard
import xyz.argent.candidateassessment.databinding.FragmentTokensBinding


class TokensFragment : BaseFragment() {
    private val views by viewBinding(FragmentTokensBinding::inflate)

    private val viewModel by viewModel {
        TokensViewModel()
    }

    override fun onStart() {
        super.onStart()
        showKeyboard(views.searchBox)
    }
}


