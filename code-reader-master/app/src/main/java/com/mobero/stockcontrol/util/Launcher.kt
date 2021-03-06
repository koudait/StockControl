/*
 * Copyright (c) 2021 大前良介 (OHMAE Ryosuke)
 *
 * This software is released under the MIT License.
 * http://opensource.org/licenses/MIT
 */

package com.mobero.stockcontrol.util

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.app.ShareCompat
import com.mobero.stockcontrol.R
import com.mobero.stockcontrol.extension.isNightMode
import com.mobero.stockcontrol.extension.resolveColor

object Launcher {
    private const val PACKAGE_NAME = "com.mobero.stockcontrol"

    private const val PRIVACY_POLICY_URL =
        "https://github.com/ohmae/code-reader/blob/master/PRIVACY-POLICY.md"
    private const val GITHUB_URL =
        "https://github.com/ohmae/code-reader/"

    fun openUri(context: Context, uri: String): Boolean = runCatching {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        intent.addCategory(Intent.CATEGORY_BROWSABLE)
        context.startActivity(intent)
        true
    }.getOrNull() ?: false

    fun search(context: Context, word: String): Boolean = runCatching {
        val intent = Intent(Intent.ACTION_WEB_SEARCH)
        intent.putExtra(SearchManager.QUERY, word)
        context.startActivity(intent)
        true
    }.getOrNull() ?: false

    private fun openCustomTabs(context: Context, uri: String): Boolean =
        openCustomTabs(context, Uri.parse(uri))

    fun openCustomTabs(context: Context, uri: Uri): Boolean = runCatching {
        val scheme =
            if (context.isNightMode()) CustomTabsIntent.COLOR_SCHEME_DARK
            else CustomTabsIntent.COLOR_SCHEME_LIGHT
        val params = CustomTabColorSchemeParams.Builder()
            .setToolbarColor(context.resolveColor(R.attr.background))
            .build()
        val intent = CustomTabsIntent.Builder()
            .setShowTitle(true)
            .setColorScheme(scheme)
            .setDefaultColorSchemeParams(params)
            .build()
        intent.intent.setPackage(CustomTabsHelper.findPackageNameToUse(context))
        intent.launchUrl(context, uri)
        true
    }.getOrNull() ?: false

    private fun openGooglePlay(context: Context, packageName: String): Boolean =
        openUri(context, "market://details?id=$packageName") ||
                openCustomTabs(
                    context,
                    "https://play.google.com/store/apps/details?id=$packageName"
                )

    fun openGooglePlay(context: Context): Boolean =
        openGooglePlay(context, PACKAGE_NAME)

    fun openPrivacyPolicy(context: Context) {
        openCustomTabs(context, PRIVACY_POLICY_URL)
    }

    fun openSourceCode(context: Context) {
        openCustomTabs(context, GITHUB_URL)
    }

    fun shareThisApp(context: Activity) {
        val url = "https://play.google.com/store/apps/details?id=$PACKAGE_NAME"
        shareText(context, url)
    }

    fun shareText(context: Activity, text: String) {
        ShareCompat.IntentBuilder
            .from(context)
            .setText(text)
            .setType("text/plain")
            .startChooser()
    }
}
