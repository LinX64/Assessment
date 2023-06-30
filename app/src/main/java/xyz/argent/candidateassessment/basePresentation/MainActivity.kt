package xyz.argent.candidateassessment.basePresentation

import android.os.Bundle
import androidx.fragment.app.commit
import xyz.argent.candidateassessment.R
import xyz.argent.candidateassessment.databinding.ActivityMainBinding
import xyz.argent.candidateassessment.welcomeScreen.WelcomeFragment

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.fragment_content, WelcomeFragment(), null)
            }
        }
    }

    fun pushFragment(fragment: BaseFragment) {
        supportFragmentManager.commit {
            replace(R.id.fragment_content, fragment, null)
            addToBackStack(null)
        }
    }
}