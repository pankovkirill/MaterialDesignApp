package com.example.materialdesignapp.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.materialdesignapp.R
import com.example.materialdesignapp.databinding.FragmentSettingsBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.bottom_sheet_settings.*

private const val APP_THEME = "APP_THEME"

class SettingsFragment : Fragment() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (activity?.getPreferences(AppCompatActivity.MODE_PRIVATE)
            ?.getInt(APP_THEME, R.style.AppTheme)) {
            R.style.CustomTheme -> binding.changeTheme.text = getString(R.string.custom_theme)
            R.style.AppTheme -> binding.changeTheme.text = getString(R.string.light_theme)
            R.style.AppThemeNight -> binding.changeTheme.text = getString(R.string.night_theme)
        }
        bottomSheetBehavior =
            BottomSheetBehavior.from(view.findViewById(R.id.bottom_sheet_container))
        changeTheme()
        choosingTheme()
    }

    private fun changeTheme() {
        binding.changeTheme.setOnClickListener {
            setBottomSheetBehavior()
        }
    }

    private fun choosingTheme() {
        bottom_sheet_system.setOnClickListener {
            setTheme(
                R.style.CustomTheme,
                AppCompatDelegate.MODE_NIGHT_NO,
                bottom_sheet_system
            )
        }
        bottom_sheet_light.setOnClickListener {
            setTheme(
                R.style.AppTheme,
                AppCompatDelegate.MODE_NIGHT_NO,
                bottom_sheet_light
            )
        }
        bottom_sheet_night.setOnClickListener {
            setTheme(
                R.style.AppThemeNight,
                AppCompatDelegate.MODE_NIGHT_YES,
                bottom_sheet_night
            )
        }
    }

    private fun setTheme(appTheme: Int, modeNightYes: Int, textView: TextView) {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        AppCompatDelegate.setDefaultNightMode(modeNightYes)
        binding.changeTheme.text = textView.text
        activity?.let {
            with(it.getPreferences(Context.MODE_PRIVATE).edit()) {
                putInt(APP_THEME, appTheme).apply()
            }
            it.recreate()
        }
    }

    private fun setBottomSheetBehavior() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.peekHeight = 450
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}