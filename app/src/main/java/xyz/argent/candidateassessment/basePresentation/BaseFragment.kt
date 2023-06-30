package xyz.argent.candidateassessment.basePresentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

abstract class BaseFragment : Fragment() {

    val mainActivity: MainActivity?
        get() = activity as? MainActivity?

    private var viewBindingDelegate: ViewBindingDelegate<out ViewBinding>? = null

    fun <T : ViewBinding> viewBinding(viewBindingFactory: (LayoutInflater, parent: ViewGroup?, attachToParent: Boolean) -> T): ViewBindingDelegate<T> {
        return ViewBindingDelegate(viewBindingFactory).also { viewBindingDelegate = it }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binder = viewBindingDelegate
        return binder?.inflate(inflater, container, false)?.root ?: super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        viewBindingDelegate?.clear()
        super.onDestroyView()
    }

    inner class ViewBindingDelegate<T : ViewBinding>(
        private val viewInflationFactory: (layoutInflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean) -> T
    ) : ReadOnlyProperty<BaseFragment, T> {

        private var binding: T? = null

        fun clear() {
            binding = null
        }

        fun inflate(layoutInflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean): T {
            require(binding == null) { "Already inflated" }
            return viewInflationFactory(layoutInflater, parent, attachToParent)
                .also { this.binding = it }
        }

        override fun getValue(thisRef: BaseFragment, property: KProperty<*>): T {
            return requireNotNull(binding) { "Not bound yet" }
        }
    }


    @MainThread
    inline fun <reified VM : ViewModel> viewModel(noinline callback: () -> VM): Lazy<VM> {
        return viewModels(factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    return callback() as T
                }
            }
        })
    }
}