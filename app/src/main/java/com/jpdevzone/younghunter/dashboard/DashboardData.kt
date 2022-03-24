package com.jpdevzone.younghunter.dashboard

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DashboardData (
    val title: Int,
    val subtitle: Int,
    val icon: Int,
    val topic: String
    ) : Parcelable