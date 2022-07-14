package com.immo.findTheDifferences.ui.screens

import android.content.ContentValues
import android.content.Context
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.immo.findTheDifferences.MainActivity
import timber.log.Timber

fun showAd(context: Context, callback: () -> Unit) {
    val adRequest = AdRequest.Builder().build()
    var mInterstitialAd: InterstitialAd? = null
    InterstitialAd.load(
        context,
        "ca-app-pub-3940256099942544/1033173712",
        adRequest,
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Timber.tag(ContentValues.TAG).d(adError.toString())
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Timber.tag(ContentValues.TAG).d("Ad was loaded.")
                mInterstitialAd = interstitialAd
                mInterstitialAd?.show(context as MainActivity)
            }
        })
    mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {

        override fun onAdDismissedFullScreenContent() {
            mInterstitialAd = null
            callback()
        }

        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
            mInterstitialAd = null
            callback()
        }

        override fun onAdClicked() {
            mInterstitialAd = null
            callback()
        }


        override fun onAdShowedFullScreenContent() {
            mInterstitialAd = null
            callback()
        }
    }
}