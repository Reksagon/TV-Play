package com.tevibox.tvplay.utils

import androidx.annotation.IdRes
import androidx.fragment.app.*

object FragmentUtils {

    fun runFragmentTransactionWithRemoveCurrentFragment(
        fragmentManager: FragmentManager,
        fragmentTag: String,
        block: (FragmentTransaction) -> Unit
    ) {
        fragmentManager.commit {
            val currentFragment = fragmentManager.findFragmentByTag(fragmentTag)
            if (currentFragment != null) remove(currentFragment)
            block(this)
        }
    }

    inline fun <reified F : Fragment> addFragment(
        transaction: FragmentTransaction,
        @IdRes fragmentContainerId: Int,
        fragmentTag: String
    ) {
        transaction.add<F>(
            fragmentContainerId,
            fragmentTag
        )
    }
}