package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppPreset
import com.example.data.AppPresetData
import com.example.data.FontPairOption
import com.example.data.InstructionTemplates
import com.example.data.ThemeOption
import com.example.data.local.AppDatabase
import com.example.data.model.SavedProject
import com.example.data.repository.ProjectRepository
import com.example.domain.PromptGenerator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ProjectRepository

    val savedProjects: StateFlow<List<SavedProject>>

    init {
        val dao = AppDatabase.getDatabase(application).projectDao()
        repository = ProjectRepository(dao)
        savedProjects = repository.allProjects.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    // Navigation State
    private val _selectedNavIndex = MutableStateFlow(0)
    val selectedNavIndex: StateFlow<Int> = _selectedNavIndex.asStateFlow()

    fun setNavIndex(index: Int) {
        _selectedNavIndex.value = index
    }

    // Instruction Hub State
    private val _instructionLanguage = MutableStateFlow("English")
    val instructionLanguage: StateFlow<String> = _instructionLanguage.asStateFlow()

    fun setInstructionLanguage(lang: String) {
        _instructionLanguage.value = lang
    }

    // Builder Wizard State
    private val _builderStep = MutableStateFlow(1) // 1: Idea, 2: Features, 3: Style, 4: Review & Prompt
    val builderStep: StateFlow<Int> = _builderStep.asStateFlow()

    private val _customAppTitle = MutableStateFlow("Free Fire Tournament App")
    val customAppTitle: StateFlow<String> = _customAppTitle.asStateFlow()

    private val _selectedPreset = MutableStateFlow<AppPreset?>(AppPresetData.PRESETS[0])
    val selectedPreset: StateFlow<AppPreset?> = _selectedPreset.asStateFlow()

    private val _trendingSuggestions = MutableStateFlow<List<String>>(AppPresetData.PRESETS[0].trendingIdeas)
    val trendingSuggestions: StateFlow<List<String>> = _trendingSuggestions.asStateFlow()

    private val _currentFeatures = MutableStateFlow<List<String>>(AppPresetData.PRESETS[0].defaultFeatures)
    val currentFeatures: StateFlow<List<String>> = _currentFeatures.asStateFlow()

    private val _selectedFeatureIndices = MutableStateFlow<Set<Int>>(
        AppPresetData.PRESETS[0].defaultFeatures.indices.toSet()
    )
    val selectedFeatureIndices: StateFlow<Set<Int>> = _selectedFeatureIndices.asStateFlow()

    private val _selectedTheme = MutableStateFlow<ThemeOption>(AppPresetData.THEMES[0])
    val selectedTheme: StateFlow<ThemeOption> = _selectedTheme.asStateFlow()

    private val _selectedFontPair = MutableStateFlow<FontPairOption>(AppPresetData.FONT_PAIRS[0])
    val selectedFontPair: StateFlow<FontPairOption> = _selectedFontPair.asStateFlow()

    private val _selectedArchitecture = MutableStateFlow(AppPresetData.ARCHITECTURES[0])
    val selectedArchitecture: StateFlow<String> = _selectedArchitecture.asStateFlow()

    private val _selectedTargetPlatform = MutableStateFlow(AppPresetData.TARGET_PLATFORMS[0])
    val selectedTargetPlatform: StateFlow<String> = _selectedTargetPlatform.asStateFlow()

    private val _extraNotes = MutableStateFlow("")
    val extraNotes: StateFlow<String> = _extraNotes.asStateFlow()

    private val _generatedPrompt = MutableStateFlow("")
    val generatedPrompt: StateFlow<String> = _generatedPrompt.asStateFlow()

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    init {
        updateGeneratedPrompt()
    }

    fun setAppTitle(title: String) {
        _customAppTitle.value = title
        updateGeneratedPrompt()
    }

    fun setExtraNotes(notes: String) {
        _extraNotes.value = notes
        updateGeneratedPrompt()
    }

    fun setBuilderStep(step: Int) {
        _builderStep.value = step.coerceIn(1, 4)
    }

    fun selectPreset(preset: AppPreset) {
        _selectedPreset.value = preset
        _customAppTitle.value = preset.title
        _trendingSuggestions.value = preset.trendingIdeas
        _currentFeatures.value = preset.defaultFeatures
        _selectedFeatureIndices.value = preset.defaultFeatures.indices.toSet()
        _selectedArchitecture.value = preset.defaultArchitecture
        updateGeneratedPrompt()
    }

    fun applyCustomIdea(idea: String) {
        _customAppTitle.value = idea
        val dynamicSuggestions = listOf(
            "High-conversion mobile UX with modern Android card list layout",
            "Realtime data sync using Firebase Realtime Database with live updates",
            "Native-feeling bottom navigation bar with Font Awesome 6 icons",
            "Direct external image hosting (ImgBB/Imgur) - zero Firebase Storage",
            "Admin Panel with relaxed login and live content controls"
        )
        _trendingSuggestions.value = dynamicSuggestions
        val dynamicFeatures = listOf(
            "Native Android Top Bar with notification and profile avatar",
            "Card list with smooth active tap ripple animations",
            "Detail modal with image viewer, description, and action button",
            "Search bar with live instant client-side filtering",
            "Floating Action Button (FAB) or Action Drawer",
            "Bottom Navigation Bar with active indicator pills",
            "Admin Panel: Create, Edit, and Delete items in Firebase RTDB",
            "Admin Panel: Direct Image URL preview and updater",
            "Relaxed Admin authentication saved under /admin_auth",
            "Zero Firebase Storage requirement (all external hosted images)"
        )
        _currentFeatures.value = dynamicFeatures
        _selectedFeatureIndices.value = dynamicFeatures.indices.toSet()
        updateGeneratedPrompt()
    }

    fun toggleFeature(index: Int) {
        val current = _selectedFeatureIndices.value.toMutableSet()
        if (current.contains(index)) {
            current.remove(index)
        } else {
            current.add(index)
        }
        _selectedFeatureIndices.value = current
        updateGeneratedPrompt()
    }

    fun selectAllFeatures(select: Boolean) {
        if (select) {
            _selectedFeatureIndices.value = _currentFeatures.value.indices.toSet()
        } else {
            _selectedFeatureIndices.value = emptySet()
        }
        updateGeneratedPrompt()
    }

    fun setTheme(theme: ThemeOption) {
        _selectedTheme.value = theme
        updateGeneratedPrompt()
    }

    fun setFontPair(fontPair: FontPairOption) {
        _selectedFontPair.value = fontPair
        updateGeneratedPrompt()
    }

    fun setArchitecture(arch: String) {
        _selectedArchitecture.value = arch
        updateGeneratedPrompt()
    }

    fun setTargetPlatform(platform: String) {
        _selectedTargetPlatform.value = platform
        updateGeneratedPrompt()
    }

    fun updateGeneratedPrompt() {
        val selectedFeaturesList = _currentFeatures.value.filterIndexed { index, _ ->
            _selectedFeatureIndices.value.contains(index)
        }
        _generatedPrompt.value = PromptGenerator.generateMasterPrompt(
            appTitle = _customAppTitle.value,
            architecture = _selectedArchitecture.value,
            selectedFeatures = if (selectedFeaturesList.isEmpty()) _currentFeatures.value else selectedFeaturesList,
            theme = _selectedTheme.value,
            fontPair = _selectedFontPair.value,
            targetPlatform = _selectedTargetPlatform.value,
            extraNotes = _extraNotes.value
        )
    }

    fun saveCurrentProject() {
        viewModelScope.launch {
            val selectedFeaturesList = _currentFeatures.value.filterIndexed { index, _ ->
                _selectedFeatureIndices.value.contains(index)
            }.joinToString(";;")

            val project = SavedProject(
                title = _customAppTitle.value,
                appType = _selectedPreset.value?.category ?: "Custom",
                architecture = _selectedArchitecture.value,
                selectedFeatures = selectedFeaturesList,
                themeName = _selectedTheme.value.name,
                fontPairing = "${_selectedFontPair.value.headingFont} + ${_selectedFontPair.value.bodyFont}",
                targetPlatform = _selectedTargetPlatform.value,
                generatedPrompt = _generatedPrompt.value
            )
            repository.insertProject(project)
            showToast("Saved to Local Projects!")
        }
    }

    fun deleteProject(id: Int) {
        viewModelScope.launch {
            repository.deleteProjectById(id)
            showToast("Project deleted")
        }
    }

    fun showToast(msg: String) {
        _toastMessage.value = msg
    }

    fun clearToast() {
        _toastMessage.value = null
    }
}
