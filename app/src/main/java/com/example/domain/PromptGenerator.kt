package com.example.domain

import com.example.data.AppPresetData
import com.example.data.FontPairOption
import com.example.data.ThemeOption

object PromptGenerator {

    fun generateMasterPrompt(
        appTitle: String,
        architecture: String,
        selectedFeatures: List<String>,
        theme: ThemeOption,
        fontPair: FontPairOption,
        targetPlatform: String,
        extraNotes: String = ""
    ): String {
        val isAdminAndUser = architecture.contains("Admin + User")
        val isMultiplayer = architecture.contains("Multiplayer")
        val isSingleUserOnly = architecture.contains("User Only")

        val filesRule = when {
            isAdminAndUser -> """
STRICT 2-FILE ARCHITECTURE:
- Deliver exactly TWO complete, standalone single-file HTML documents:
  1. `admin.html` (Complete administrator panel with direct image link inputs, data controls, and relaxed login)
  2. `index.html` (Complete client/user application rendering live data from Firebase RTDB)
- NO external CSS files, NO external JS files. All styles in `<style>` and all scripts in `<script>`.
""".trimIndent()
            isMultiplayer -> """
STRICT 1-FILE MULTIPLAYER ARCHITECTURE:
- Deliver exactly ONE complete, standalone `index.html` file containing the complete multiplayer game client, lobby, room code matchmaking, and canvas/DOM board.
- Integrated with Firebase Realtime Database for realtime turn-taking and state sync.
- All styles in `<style>` and all scripts in `<script>`.
""".trimIndent()
            else -> """
STRICT 1-FILE ARCHITECTURE:
- Deliver exactly ONE complete, standalone `index.html` file containing HTML5, embedded CSS in `<style>`, and embedded JavaScript in `<script>`.
- Offline/LocalStorage only — NO Firebase required.
""".trimIndent()
        }

        val firebaseSection = if (isAdminAndUser || isMultiplayer) {
            """
### 3. DATABASE & IMAGE STORAGE SPECIFICATIONS (STRICT)
- **Database**: Firebase Realtime Database ONLY.
  Use Compat v9 CDN scripts:
  `<script src="https://www.gstatic.com/firebasejs/9.22.1/firebase-app-compat.js"></script>`
  `<script src="https://www.gstatic.com/firebasejs/9.22.1/firebase-database-compat.js"></script>`
- **Firebase Storage**: STRICTLY PROHIBITED. Do NOT use Firebase Storage under any circumstances.
- **Image Handling**: All images must be hosted externally (e.g. ImgBB, PostImages, Cloudinary, Imgur, or direct CDN links).
  In `admin.html`, provide a clean input field for 'Direct Image URL' with live image preview.
  In `index.html`, display these image URLs directly using `<img>` tags with graceful fallback placeholders.
- **Admin Authentication**:
  Store admin credentials directly in Firebase RTDB under `/admin_auth` (e.g. `admin_id: "admin123"`, `admin_password: "password123"`).
  Admin login logic must be relaxed and flexible (check username/password against the RTDB node, no complex OAuth or phone verification).
""".trimIndent()
        } else {
            """
### 3. DATA PERSISTENCE SPECIFICATIONS
- Pure LocalStorage / In-memory JavaScript state.
- No Firebase setup required.
""".trimIndent()
        }

        val featuresFormatted = selectedFeatures.mapIndexed { index, feature ->
            "${index + 1}. $feature"
        }.joinToString("\n")

        return """
# MASTER PROMPT: ${appTitle.uppercase()} WEB APPLICATION

Act as a Senior Principal Frontend & Mobile App Architect. Build a complete, production-ready web application for **${appTitle}** targeting **${targetPlatform}**.

---

### 1. ARCHITECTURE & FILE STRUCTURE (MANDATORY RULE)
$filesRule

---

### 2. UI / DESIGN SYSTEM (NATIVE ANDROID APP FEEL)
The UI MUST NOT feel like a generic desktop website. It must look and feel indistinguishable from a high-end native Android application built in Kotlin/Jetpack Compose:
- **Theme**: ${theme.name}
  - Primary Color: `${theme.primaryColorHex}`
  - Surface Dark: `${theme.surfaceColorHex}`
  - Accent Color: `${theme.accentColorHex}`
- **Typography**: Google Fonts CDN:
  `<link rel="stylesheet" href="${fontPair.googleFontLink}">`
  - Headings / Display: '${fontPair.headingFont}', sans-serif
  - Body / UI: '${fontPair.bodyFont}', sans-serif
- **Android Native Elements**:
  - Edge-to-edge layout with top status bar padding & elevated Top App Bar.
  - Native Bottom Navigation Bar with active indicator pills and Font Awesome 6 icons:
    `<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">`
  - Android Material ripple/touch scale feedback on cards and buttons (`active { transform: scale(0.97); }`).
  - Native-style dialog modals and slide-up bottom sheets.
  - Hide all web scrollbars (`::-webkit-scrollbar { display: none; }`, `scrollbar-width: none;`).
  - Viewport lock: `<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">`
  - Disable default web highlights: `-webkit-tap-highlight-color: transparent; user-select: none;`

---

$firebaseSection

---

### 4. SELECTED FUNCTIONAL REQUIREMENTS & FEATURES
You MUST implement every single one of the following numbered features completely, with working logic:
$featuresFormatted
${if (extraNotes.isNotBlank()) "\nAdditional Requirements:\n$extraNotes" else ""}

---

### 5. REQUIRED TOOLS & CDNS LIST
Ensure the following tools are integrated into the output:
1. Font Awesome 6.4.0 CDN for all icons.
2. Google Fonts (${fontPair.headingFont} & ${fontPair.bodyFont}).
${if (isAdminAndUser || isMultiplayer) "3. Firebase 9.22.1 Compat App & Realtime Database CDN scripts.\n4. Firebase configuration block with clear placeholder constants (`apiKey`, `databaseURL`, `projectId`)." else "3. Modern HTML5 Web Storage API."}
${if (isMultiplayer) "4. Canvas-confetti CDN for victory celebration." else ""}
5. Optimized for **${targetPlatform}** (DOMStorageEnabled, JavaScriptEnabled, zero external local asset dependencies).

---

### 6. HEAVY CODE DELIVERY RULE (STRICT)
- DO NOT summarize, omit code, or write placeholders like `/* Write your logic here */`. Every button, modal, Firebase listener, and UI element MUST have real working code.
- If the complete code exceeds the single response output limit:
  - Deliver the solution in sequential parts (e.g. **PART 1 OF 2: admin.html**, **PART 2 OF 2: index.html**, or split large files cleanly into CSS/HTML followed by JS).
  - Stop cleanly at a logical line and state: `[PART 1 COMPLETE. Please type "continue from last" to receive Part 2]`.
  - When I reply "continue from last", immediately resume from the exact character where you stopped.
""".trimIndent()
    }
}
