package com.example.design_system.showcase

import android.os.Bundle
import com.example.design_system.databinding.ActivityForoomShowcaseBinding
import com.example.shared.ui.activity.BaseActivity
import java.time.LocalDateTime

class ForoomShowcaseActivity : BaseActivity<ActivityForoomShowcaseBinding>(
    ActivityForoomShowcaseBinding::inflate
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.message.setUp(
            "message message message message message message message message message message message message",
            "shioTakeksi",
            null
        )
    }
}

