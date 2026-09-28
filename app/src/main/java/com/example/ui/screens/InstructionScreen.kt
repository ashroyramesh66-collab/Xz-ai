package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.InstructionTemplates
import com.example.ui.MainViewModel
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.NeonPink
import com.example.ui.theme.NeonViolet
import com.example.ui.theme.ObsidianBg
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.SurfaceCardElevated
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun InstructionScreen(viewModel: MainViewModel) {
    val context = LocalContext.current
    val language by viewModel.instructionLanguage.collectAsStateWithLifecycle()

    val currentInstruction = if (language == "English") {
        InstructionTemplates.MASTER_CUSTOM_INSTRUCTION_EN
    } else {
        InstructionTemplates.MASTER_CUSTOM_INSTRUCTION_HINGLISH
    }

    var copiedRecently by remember { mutableStateOf(false) }

    fun copyToClipboard(text: String, label: String = "Custom Instruction") {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
        copiedRecently = true
        Toast.makeText(context, "Copied to Clipboard! Ready to paste into AI.", Toast.LENGTH_SHORT).show()
    }

    fun shareText(text: String) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }
        context.startActivity(Intent.createChooser(sendIntent, "Share Custom Instruction"))
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(ObsidianBg)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))
            // Header card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("instruction_header_card"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceCard),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF30363D))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(ElectricCyan.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Terminal,
                                    contentDescription = "Instruction",
                                    tint = ElectricCyan,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Master Custom Instruction",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                                Text(
                                    text = "Ready for ChatGPT / Claude / Gemini",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextSecondary
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "Paste this into your AI's 'Custom Instructions' or 'System Prompt'. The AI will strictly follow your 9 rules for Web Apps, Games, Firebase RTDB, and Android-style single HTML files.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFCAD1D9),
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Language toggles
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Format:",
                            style = MaterialTheme.typography.labelMedium,
                            color = TextSecondary
                        )
                        FilterChip(
                            selected = language == "English",
                            onClick = { viewModel.setInstructionLanguage("English") },
                            label = { Text("English (Official)") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = ElectricCyan.copy(alpha = 0.2f),
                                selectedLabelColor = ElectricCyan
                            )
                        )
                        FilterChip(
                            selected = language == "Hinglish",
                            onClick = { viewModel.setInstructionLanguage("Hinglish") },
                            label = { Text("Hinglish (Desi / Direct)") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = NeonViolet.copy(alpha = 0.2f),
                                selectedLabelColor = NeonViolet
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Copy and Share Action Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = { copyToClipboard(currentInstruction) },
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .testTag("copy_master_instruction_button"),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (copiedRecently) EmeraldGreen else ElectricCyan
                            )
                        ) {
                            Icon(
                                imageVector = if (copiedRecently) Icons.Default.Check else Icons.Default.ContentCopy,
                                contentDescription = "Copy",
                                tint = Color.Black,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (copiedRecently) "Copied!" else "Copy Instruction",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }

                        OutlinedButton(
                            onClick = { shareText(currentInstruction) },
                            modifier = Modifier
                                .height(48.dp)
                                .testTag("share_master_instruction_button"),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = TextPrimary
                            ),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF30363D))
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Share",
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }

        item {
            // Live Code Preview Box
            Text(
                text = "RAW INSTRUCTION PREVIEW",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = ElectricCyan,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .border(1.dp, Color(0xFF30363D), RoundedCornerShape(14.dp)),
                color = Color(0xFF090D13)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = currentInstruction,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.5.sp,
                            lineHeight = 18.sp
                        ),
                        color = Color(0xFF8B949E)
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "THE 9 CORE RULES BREAKDOWN",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = NeonPink,
                letterSpacing = 1.sp
            )
        }

        // 9 Rule Expandable Cards
        item {
            RuleCard(
                ruleNumber = "1",
                title = "App / Game Idea Handling",
                summary = "AI first suggests trending angles, then gives a detailed numbered list of features.",
                details = "Whenever you give an app name or concept, the AI is blocked from coding immediately. It must present 3-5 trending variations and a detailed numbered list (1 to 15+) covering user UI, admin controls, data sync, and Android native touches."
            )
        }
        item {
            RuleCard(
                ruleNumber = "2",
                title = "Theme & Font Selection",
                summary = "AI suggests trending visual themes & Google Font combinations before coding.",
                details = "Presents options like Cyber AMOLED, Material You, Luxury Gold, or Glassmorphism, paired with modern Google Fonts (e.g., Poppins + Inter, Rajdhani + Inter). The user selects a style, and the final UI matches it precisely."
            )
        }
        item {
            RuleCard(
                ruleNumber = "3",
                title = "Feature Selection Flow",
                summary = "You pick specific feature numbers (e.g. 1, 3, 5) or 'ALL' before prompt generation.",
                details = "The AI generates the final detailed prompt only after feature selection, utilizing HTML5, modern CSS3 variables, Vanilla JavaScript, and Font Awesome 6 icons."
            )
        }
        item {
            RuleCard(
                ruleNumber = "4",
                title = "Strict Single-File Architecture",
                summary = "User only = 1 single HTML file. Admin + User = exactly 2 single HTML files.",
                details = "Strictly no external local CSS or JS files. All CSS inside <style> and scripts in <script>. For Admin + User apps, exactly 2 files: admin.html and index.html."
            )
        }
        item {
            RuleCard(
                ruleNumber = "5",
                title = "Admin Panel & Firebase RTDB Rules",
                summary = "Firebase Realtime Database ONLY. Strictly NO Firebase Storage.",
                details = "Admin credentials are saved under /admin_auth with relaxed login validation. All images are hosted externally (ImgBB, PostImages, Cloudinary). Admin pastes direct link, which syncs to RTDB and renders on user panel."
            )
        }
        item {
            RuleCard(
                ruleNumber = "6",
                title = "Single HTML & Multiplayer Game Rule",
                summary = "Offline apps use no Firebase. Online multiplayer games use Firebase RTDB.",
                details = "Single player games use pure localStorage state. Online multiplayer games use Firebase RTDB for room code matchmaking and live turns, including complete setup instructions."
            )
        }
        item {
            RuleCard(
                ruleNumber = "7",
                title = "Native Android Look & Feel (Mandatory)",
                summary = "UI looks and feels like a native Kotlin/Java Android app, not a website.",
                details = "Includes Top App Bar, Bottom Navigation Bar with active pills, touch ripples (active: scale(0.97)), hidden scrollbars, viewport zoom lock, and Android dialog bottom sheets."
            )
        }
        item {
            RuleCard(
                ruleNumber = "8",
                title = "Prompt & Tooling Requirement",
                summary = "Lists all tools & CDNs ready for HopWeb, Sketchware Pro, and InfinityFree.",
                details = "Includes Font Awesome CDN, Google Fonts, Firebase compat scripts, and Webview settings (JavaScriptEnabled, DomStorageEnabled)."
            )
        }
        item {
            RuleCard(
                ruleNumber = "9",
                title = "Heavy Code / Multi-Part Continuation",
                summary = "Never compromise code. Split into Part 1, Part 2, and resume on 'continue from last'.",
                details = "For heavy apps, AI writes full production code up to the token limit, cleanly stops, and when you type 'continue from last' or 'aage continue karo', resumes from the exact next line without restarting or using lazy placeholders."
            )
        }

        item {
            // How to apply guide card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceCardElevated)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "🚀 How to Set this Up in AI:",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldGreen
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "1. Tap 'Copy Instruction' above.\n2. Open ChatGPT -> Settings -> Custom Instructions -> Paste into 'How would you like ChatGPT to respond?'\n3. For Claude: Create a Project -> Project Instructions -> Paste.\n4. For Gemini / AI Studio: System Instructions -> Paste.\n5. Now just write 'Free Fire tournament app' or any app name, and watch the AI follow your 9 rules!",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary,
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }
}

@Composable
fun RuleCard(
    ruleNumber: String,
    title: String,
    summary: String,
    details: String
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .clickable { expanded = !expanded }
            .testTag("rule_card_$ruleNumber"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceCard),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF262C36))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(ElectricCyan.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = ruleNumber,
                            fontWeight = FontWeight.Bold,
                            color = ElectricCyan,
                            fontSize = 13.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Rule $ruleNumber: $title",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary
                        )
                        Text(
                            text = summary,
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary,
                            maxLines = if (expanded) 10 else 1
                        )
                    }
                }
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = "Expand",
                    tint = TextMuted,
                    modifier = Modifier.size(20.dp)
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 10.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color(0xFF30363D))
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = details,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFCAD1D9),
                        lineHeight = 19.sp
                    )
                }
            }
        }
    }
}
