package com.tevibox.tvplay.presentation.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.tevibox.tvplay.R

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseDialog<VB : ViewDataBinding>(
    @LayoutRes protected val contentLayoutId: Int,
    @StyleRes protected val dialogTheme: Int = R.style.BaseDialog
) : DialogFragment() {

    protected lateinit var binding: VB

    protected var dialogStyle: Int = STYLE_NO_TITLE

    var cancelListener: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(dialogStyle, dialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, contentLayoutId, container, false)
        onViewBindingCreated(binding)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    protected open fun onViewBindingCreated(binding: VB) {
        // none
    }

    open fun show(manager: FragmentManager) = show(manager, javaClass.name)

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        cancelListener?.invoke()
    }

    fun setOnCancelListener(listener: () -> Unit): BaseDialog<*> {
        cancelListener = listener
        return this
    }
}