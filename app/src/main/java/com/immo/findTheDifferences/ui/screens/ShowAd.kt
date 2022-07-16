package com.immo.findTheDifferences.ui.screens

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.immo.findTheDifferences.Const.AD_INTERSTITIAL_ID
import com.immo.findTheDifferences.Const.AD_REWARDED_ID
import com.immo.findTheDifferences.MainActivity
import timber.log.Timber

fun showAd(context: Context, callback: () -> Unit) {
    val adRequest = AdRequest.Builder().build()
    InterstitialAd.load(
        context,
        AD_INTERSTITIAL_ID,
        adRequest,
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Timber.tag(TAG).d(adError.toString())
                callback()
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Timber.tag(TAG).d("Ad was loaded.")
                interstitialAd.fullScreenContentCallback = object : FullScreenContentCallback() {

                    override fun onAdDismissedFullScreenContent() {
                        callback()
                    }

                    override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                        callback()
                    }
                }
                interstitialAd.show(context as MainActivity)
            }
        })
}

fun showRewardedAD(context: Context, callback: (rewarded: Boolean) -> Unit) {
    val adRequest = AdRequest.Builder().build()
    var isRewarded = false
    RewardedAd.load(
        context,
        AD_REWARDED_ID,
        adRequest,
        object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Timber.tag(TAG).d(adError.toString())
                callback(false)
            }

            override fun onAdLoaded(rewardedAd: RewardedAd) {
                Log.d(TAG, "Ad was loaded.")
                rewardedAd.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdClicked() {
                        // Called when a click is recorded for an ad.
                        Log.d(TAG, "Ad was clicked.")
                    }

                    override fun onAdDismissedFullScreenContent() {
                        Log.d(TAG, "Ad dismissed fullscreen content.")
                        callback(isRewarded)
                    }

                    override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                        // Called when ad fails to show.
                        Log.e(TAG, "Ad failed to show fullscreen content.")
                        callback(false)
                    }

                    override fun onAdImpression() {
                        // Called when an impression is recorded for an ad.
                        Log.d(TAG, "Ad recorded an impression.")
                    }

                    override fun onAdShowedFullScreenContent() {
                        // Called when ad is shown.
                        Log.d(TAG, "Ad showed fullscreen content.")
                    }

                }
                rewardedAd.show(context as MainActivity) { isRewarded = true }
            }
        })
}