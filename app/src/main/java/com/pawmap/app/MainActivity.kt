package com.pawmap.app

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pawmap.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Destinations that show the bottom navigation bar (the 3 top-level tabs).
    private val topLevelDestinations = setOf(
        R.id.mapHomeFragment,
        R.id.savedPlacesFragment,
        R.id.tripMainFragment,
        R.id.tripNameFragment
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // targetSdk 36부터 edge-to-edge가 강제돼 콘텐츠가 상태바/내비게이션바와 겹치므로,
        // 최상위 루트에 시스템 바 인셋만큼 패딩을 줘서 모든 화면에서 겹침을 방지.
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(bars.left, bars.top, bars.right, 0)
            // 하단 인셋은 bottomNav 자체에 적용해 콘텐츠 영역이 그만큼 줄지 않도록 함
            binding.bottomNav.setPadding(0, 0, 0, bars.bottom)
            insets
        }

        val navHost = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHost.navController

        val bottomNav: BottomNavigationView = binding.bottomNav
        bottomNav.setupWithNavController(navController)

        // Show the bottom bar only on the three main tabs.
        navController.addOnDestinationChangedListener { _, destination, _ ->
            bottomNav.visibility =
                if (destination.id in topLevelDestinations) View.VISIBLE else View.GONE
        }
    }
}