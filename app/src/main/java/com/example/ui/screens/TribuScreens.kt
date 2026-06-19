package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.graphics.Bitmap
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.compose.ui.graphics.asImageBitmap
import com.example.data.model.Appointment
import com.example.data.model.MedicalDocument
import com.example.data.model.MoodCheckIn
import com.example.data.model.SupportContact
import com.example.data.model.UserProfile
import com.example.data.model.WhatsAppMessageLog
import com.example.ui.theme.*
import com.example.viewmodel.TribuMentalViewModel
import com.example.utils.Locales
import com.example.services.GeminiService
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import kotlin.math.roundToInt
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

// Dynamic Procedural Soft Illustration for Mothers using Canvas drawing
@Composable
fun MotherSoothingIllustration(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height

        // Draw soft soothing auras in pink and lavender background
        drawCircle(
            color = SoftLila.copy(alpha = 0.4f),
            radius = width * 0.4f,
            center = Offset(width * 0.5f, height * 0.5f)
        )
        drawCircle(
            color = PowderPink.copy(alpha = 0.5f),
            radius = width * 0.28f,
            center = Offset(width * 0.38f, height * 0.42f)
        )

        // Draw delicate winding branch (Cherry Blossom branch)
        val branchPath = Path().apply {
            moveTo(width * 0.15f, height * 0.72f)
            quadraticTo(width * 0.4f, height * 0.62f, width * 0.55f, height * 0.45f)
            quadraticTo(width * 0.75f, height * 0.35f, width * 0.85f, height * 0.28f)
        }
        drawPath(
            path = branchPath,
            color = Color(0xFF8D736A), // Elegant warm brown branch
            style = Stroke(width = 7f, cap = androidx.compose.ui.graphics.StrokeCap.Round)
        )

        // Draw smaller secondary branch
        val subBranchPath = Path().apply {
            moveTo(width * 0.48f, height * 0.52f)
            quadraticTo(width * 0.62f, height * 0.58f, width * 0.78f, height * 0.62f)
        }
        drawPath(
            path = subBranchPath,
            color = Color(0xFF8D736A),
            style = Stroke(width = 4f, cap = androidx.compose.ui.graphics.StrokeCap.Round)
        )

        // Draw beautiful delicate cherry blossoms (🌸) at specific node coordinates
        val blossomNodes = listOf(
            Offset(width * 0.35f, height * 0.65f) to 0.15f,
            Offset(width * 0.55f, height * 0.45f) to 0.19f,
            Offset(width * 0.75f, height * 0.35f) to 0.14f,
            Offset(width * 0.68f, height * 0.59f) to 0.13f
        )

        blossomNodes.forEach { (center, scale) ->
            val r = width * scale * 0.35f
            // Draw 5 petals for each flower
            for (i in 0 until 5) {
                val angle = i * (2 * Math.PI / 5) - Math.PI / 2
                val petalX = center.x + (r * 1.2f * Math.cos(angle)).toFloat()
                val petalY = center.y + (r * 1.2f * Math.sin(angle)).toFloat()
                
                // Outer soft pink petal
                drawCircle(
                    color = PowderPink,
                    radius = r,
                    center = Offset(petalX, petalY)
                )
                
                // Delicate inner pink petal highlight
                drawCircle(
                    color = SecondaryDustPink.copy(alpha = 0.9f),
                    radius = r * 0.65f,
                    center = Offset(petalX, petalY)
                )
            }
            
            // Core stamen glow
            drawCircle(
                color = Color(0xFFFFF59D), // Primrose yellow
                radius = r * 0.4f,
                center = center
            )
            drawCircle(
                color = Color(0xFFE5A44E), // Sweet orange-gold center
                radius = r * 0.18f,
                center = center
            )
        }

        // Draw falling petals for romantic wind flow
        val details = listOf(
            Offset(width * 0.22f, height * 0.38f),
            Offset(width * 0.82f, height * 0.52f),
            Offset(width * 0.28f, height * 0.82f)
        )

        details.forEach { pos ->
            val petalPath = Path().apply {
                moveTo(pos.x, pos.y)
                quadraticTo(pos.x - 10f, pos.y + 10f, pos.x, pos.y + 20f)
                quadraticTo(pos.x + 10f, pos.y + 10f, pos.x, pos.y)
            }
            drawPath(petalPath, color = PowderPink)
            drawPath(
                path = petalPath,
                color = SecondaryDustPink.copy(alpha = 0.6f),
                style = Stroke(width = 1.5f)
            )
        }
    }
}

// Draw calming breathing circle guide
@Composable
fun BreathingCircleGuide(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "breathing")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(3500, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    val textState = if (scale > 1.12f) "Inhala..." else "Exhala con calma..."

    Box(
        modifier = modifier
            .size(160.dp)
            .drawBehind {
                drawCircle(
                    color = SoftLila,
                    radius = (size.minDimension / 1.8f) * scale,
                    center = center
                )
                drawCircle(
                    color = PowderPink.copy(alpha = 0.6f),
                    radius = (size.minDimension / 2.1f) * scale,
                    center = center
                )
                drawCircle(
                    color = PrimaryDeepPurple.copy(alpha = 0.2f),
                    radius = (size.minDimension / 1.8f) * scale,
                    center = center,
                    style = Stroke(width = 3.dp.toPx())
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                tint = SecondaryDustPink,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = textState,
                color = PrimaryDeepPurple,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }
    }
}

// Custom formatted date utility
fun formatTimestamp(time: Long, format: String = "dd/MM/yyyy HH:mm"): String {
    return try {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        sdf.format(Date(time))
    } catch (e: Exception) {
        "Reciente"
    }
}

// Landing - Pricing Screen Component
@Composable
fun LandingScreen(
    onNavigateToOnboarding: () -> Unit,
    onNavigateToDirectUpgrade: (String) -> Unit,
    profile: UserProfile?,
    viewModel: TribuMentalViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(CreamBackground)
            .padding(20.dp)
            .windowInsetsPadding(WindowInsets.safeDrawing),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        
        // Brand Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(PowderPink),
                contentAlignment = Alignment.Center
            ) {
                Text("🌸", fontSize = 24.sp)
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = Locales.string("app_title", viewModel) + " 🌸",
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                color = PrimaryDeepPurple,
                fontFamily = FontFamily.Serif,
                letterSpacing = 0.5.sp
            )
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = Locales.string("landing_subtitle", viewModel),
            fontSize = 14.sp,
            color = MutedSlateSub,
            fontFamily = FontFamily.Default
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Large Premium Vector Illustration Card
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = WarmCardWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                MotherSoothingIllustration(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .align(Alignment.Center)
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Box(
                        modifier = Modifier
                            .background(PrimaryDeepPurple, RoundedCornerShape(12.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "Acompañamiento Doula & IA",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Emotion hook title
        Text(
            text = Locales.string("landing_title", viewModel),
            fontSize = 26.sp,
            fontWeight = FontWeight.SemiBold,
            color = PrimaryDeepPurple,
            textAlign = TextAlign.Center,
            lineHeight = 32.sp,
            fontFamily = FontFamily.Serif
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = Locales.string("landing_desc", viewModel),
            fontSize = 14.sp,
            color = DeepCharcoalText.copy(alpha = 0.85f),
            textAlign = TextAlign.Center,
            lineHeight = 22.sp,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Action CTA
        Button(
            onClick = {
                onNavigateToOnboarding()
            },
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryDeepPurple),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .testTag("get_started_btn")
        ) {
            Text(
                text = if (profile?.isOnboarded == true) "Dashboard" else Locales.string("landing_start", viewModel),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Feature highlights
        Divider(color = SoftBorderPlum)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "¿Por qué unirse a TribuMental?",
            fontWeight = FontWeight.Bold,
            color = PrimaryDeepPurple,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(16.dp))

        FeatureBulletItem(
            emoji = "💬",
            title = "Integración total con WhatsApp",
            desc = "Registros diarios y alertas cariñosas directamente en tu móvil."
        )
        FeatureBulletItem(
            emoji = "🩺",
            title = "Calendario Médico Integrado",
            desc = "Planifica prenatales, vacunas y consultas pediátricas con un clic."
        )
        FeatureBulletItem(
            emoji = "📄",
            title = "Escaneo Inteligente de Recetas",
            desc = "Sube fotos de tus órdenes e informes y asócialos a sus citas con OCR."
        )
        FeatureBulletItem(
            emoji = "🧠",
            title = "Inteligencia Emocional Adaptativa",
            desc = "Mensajes de validación y contención desarrollados con IA de Gemini."
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Pricing Cards
        Divider(color = SoftBorderPlum)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Planes creados con Amor",
            fontWeight = FontWeight.ExtraBold,
            color = PrimaryDeepPurple,
            fontSize = 18.sp,
            fontFamily = FontFamily.Serif
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Plan 1: Free
        PricingPlanCard(
            title = "Plan Diario Gratis",
            price = "Gratuito",
            benefits = listOf(
                "Calendario de citas médicas local",
                "Historial básico de estado de ánimo",
                "Ingreso de documentos manual"
            ),
            buttonText = "Comenzar gratis",
            onPlanSelected = { onNavigateToOnboarding() },
            isRecommended = false
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Plan 2: Premium
        PricingPlanCard(
            title = "Plan Tribu Premium",
            price = "$9.99 / mes",
            benefits = listOf(
                "Check-ins interactivos vía WhatsApp",
                "Escaneo OCR inteligente de recetas",
                "Soporte emocional dinámico Gemini",
                "Acompañamiento de alarma de bienestar"
            ),
            buttonText = "Suscribirse Premium",
            onPlanSelected = { onNavigateToDirectUpgrade("MENSUAL") },
            isRecommended = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Plan 3: Family Copilot
        PricingPlanCard(
            title = "Plan Acompañante Dual",
            price = "$14.99 / mes",
            benefits = listOf(
                "Todo lo incluido en Premium",
                "Segundo canal asociado (Pareja/Cuidador)",
                "Historial unificado y chat compartido"
            ),
            buttonText = "Suscribirme Dual",
            onPlanSelected = { onNavigateToDirectUpgrade("FAMILIAR") },
            isRecommended = false
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Health Disclaimer mandated
        Box(
            modifier = Modifier
                .background(SageGreen.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Info, contentDescription = null, tint = TerciaryWarmTeal, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Información de Salud y Seguridad", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = TerciaryWarmTeal)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "TribuMental es una herramienta educativa y de apoyo emocional privado. No sustituye terapias clínicas, diagnósticos médicos obstétricos ni asesoramiento psiquiátrico profesional.",
                    fontSize = 11.sp,
                    color = DeepCharcoalText,
                    lineHeight = 16.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun FeatureBulletItem(emoji: String, title: String, desc: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(SoftLila.copy(alpha = 0.7f)),
            contentAlignment = Alignment.Center
        ) {
            Text(emoji, fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontWeight = FontWeight.Bold, color = PrimaryDeepPurple, fontSize = 14.sp)
            Text(text = desc, color = MutedSlateSub, fontSize = 12.sp, lineHeight = 16.sp)
        }
    }
}

@Composable
fun PricingPlanCard(
    title: String,
    price: String,
    benefits: List<String>,
    buttonText: String,
    onPlanSelected: () -> Unit,
    isRecommended: Boolean
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isRecommended) SoftLila.copy(alpha = 0.4f) else WarmCardWhite
        ),
        border = BorderStroke(
            width = if (isRecommended) 2.dp else 1.dp,
            color = if (isRecommended) SecondaryDustPink else SoftBorderPlum
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            if (isRecommended) {
                Box(
                    modifier = Modifier
                        .background(SecondaryDustPink, RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text("RECOMENDADO PARA MADRES", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = PrimaryDeepPurple)
                Text(text = price, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = SecondaryDustPink)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Divider(color = SoftBorderPlum)
            Spacer(modifier = Modifier.height(12.dp))

            benefits.forEach { benefit ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Icon(Icons.Default.Check, contentDescription = null, tint = TerciaryWarmTeal, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = benefit, fontSize = 12.sp, color = DeepCharcoalText)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onPlanSelected,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isRecommended) SecondaryDustPink else PrimaryDeepPurple.copy(alpha = 0.1f)
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = buttonText,
                    color = if (isRecommended) Color.White else PrimaryDeepPurple,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
        }
    }
}

// Onboarding Screen Components
@Composable
fun OnboardingScreen(
    preselectedPlan: String = "GRATIS",
    onFinished: (name: String, stage: String, weeksOrMonths: Int, concern: String, optWhatsApp: Boolean, reminderFreq: String, plan: String, age: Int, supportNetwork: String, previousPregnancies: Int, location: String) -> Unit,
    onBack: () -> Unit
) {
    var onboardingStep by remember { mutableStateOf(1) }
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf(28) }
    var location by remember { mutableStateOf("") }
    
    var stage by remember { mutableStateOf("EMBARAZADA") } // "EMBARAZADA" or "POSPARTO"
    var weeksOrMonths by remember { mutableStateOf(12) } // default 12 weeks
    var previousPregnancies by remember { mutableStateOf(0) }
    
    var supportNetwork by remember { mutableStateOf("Pareja") } // "Pareja", "Familia/Amigos", "Ninguna"
    var concern by remember { mutableStateOf("Cambios de humor / Ansiedad") }
    
    var optWhatsApp by remember { mutableStateOf(true) }
    var reminderFrequency by remember { mutableStateOf("Diario") }
    var selectedPlan by remember { mutableStateOf(preselectedPlan) }

    val concernsList = listOf(
        "Cambios de humor / Ansiedad",
        "Calidad del sueño",
        "Lactancia y nutrición del bebé",
        "Dolores físicos / Cansancio",
        "Apoyo familiar y soledad"
    )

    // Sync selectedPlan state if preselectedPlan changes
    LaunchedEffect(preselectedPlan) {
        selectedPlan = preselectedPlan
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(CreamBackground)
            .padding(20.dp)
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        // TOP HEADER ROW
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = {
                    if (onboardingStep > 1) {
                        onboardingStep--
                    } else {
                        onBack()
                    }
                }
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar", tint = PrimaryDeepPurple)
            }
            Text(
                text = "Creando Tu Cuenta Tribu",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = PrimaryDeepPurple,
                fontFamily = FontFamily.Serif,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "Paso $onboardingStep de 4",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = SecondaryDustPink
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Progress bar reflecting steps beautifully
        val progressVal = when (onboardingStep) {
            1 -> 0.25f
            2 -> 0.50f
            3 -> 0.75f
            else -> 1.0f
        }
        LinearProgressIndicator(
            progress = progressVal,
            color = SecondaryDustPink,
            trackColor = SoftLila,
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp))
        )

        Spacer(modifier = Modifier.height(24.dp))

        when (onboardingStep) {
            1 -> {
                // STEP 1: PERSONAL DATA
                Text(
                    text = "Dinos más sobre ti...",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryDeepPurple,
                    fontFamily = FontFamily.Serif
                )
                Text(
                    text = "Queremos conocerte para brindarte alertas y contención a tu medida.",
                    fontSize = 12.sp,
                    color = MutedSlateSub
                )

                Spacer(modifier = Modifier.height(24.dp))

                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = WarmCardWhite),
                    border = BorderStroke(1.dp, SoftBorderPlum),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = "Nombre Completo",
                            fontWeight = FontWeight.Bold,
                            color = PrimaryDeepPurple,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            placeholder = { Text("Escribe tu nombre...") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("onboarding_name_field"),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryDeepPurple,
                                unfocusedBorderColor = SoftBorderPlum
                            )
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Age selector
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "¿Cuál es tu edad?",
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryDeepPurple,
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "Edad en años",
                                    fontSize = 11.sp,
                                    color = MutedSlateSub
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .background(SoftLila, RoundedCornerShape(12.dp))
                                    .padding(horizontal = 4.dp)
                            ) {
                                IconButton(onClick = { if (age > 15) age-- }) {
                                    Text("-", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = PrimaryDeepPurple)
                                }
                                Text(
                                    text = "$age",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = PrimaryDeepPurple,
                                    modifier = Modifier.padding(horizontal = 10.dp)
                                )
                                IconButton(onClick = { if (age < 55) age++ }) {
                                    Text("+", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = PrimaryDeepPurple)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Location
                        Text(
                            text = "¿Dónde resides?",
                            fontWeight = FontWeight.Bold,
                            color = PrimaryDeepPurple,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = location,
                            onValueChange = { location = it },
                            placeholder = { Text("Ciudad, País o Localidad...") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryDeepPurple,
                                unfocusedBorderColor = SoftBorderPlum
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { onboardingStep = 2 },
                    enabled = name.trim().isNotBlank() && location.trim().isNotBlank(),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryDeepPurple),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .testTag("onboarding_step1_next")
                ) {
                    Text("Continuar a Paso 2", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color.White)
                }
            }
            2 -> {
                // STEP 2: MOTHERHOOD STATE
                Text(
                    text = "Tu Maternidad...",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryDeepPurple,
                    fontFamily = FontFamily.Serif
                )
                Text(
                    text = "Personalizaremos tu experiencia según tu etapa actual.",
                    fontSize = 12.sp,
                    color = MutedSlateSub
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Stage selection (Embarazada or Posparto)
                Text("¿En qué etapa te encuentras?", fontWeight = FontWeight.Bold, color = PrimaryDeepPurple, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = if (stage == "EMBARAZADA") SoftLila else WarmCardWhite
                        ),
                        border = BorderStroke(1.dp, if (stage == "EMBARAZADA") PrimaryDeepPurple else SoftBorderPlum),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { stage = "EMBARAZADA" },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("🤰", fontSize = 28.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Embarazada", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = PrimaryDeepPurple)
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = if (stage == "POSPARTO") SoftLila else WarmCardWhite
                        ),
                        border = BorderStroke(1.dp, if (stage == "POSPARTO") PrimaryDeepPurple else SoftBorderPlum),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { stage = "POSPARTO" },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("🤱", fontSize = 28.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Posparto", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = PrimaryDeepPurple)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Weeks/Months counter card
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = WarmCardWhite),
                    border = BorderStroke(1.dp, SoftBorderPlum),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (stage == "EMBARAZADA") "¿Cuántas semanas tienes?" else "¿Cuántos meses después del parto?",
                            fontWeight = FontWeight.Bold,
                            color = PrimaryDeepPurple,
                            fontSize = 13.sp,
                            modifier = Modifier.weight(1f)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.background(SoftLila, RoundedCornerShape(12.dp))
                        ) {
                            IconButton(onClick = { if (weeksOrMonths > 1) weeksOrMonths-- }) {
                                Text("-", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = PrimaryDeepPurple)
                            }
                            Text(
                                text = "$weeksOrMonths",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = PrimaryDeepPurple,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                            IconButton(onClick = { weeksOrMonths++ }) {
                                Text("+", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = PrimaryDeepPurple)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Previous pregnancies
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = WarmCardWhite),
                    border = BorderStroke(1.dp, SoftBorderPlum),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "¿Cuántos embarazos previos has tenido?",
                                fontWeight = FontWeight.Bold,
                                color = PrimaryDeepPurple,
                                fontSize = 13.sp
                            )
                            Text(
                                text = "Sin contar el actual",
                                fontSize = 11.sp,
                                color = MutedSlateSub
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.background(SoftLila, RoundedCornerShape(12.dp))
                        ) {
                            IconButton(onClick = { if (previousPregnancies > 0) previousPregnancies-- }) {
                                Text("-", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = PrimaryDeepPurple)
                            }
                            Text(
                                text = "$previousPregnancies",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = PrimaryDeepPurple,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                            IconButton(onClick = { if (previousPregnancies < 15) previousPregnancies++ }) {
                                Text("+", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = PrimaryDeepPurple)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedButton(
                        onClick = { onboardingStep = 1 },
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, PrimaryDeepPurple)
                    ) {
                        Text("Atrás", fontWeight = FontWeight.Bold, color = PrimaryDeepPurple)
                    }
                    Button(
                        onClick = { onboardingStep = 3 },
                        modifier = Modifier.weight(1.5f).height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryDeepPurple)
                    ) {
                        Text("Siguiente", fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }
            3 -> {
                // STEP 3: SUPPORT NETWORK & CONCERNS
                Text(
                    text = "Tu Red de Apoyo...",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryDeepPurple,
                    fontFamily = FontFamily.Serif
                )
                Text(
                    text = "El acompañamiento es vital en el proceso maternal.",
                    fontSize = 12.sp,
                    color = MutedSlateSub
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Support Network option cards
                Text("¿Cuál es tu principal red de apoyo actual?", fontWeight = FontWeight.Bold, color = PrimaryDeepPurple, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(10.dp))
                
                val networks = listOf(
                    "Pareja" to "💖 Cuento con el apoyo activo de mi pareja.",
                    "Familia/Amigos" to "🏘️ Mi familia o amigos cercanos me acompañan.",
                    "Ninguna" to "🥀 Siento que no tengo red de apoyo o es muy limitada."
                )

                networks.forEach { (key, title) ->
                    val isSel = supportNetwork == key
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSel) SoftLila else WarmCardWhite
                        ),
                        border = BorderStroke(1.dp, if (isSel) PrimaryDeepPurple else SoftBorderPlum),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { supportNetwork = key }
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = isSel,
                                onClick = { supportNetwork = key },
                                colors = RadioButtonDefaults.colors(selectedColor = SecondaryDustPink)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = title,
                                fontSize = 13.sp,
                                fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                                color = DeepCharcoalText
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Primary Concern
                Text("¿Cuál es tu principal preocupación actual?", fontWeight = FontWeight.Bold, color = PrimaryDeepPurple, fontSize = 13.sp)
                Spacer(modifier = Modifier.height(8.dp))
                concernsList.forEach { item ->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = if (concern == item) SoftLila.copy(alpha = 0.5f) else WarmCardWhite
                        ),
                        border = BorderStroke(1.dp, if (concern == item) SecondaryDustPink else SoftBorderPlum),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { concern = item }
                            .padding(vertical = 3.dp),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (concern == item),
                                onClick = { concern = item },
                                colors = RadioButtonDefaults.colors(selectedColor = SecondaryDustPink)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = item, fontSize = 13.sp, color = DeepCharcoalText)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedButton(
                        onClick = { onboardingStep = 2 },
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, PrimaryDeepPurple)
                    ) {
                        Text("Atrás", fontWeight = FontWeight.Bold, color = PrimaryDeepPurple)
                    }
                    Button(
                        onClick = { onboardingStep = 4 },
                        modifier = Modifier.weight(1.5f).height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryDeepPurple)
                    ) {
                        Text("Siguiente", fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }
            4 -> {
                // STEP 4: ALERTS & PLANS
                Text(
                    text = "Acompañamiento...",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryDeepPurple,
                    fontFamily = FontFamily.Serif
                )
                Text(
                    text = "Selecciona tus canales y configúralos a tu medida.",
                    fontSize = 12.sp,
                    color = MutedSlateSub
                )

                Spacer(modifier = Modifier.height(20.dp))

                // WhatsApp opt-in
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = SageGreen.copy(alpha = 0.4f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Acompañamiento por WhatsApp", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = PrimaryDeepPurple)
                                Text("Te enviaremos alertas cariñosas y recordatorios de salud.", fontSize = 11.sp, color = DeepCharcoalText)
                            }
                            Switch(
                                checked = optWhatsApp,
                                onCheckedChange = { optWhatsApp = it },
                                colors = SwitchDefaults.colors(checkedThumbColor = SecondaryDustPink)
                            )
                        }

                        if (optWhatsApp) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Text("Frecuencia sugerida de alertas:", fontWeight = FontWeight.SemiBold, fontSize = 12.sp, color = MutedSlateSub)
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                listOf("Diario", "Dos veces", "Semanal").forEach { freq ->
                                    val isSel = reminderFrequency == freq
                                    AssistChip(
                                        onClick = { reminderFrequency = freq },
                                        label = { Text(freq, fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                                        colors = AssistChipDefaults.assistChipColors(
                                            containerColor = if (isSel) SoftLila else Color.Transparent,
                                            labelColor = if (isSel) PrimaryDeepPurple else DeepCharcoalText
                                        )
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Plan Choice
                Text("Plan de Beneficios Tribu:", fontWeight = FontWeight.Bold, color = PrimaryDeepPurple, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = if (selectedPlan == "GRATIS") SoftLila else WarmCardWhite
                        ),
                        border = BorderStroke(if (selectedPlan == "GRATIS") 2.dp else 1.dp, if (selectedPlan == "GRATIS") PrimaryDeepPurple else SoftBorderPlum),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { selectedPlan = "GRATIS" },
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text("Plan Free", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = PrimaryDeepPurple)
                            Text("Gratis", fontWeight = FontWeight.ExtraBold, fontSize = 15.sp, color = SecondaryDustPink)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text("• Historial limitado\n• Citas médicas locales\n• Guardado manual", fontSize = 9.sp, color = DeepCharcoalText, lineHeight = 12.sp)
                        }
                    }

                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = if (selectedPlan != "GRATIS") SoftLila else WarmCardWhite
                        ),
                        border = BorderStroke(if (selectedPlan != "GRATIS") 2.dp else 1.dp, if (selectedPlan != "GRATIS") SecondaryDustPink else SoftBorderPlum),
                        modifier = Modifier
                            .weight(1.2f)
                            .clickable { selectedPlan = "MENSUAL" },
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Tribu Premium", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = PrimaryDeepPurple)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("⭐", fontSize = 10.sp)
                            }
                            Text("$9.99 / m", fontWeight = FontWeight.ExtraBold, fontSize = 15.sp, color = SecondaryDustPink)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text("• Check-ins WhatsApp\n• Escaneo OCR Recetas\n• IA Contención Gemini\n• Historial Infinito", fontSize = 9.sp, color = DeepCharcoalText, lineHeight = 12.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(36.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedButton(
                        onClick = { onboardingStep = 3 },
                        modifier = Modifier.weight(1f).height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, PrimaryDeepPurple)
                    ) {
                        Text("Atrás", fontWeight = FontWeight.Bold, color = PrimaryDeepPurple)
                    }
                    Button(
                        onClick = {
                            if (name.trim().isNotBlank()) {
                                onFinished(name, stage, weeksOrMonths, concern, optWhatsApp, reminderFrequency, selectedPlan, age, supportNetwork, previousPregnancies, location)
                            }
                        },
                        enabled = name.trim().isNotBlank(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryDeepPurple),
                        modifier = Modifier
                            .weight(1.8f)
                            .height(50.dp)
                            .testTag("complete_onboarding_btn")
                    ) {
                        Text("Activar Mi Tribu 🌸", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.White)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

// Simulated Authentication Screen Component (Login / Register) before Onboarding
@Composable
fun AuthScreen(
    viewModel: TribuMentalViewModel,
    onAuthSuccess: (email: String, isRegistered: Boolean) -> Unit,
    onBack: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var isRegisterMode by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    var showGooglePicker by remember { mutableStateOf(false) }
    var googleConnecting by remember { mutableStateOf(false) }
    var googleSelectedAccount by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(CreamBackground)
            .padding(24.dp)
            .windowInsetsPadding(WindowInsets.safeDrawing),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar", tint = PrimaryDeepPurple)
            }
            Text(
                text = if (isRegisterMode) "Crear tu Cuenta" else "Iniciar Sesión",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = PrimaryDeepPurple,
                fontFamily = FontFamily.Serif
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Brand icon circle
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(SoftLila),
            contentAlignment = Alignment.Center
        ) {
            Text(if (isRegisterMode) "🌸" else "👶", fontSize = 32.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Bienvenida a tu Tribu",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryDeepPurple,
            fontFamily = FontFamily.Serif,
            textAlign = TextAlign.Center
        )

        Text(
            text = if (isRegisterMode) 
                "Únete a miles de mamás y recibe acompañamiento emocional en WhatsApp" 
                else "Conéctate de nuevo a tu red de apoyo y seguimiento médico",
            fontSize = 12.sp,
            color = MutedSlateSub,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = WarmCardWhite),
            border = BorderStroke(1.dp, SoftBorderPlum),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                if (isRegisterMode) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it ; errorMessage = "" },
                        label = { Text("Nombre Completo") },
                        placeholder = { Text("Escribe tu nombre...") },
                        modifier = Modifier.fillMaxWidth().testTag("auth_name_field"),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryDeepPurple,
                            unfocusedBorderColor = SoftBorderPlum
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it ; errorMessage = "" },
                        label = { Text("Teléfono / WhatsApp") },
                        placeholder = { Text("+57 300 123 4567") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier.fillMaxWidth().testTag("auth_phone_field"),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryDeepPurple,
                            unfocusedBorderColor = SoftBorderPlum
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it; errorMessage = "" },
                    label = { Text("Correo Electrónico") },
                    placeholder = { Text("ejemplo@gmail.com") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth().testTag("auth_email_field"),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryDeepPurple,
                        unfocusedBorderColor = SoftBorderPlum
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it; errorMessage = "" },
                    label = { Text("Contraseña (min. 6 caracteres)") },
                    placeholder = { Text("••••••••") },
                    modifier = Modifier.fillMaxWidth().testTag("auth_pass_field"),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryDeepPurple,
                        unfocusedBorderColor = SoftBorderPlum
                    )
                )

                if (errorMessage.isNotBlank()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = errorMessage, color = AlertSoftRed, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(24.dp))

                if (isLoading) {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = SecondaryDustPink, modifier = Modifier.size(24.dp), strokeWidth = 2.5.dp)
                    }
                } else {
                    Button(
                        onClick = {
                            if (email.isBlank() || password.length < 6 || (isRegisterMode && (name.isBlank() || phone.isBlank()))) {
                                errorMessage = "Por favor completa todos los campos correctamente."
                            } else {
                                isLoading = true
                                viewModel.setAuthLoading(true)
                                coroutineScope.launch {
                                    if (isRegisterMode) {
                                        com.example.services.FirebaseAuthService.registerUser(email, password, name) { success, errorMsg ->
                                            if (success) {
                                                viewModel.loginGoogleUser(email, name) { isAlreadyOnboarded ->
                                                    isLoading = false
                                                    viewModel.setAuthLoading(false)
                                                    onAuthSuccess(email, isAlreadyOnboarded)
                                                }
                                            } else {
                                                isLoading = false
                                                viewModel.setAuthLoading(false)
                                                errorMessage = errorMsg ?: "Error al registrar usuario en Firebase"
                                            }
                                        }
                                    } else {
                                        com.example.services.FirebaseAuthService.signInUser(email, password) { success, errorMsg ->
                                            if (success) {
                                                viewModel.loginGoogleUser(email, "") { isAlreadyOnboarded ->
                                                    isLoading = false
                                                    viewModel.setAuthLoading(false)
                                                    onAuthSuccess(email, isAlreadyOnboarded)
                                                }
                                            } else {
                                                isLoading = false
                                                viewModel.setAuthLoading(false)
                                                errorMessage = errorMsg ?: "Error al iniciar sesión en Firebase"
                                            }
                                        }
                                    }
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryDeepPurple),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("auth_action_btn")
                    ) {
                        Text(
                            text = if (isRegisterMode) "Registrarme Ahora" else "Entrar a TribuMental",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Divider(modifier = Modifier.weight(1f), color = SoftBorderPlum.copy(alpha = 0.5f))
                        Text(
                            text = "o continúa con",
                            fontSize = 11.sp,
                            color = MutedSlateSub,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                        Divider(modifier = Modifier.weight(1f), color = SoftBorderPlum.copy(alpha = 0.5f))
                    }

                    OutlinedButton(
                        onClick = {
                            showGooglePicker = true
                        },
                        border = BorderStroke(1.dp, SoftBorderPlum),
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("google_auth_btn")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .background(Color(0xFFF1F3F4), CircleShape)
                                    .border(BorderStroke(1.dp, Color(0xFFDADCE0)), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("G", fontWeight = FontWeight.Black, fontSize = 12.sp, color = Color(0xFF4285F4))
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = if (isRegisterMode) "Registrarse con Google" else "Acceder con Google",
                                color = DeepCharcoalText,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Toggle auth modes
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = if (isRegisterMode) "¿Ya posees una cuenta?" else "¿Eres nueva en la red?",
                fontSize = 12.sp,
                color = DeepCharcoalText
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = if (isRegisterMode) "Inicia Sesión" else "Regístrate gratis",
                fontSize = 12.sp,
                fontWeight = FontWeight.ExtraBold,
                color = PrimaryDeepPurple,
                modifier = Modifier
                    .clickable { 
                        isRegisterMode = !isRegisterMode
                        errorMessage = ""
                    }
                    .testTag("toggle_auth_mode")
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Security / HIPAA compliance badge
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(SageGreen.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Icon(Icons.Default.Lock, contentDescription = null, tint = TerciaryWarmTeal, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Tus datos médicos y de diario están encriptados localmente.",
                fontSize = 10.sp,
                color = DeepCharcoalText
            )
        }

        if (showGooglePicker) {
            androidx.compose.ui.window.Dialog(
                onDismissRequest = { if (!googleConnecting) showGooglePicker = false }
            ) {
                Card(
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color(0xFFDADCE0)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .testTag("google_account_picker_dialog")
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Google",
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            fontFamily = FontFamily.SansSerif,
                            color = Color(0xFF4285F4),
                            letterSpacing = (-0.5).sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        
                        Text(
                            text = "Elegir una cuenta",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = DeepCharcoalText
                        )
                        
                        Text(
                            text = "para continuar a TribuMental",
                            fontSize = 12.sp,
                            color = MutedSlateSub,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        if (googleConnecting) {
                            Spacer(modifier = Modifier.height(16.dp))
                            CircularProgressIndicator(
                                color = Color(0xFF4285F4),
                                modifier = Modifier.size(36.dp),
                                strokeWidth = 3.dp
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Accediendo con la cuenta de Google...",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = PrimaryDeepPurple,
                                textAlign = TextAlign.Center
                            )
                        } else {
                            AccountItemRow(
                                name = "Cristian Mosquera",
                                email = "cristianmosquera.fhco@gmail.com",
                                avatarColor = Color(0xFFE2F0D9),
                                avatarText = "C",
                                onClick = {
                                    googleConnecting = true
                                    googleSelectedAccount = "cristianmosquera.fhco@gmail.com"
                                    simulateAuthConnection {
                                        viewModel.loginGoogleUser("cristianmosquera.fhco@gmail.com", "Cristian Mosquera") { isAlreadyOnboarded ->
                                            googleConnecting = false
                                            showGooglePicker = false
                                            onAuthSuccess("cristianmosquera.fhco@gmail.com", isAlreadyOnboarded)
                                        }
                                    }
                                }
                            )

                            Divider(color = Color(0xFFF1F3F4), thickness = 1.dp)

                            AccountItemRow(
                                name = "Camila Silva (Tribu Mamá)",
                                email = "mama.tribu@gmail.com",
                                avatarColor = Color(0xFFFCE4D6),
                                avatarText = "C",
                                onClick = {
                                    googleConnecting = true
                                    googleSelectedAccount = "mama.tribu@gmail.com"
                                    simulateAuthConnection {
                                        viewModel.loginGoogleUser("mama.tribu@gmail.com", "Camila Silva") { isAlreadyOnboarded ->
                                            googleConnecting = false
                                            showGooglePicker = false
                                            onAuthSuccess("mama.tribu@gmail.com", isAlreadyOnboarded)
                                        }
                                    }
                                }
                            )

                            Divider(color = Color(0xFFF1F3F4), thickness = 1.dp)

                            AccountItemRow(
                                name = "Invitada Tribu",
                                email = "invitada.tribu@gmail.com",
                                avatarColor = Color(0xFFFFF2CC),
                                avatarText = "I",
                                onClick = {
                                    googleConnecting = true
                                    googleSelectedAccount = "invitada.tribu@gmail.com"
                                    simulateAuthConnection {
                                        viewModel.loginGoogleUser("invitada.tribu@gmail.com", "Invitada Tribu") { isAlreadyOnboarded ->
                                            googleConnecting = false
                                            showGooglePicker = false
                                            onAuthSuccess("invitada.tribu@gmail.com", isAlreadyOnboarded)
                                        }
                                    }
                                }
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "Para continuar, Google compartirá tu nombre, correo electrónico, foto de perfil y preferencia de idioma con TribuMental. Consulta la Política de Privacidad.",
                                fontSize = 9.sp,
                                color = MutedSlateSub,
                                lineHeight = 12.sp,
                                textAlign = TextAlign.Start
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            TextButton(
                                onClick = { showGooglePicker = false },
                                modifier = Modifier.align(Alignment.End)
                            ) {
                                Text("Cancelar", color = Color(0xFF5F6368), fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AccountItemRow(
    name: String,
    email: String,
    avatarColor: Color,
    avatarText: String,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 4.dp)
            .testTag("google_account_row_${email.replace(".", "_")}")
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(avatarColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = avatarText,
                fontWeight = FontWeight.Bold,
                color = DeepCharcoalText,
                fontSize = 14.sp
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = name,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = DeepCharcoalText
            )
            Text(
                text = email,
                fontSize = 11.sp,
                color = MutedSlateSub
            )
        }
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = null,
            tint = MutedSlateSub,
            modifier = Modifier.size(16.dp)
        )
    }
}

// Simple handler postDelayed simulator
fun simulateAuthConnection(onCompleted: () -> Unit) {
    android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
        onCompleted()
    }, 1200)
}

// MAIN DASHBOARD COMPONENT
@Composable
fun DashboardScreen(
    viewModel: TribuMentalViewModel,
    onNavigateToCheckIn: () -> Unit,
    onNavigateToCalendar: () -> Unit,
    onNavigateToDocuments: () -> Unit,
    onNavigateToWhatsApp: () -> Unit,
    onNavigateToSubscriptions: () -> Unit,
    onNavigateEmergency: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToPsyTest: () -> Unit
) {
    val profile by viewModel.userProfile.collectAsStateWithLifecycle()
    val moods by viewModel.moodHistory.collectAsStateWithLifecycle()
    val appointmentsList by viewModel.appointments.collectAsStateWithLifecycle()
    val context = LocalContext.current

    var inlineSelectedScore by remember { mutableStateOf<Int?>(null) }
    var inlineNotes by remember { mutableStateOf("") }
    var inlineIsSubmitted by remember { mutableStateOf(false) }
    var inlineIsSaving by remember { mutableStateOf(false) }
    var inlineFeedback by remember { mutableStateOf("") }

    val nextAppointment = remember(appointmentsList) {
        appointmentsList.firstOrNull { it.status == "PROGRAMADA" && it.dateTime > System.currentTimeMillis() }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(CreamBackground)
            .padding(16.dp)
    ) {
        // Safe spacing from notch
        Spacer(modifier = Modifier.height(16.dp))

        // Interactive Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Hola, ${profile?.name ?: "Mamá"} 🌸",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = PrimaryDeepPurple,
                    fontFamily = FontFamily.Serif
                )
                Text(
                    text = if (profile?.stage == "EMBARAZADA") {
                        "Semana ${profile?.weeksOrMonths} de Embarazo"
                    } else {
                        "Mes ${profile?.weeksOrMonths} del Posparto"
                    },
                    fontSize = 13.sp,
                    color = MutedSlateSub,
                    fontWeight = FontWeight.Medium
                )
            }

            // Small profile card click and theme toggle row
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onNavigateToProfile,
                    modifier = Modifier
                        .size(44.dp)
                        .testTag("dashboard_profile_button")
                ) {
                    UserAvatar(
                        avatarPath = profile?.avatarUri,
                        size = 44.dp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Subscription Tier Banner
        val currentPlan = profile?.billingPlan ?: "GRATIS"
        if (profile?.isPremium == true) {
            Card(
                colors = CardDefaults.cardColors(containerColor = SoftLila.copy(alpha = 0.5f)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("✨", fontSize = 18.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Miembro Premium - Plan $currentPlan",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = PrimaryDeepPurple
                        )
                    }
                    Text(
                        text = "Activo",
                        color = MoodVibrantGreen,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 11.sp
                    )
                }
            }
        } else {
            Card(
                colors = CardDefaults.cardColors(containerColor = PowderPink),
                border = BorderStroke(1.dp, SecondaryDustPink.copy(alpha = 0.5f)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Acompañamiento Premium inactivo",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = PrimaryDeepPurple
                        )
                        Text(
                            text = "Activa WhatsApp alertas y OCR hoy",
                            fontSize = 10.sp,
                            color = MutedSlateSub
                        )
                    }
                    Button(
                        onClick = onNavigateToSubscriptions,
                        colors = ButtonDefaults.buttonColors(containerColor = SecondaryDustPink),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text("Mejorar", fontSize = 11.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // PANEL DE ESTADO MENTAL Y RECOMENDACIONES (ACTIVE CONTROL PANEL COMPONENT)
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = WarmCardWhite),
            border = BorderStroke(1.5.dp, SoftLila),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("mental_score_dashboard_panel")
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(SecondaryDustPink.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("🧠", fontSize = 24.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Panel de Control de Bienestar",
                            fontWeight = FontWeight.Bold,
                            color = PrimaryDeepPurple,
                            fontFamily = FontFamily.Serif,
                            fontSize = 16.sp
                        )
                        Text(
                            text = "Estado Mental y Recomendaciones Activas",
                            color = SecondaryDustPink,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                val hasScore = profile?.testScore != null
                if (hasScore) {
                    val score = profile?.testScore ?: 0
                    val (label, colorAccent, bgAccent) = when (score) {
                        in 9..10 -> Triple("Bienestar Óptimo 🌸", SageGreen, SageGreen.copy(alpha = 0.2f))
                        in 7..8 -> Triple("Bienestar Moderado ✨", SecondaryDustPink, SoftLila.copy(alpha = 0.3f))
                        in 5..6 -> Triple("Estrés Leve ⚠️", Color(0xFFF2CC8F), Color(0xFFFDF6EC))
                        else -> Triple("Contención Prioritaria 🚨", AlertSoftRed, AlertSoftRed.copy(alpha = 0.15f))
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(bgAccent, RoundedCornerShape(16.dp))
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.size(64.dp)
                        ) {
                            CircularProgressIndicator(
                                progress = { score / 10f },
                                modifier = Modifier.fillMaxSize(),
                                color = colorAccent,
                                strokeWidth = 6.dp,
                                trackColor = Color.White.copy(alpha = 0.6f)
                            )
                            Text(
                                text = "$score/10",
                                fontWeight = FontWeight.Black,
                                fontSize = 15.sp,
                                color = PrimaryDeepPurple
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            Text(
                                text = label,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = PrimaryDeepPurple
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Evaluación integral de tu salud emocional actual.",
                                fontSize = 11.sp,
                                color = MutedSlateSub,
                                lineHeight = 14.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "📋 Recomendaciones Activas a Seguir:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = PrimaryDeepPurple
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    val recsString = profile?.trackedRecommendations ?: ""
                    if (recsString.isNotBlank()) {
                        val list = recsString.split("||")
                        list.forEach { item ->
                            val parts = item.split(":")
                            if (parts.size >= 2) {
                                val recText = parts[0]
                                val isCompleted = parts[1].toBoolean()

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            viewModel.toggleTrackedRecommendation(recText, !isCompleted)
                                        }
                                        .padding(vertical = 6.dp, horizontal = 4.dp)
                                        .background(
                                            if (isCompleted) Color(0xFFF1F5F3) else Color.Transparent,
                                            RoundedCornerShape(8.dp)
                                        )
                                        .padding(horizontal = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Checkbox(
                                        checked = isCompleted,
                                        onCheckedChange = {
                                            viewModel.toggleTrackedRecommendation(recText, it)
                                        },
                                        colors = CheckboxDefaults.colors(checkedColor = colorAccent)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = recText,
                                        fontSize = 12.sp,
                                        color = if (isCompleted) MutedSlateSub else DeepCharcoalText,
                                        lineHeight = 16.sp,
                                        style = if (isCompleted) TextStyle(textDecoration = TextDecoration.LineThrough) else TextStyle.Default,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }
                    } else {
                        Text(
                            text = "No hay recomendaciones activas. Por favor, re-haz el test.",
                            fontSize = 11.sp,
                            color = MutedSlateSub
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedButton(
                        onClick = onNavigateToPsyTest,
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = PrimaryDeepPurple),
                        border = BorderStroke(1.2.dp, PrimaryDeepPurple),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .testTag("retake_psy_test_from_dash")
                    ) {
                        Text(
                            text = "🔄 Re-evaluar mi Estado Mental",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.5.sp
                        )
                    }

                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp)
                    ) {
                        Text(
                            text = "Aún no has medido tu Estado Mental",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = PrimaryDeepPurple
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Realiza el Screening Psicológico Confidencial de TribuMental para calcular tu puntaje, conocer recomendaciones personalizadas y recibir contención adaptada a tu semana de maternidad.",
                            fontSize = 11.sp,
                            color = DeepCharcoalText,
                            textAlign = TextAlign.Center,
                            lineHeight = 16.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = onNavigateToPsyTest,
                            colors = ButtonDefaults.buttonColors(containerColor = SecondaryDustPink),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                                .testTag("take_first_psy_test_btn")
                        ) {
                            Text(
                                text = "🧠 Comenzar Test de Screening",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.5.sp
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // FLOATING ACTION CHECK-IN TRIGGER (INTERACTIVE DAILY EMOTIONAL CHECK-IN COMPONENT)
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = WarmCardWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            border = BorderStroke(1.dp, SoftBorderPlum),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                if (!inlineIsSubmitted) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(SecondaryDustPink)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Check-In Emocional Diario",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryDeepPurple,
                                fontFamily = FontFamily.Serif
                            )
                        }
                        Box(
                            modifier = Modifier
                                .background(SageGreen, RoundedCornerShape(8.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "HOY",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = DeepCharcoalText
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Toma conciencia de tu respiración y selecciona el estado que mejor represente tu ánimo actual:",
                        fontSize = 11.sp,
                        color = MutedSlateSub,
                        lineHeight = 15.sp
                    )
                    Spacer(modifier = Modifier.height(14.dp))

                    // Interactable emoji row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        val moodsSet = listOf(
                            Triple(1, "😭", "Abrumada"),
                            Triple(2, "😔", "Triste"),
                            Triple(3, "😐", "Cansada"),
                            Triple(4, "🙂", "Alegre"),
                            Triple(5, "🌸", "Plena")
                        )

                        moodsSet.forEach { moodsItem ->
                            val isSelected = inlineSelectedScore == moodsItem.first
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(if (isSelected) SoftLila else Color.Transparent)
                                    .border(
                                        width = if (isSelected) 1.5.dp else 1.dp,
                                        color = if (isSelected) PrimaryDeepPurple else SoftBorderPlum.copy(alpha = 0.5f),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clickable { inlineSelectedScore = moodsItem.first }
                                    .padding(vertical = 10.dp, horizontal = 4.dp)
                                    .width(52.dp)
                            ) {
                                Text(text = moodsItem.second, fontSize = 28.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = moodsItem.third,
                                    fontSize = 8.5.sp,
                                    fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                                    color = PrimaryDeepPurple,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }

                    // Interactive Note form expands beautifully once a score is selected
                    AnimatedVisibility(
                        visible = inlineSelectedScore != null,
                        enter = expandVertically() + fadeIn(),
                        exit = shrinkVertically() + fadeOut()
                    ) {
                        Column {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "¿Quieres añadir una breve nota sobre lo que estás viviendo hoy?",
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryDeepPurple
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            OutlinedTextField(
                                value = inlineNotes,
                                onValueChange = { inlineNotes = it },
                                placeholder = { Text("Escribe aquí tu pensamiento o diario breve...", fontSize = 11.sp, color = MutedSlateSub) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(76.dp)
                                    .testTag("inline_mood_note_field"),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = PrimaryDeepPurple,
                                    unfocusedBorderColor = SoftBorderPlum
                                )
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            if (inlineIsSaving) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    CircularProgressIndicator(color = SecondaryDustPink, modifier = Modifier.size(24.dp), strokeWidth = 2.5.dp)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Sincronizando y guardando con IA...", fontSize = 12.sp, color = MutedSlateSub)
                                }
                            } else {
                                Button(
                                    onClick = {
                                        if (inlineSelectedScore != null) {
                                            inlineIsSaving = true
                                            viewModel.performMoodCheckIn(inlineSelectedScore!!, inlineNotes) { advice ->
                                                inlineFeedback = advice
                                                inlineIsSaving = false
                                                inlineIsSubmitted = true
                                            }
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryDeepPurple),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(44.dp)
                                        .testTag("inline_save_mood_btn")
                                ) {
                                    Icon(Icons.Default.Favorite, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Guardar Registro Emocional", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                }
                            }
                        }
                    }
                } else {
                    // SUCCESS STATE DISPLAYED DIRECTLY ON THE DASHBOARD INLINE
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "🎉 ¡Check-In Exitoso!",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryDeepPurple,
                            fontFamily = FontFamily.Serif
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Tu bienestar ha sido registrado y respaldado en tu base de datos local.",
                            fontSize = 11.sp,
                            color = MutedSlateSub,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        // Small premium card context
                        Card(
                            colors = CardDefaults.cardColors(containerColor = SoftLila.copy(alpha = 0.5f)),
                            border = BorderStroke(1.dp, PrimaryDeepPurple.copy(alpha = 0.15f)),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                val selectedMoodText = when (inlineSelectedScore) {
                                    1 -> "😭 Abrumada"
                                    2 -> "😔 Triste"
                                    3 -> "😐 Cansada"
                                    4 -> "🙂 Alegre"
                                    5 -> "🌸 Plena"
                                    else -> "🌸 Plena"
                                }
                                Text(
                                    text = "Valoración: $selectedMoodText",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryDeepPurple
                                )
                                if (inlineFeedback.isNotBlank()) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = inlineFeedback,
                                        fontSize = 12.sp,
                                        color = DeepCharcoalText,
                                        lineHeight = 17.sp,
                                        textAlign = TextAlign.Center,
                                        fontFamily = FontFamily.Serif
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            OutlinedButton(
                                onClick = {
                                    inlineIsSubmitted = false
                                    inlineNotes = ""
                                    inlineSelectedScore = null
                                    inlineFeedback = ""
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp),
                                border = BorderStroke(1.dp, PrimaryDeepPurple)
                            ) {
                                Text("Volver a Medir", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = PrimaryDeepPurple)
                            }

                            Button(
                                onClick = onNavigateToCheckIn,
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = PrimaryDeepPurple),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("Ver Historial", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Next Medical Appointment Box
        Text(
            text = "Próxima Cita de Control",
            fontWeight = FontWeight.Bold,
            color = PrimaryDeepPurple,
            fontSize = 15.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (nextAppointment != null) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = WarmCardWhite),
                border = BorderStroke(1.dp, SoftBorderPlum),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigateToCalendar() }
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(SageGreen),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = when (nextAppointment.type) {
                                "Control Prenatal" -> "🤰"
                                "Pediatría" -> "👶"
                                "Psicología" -> "🧠"
                                "Laboratorio" -> "🧪"
                                else -> "📅"
                            },
                            fontSize = 22.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = nextAppointment.title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = PrimaryDeepPurple)
                        Text(text = "${nextAppointment.type} • Dr(a). ${nextAppointment.provider}", fontSize = 11.sp, color = MutedSlateSub)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = formatTimestamp(nextAppointment.dateTime),
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 11.sp,
                            color = SecondaryDustPink
                        )
                    }
                    Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Ver", tint = PrimaryDeepPurple)
                }
            }
        } else {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = WarmCardWhite),
                border = BorderStroke(1.dp, SoftBorderPlum),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("🏥", fontSize = 28.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("No hay citas programadas", fontWeight = FontWeight.Medium, fontSize = 13.sp, color = MutedSlateSub)
                    Spacer(modifier = Modifier.height(8.dp))
                    TextButton(onClick = onNavigateToCalendar) {
                        Text("+ Programar Control Médico", color = PrimaryDeepPurple, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Direct Quick Actions Row
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(WarmCardWhite)
                    .border(1.dp, SoftBorderPlum, RoundedCornerShape(16.dp))
                    .clickable { onNavigateToDirectWhatsAppIntent(viewModel, context) }
                    .padding(14.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("💬", fontSize = 24.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Tribu WhatsApp", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = PrimaryDeepPurple, textAlign = TextAlign.Center)
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(WarmCardWhite)
                    .border(1.dp, SoftBorderPlum, RoundedCornerShape(16.dp))
                    .clickable { onNavigateToDocuments() }
                    .padding(14.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("📄", fontSize = 24.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Escanear Recetas", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = PrimaryDeepPurple, textAlign = TextAlign.Center)
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(PrimaryDeepPurple.copy(alpha = 0.08f))
                    .border(1.dp, AlertSoftRed, RoundedCornerShape(16.dp))
                    .clickable { onNavigateEmergency() }
                    .padding(14.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🚨", fontSize = 24.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Ayuda / Crisis", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = PrimaryDeepPurple, textAlign = TextAlign.Center)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Micro-Breathing Exercise Interactive banner
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = SageGreen.copy(alpha = 0.6f)),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { viewModel.setBreathingActive(true) }
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BreathingCircleGuide(modifier = Modifier.size(90.dp))
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Pausa de Respiración 4-7-8 ", fontWeight = FontWeight.Bold, color = PrimaryDeepPurple, fontSize = 14.sp)
                        Box(
                            modifier = Modifier
                                .background(SecondaryDustPink.copy(alpha = 0.2f), RoundedCornerShape(6.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text("IA ACTIVA ✨", color = SecondaryDustPink, fontWeight = FontWeight.Bold, fontSize = 8.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Reduce la ansiedad del posparto respirando. Toca aquí para iniciar la guía interactiva donde la IA te guiará y evaluará.",
                        fontSize = 11.sp,
                        color = DeepCharcoalText,
                        lineHeight = 15.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Community Support / Grupo de WhatsApp de Apoyo
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = WarmCardWhite),
            border = BorderStroke(1.5.dp, SoftLila),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(SageGreen.copy(alpha = 0.4f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("🏘️", fontSize = 24.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Grupo de Soporte Comunitario",
                            fontWeight = FontWeight.Bold,
                            color = PrimaryDeepPurple,
                            fontFamily = FontFamily.Serif,
                            fontSize = 16.sp
                        )
                        Text(
                            text = "Nuestra Tribu de Madres",
                            color = SecondaryDustPink,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Únete a nuestro grupo oficial de WhatsApp para compartir experiencias, encontrar contención y conversar en un entorno seguro y confidencial moderado por psicólogas obstétricas especializadas.",
                    fontSize = 11.5.sp,
                    color = DeepCharcoalText,
                    lineHeight = 16.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { viewModel.triggerWhatsAppGroupIntent(context) },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryDeepPurple),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("community_support_whatsapp_btn")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("💬", fontSize = 18.sp, modifier = Modifier.padding(end = 8.dp))
                        Text(
                            text = "Unirse a la Tribu en WhatsApp",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.5.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Recent Moods History
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Historial Emocional",
                fontWeight = FontWeight.Bold,
                color = PrimaryDeepPurple,
                fontSize = 15.sp
            )
            if (moods.isNotEmpty()) {
                TextButton(onClick = onNavigateToCheckIn) {
                    Text("Ver Todo", color = SecondaryDustPink, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        if (moods.isEmpty()) {
            Text(
                text = "No has registrado tu estado de ánimo de hoy. Haz clic arriba para empezar.",
                fontSize = 12.sp,
                color = MutedSlateSub,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        } else {
            moods.take(2).forEach { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = WarmCardWhite),
                    border = BorderStroke(1.dp, SoftBorderPlum)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = when (item.moodScore) {
                                        1 -> "😭"
                                        2 -> "😔"
                                        3 -> "😐"
                                        4 -> "🙂"
                                        5 -> "🌸"
                                        else -> "🍂"
                                    },
                                    fontSize = 20.sp
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = when (item.moodScore) {
                                        1 -> "Abrumada"
                                        2 -> "Triste"
                                        3 -> "Cansada"
                                        4 -> "Alegre"
                                        5 -> "Plena"
                                        else -> "Otro"
                                    },
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = PrimaryDeepPurple
                                )
                            }
                            Text(
                                text = formatTimestamp(item.timestamp, "dd MMM, h:mm a"),
                                fontSize = 10.sp,
                                color = MutedSlateSub
                            )
                        }
                        if (item.moodNotes.isNotBlank()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "\"${item.moodNotes}\"", fontSize = 12.sp, color = DeepCharcoalText, style = androidx.compose.ui.text.TextStyle(fontFamily = FontFamily.Serif))
                        }
                        if (item.advice.isNotBlank()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Box(
                                modifier = Modifier
                                    .background(SoftLila.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                                    .padding(8.dp)
                            ) {
                                Column {
                                    Text("🌸 Respuesta de TribuMental :", fontWeight = FontWeight.Bold, fontSize = 10.sp, color = PrimaryDeepPurple)
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(text = item.advice, fontSize = 11.sp, color = DeepCharcoalText, lineHeight = 15.sp)
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // WhatsApp adapter logging view link (DevOps visible transparency)
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = SageGreen.copy(alpha = 0.3f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .clickable { onNavigateToWhatsApp() }
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Settings, contentDescription = null, tint = TerciaryWarmTeal, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Logs de WhatsApp Recibidos", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = PrimaryDeepPurple)
                    Text("Revisa las plantillas y flujos automáticos transaccionales simulados.", fontSize = 10.sp, color = MutedSlateSub)
                }
                Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = PrimaryDeepPurple, modifier = Modifier.size(16.dp))
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

fun onNavigateToDirectWhatsAppIntent(viewModel: TribuMentalViewModel, context: Context) {
    viewModel.triggerWhatsAppDirectIntent(context)
}

// DAILY EMOTIONAL CHECK-IN SCREEN
@Composable
fun MoodCheckInScreen(
    viewModel: TribuMentalViewModel,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("tribumental_prefs", Context.MODE_PRIVATE) }

    var selectedScore by remember { mutableStateOf(3) }
    var notes by remember { mutableStateOf("") }
    var isSubmitted by remember { mutableStateOf(false) }
    var emotionalAdvice by remember { mutableStateOf("") }
    var autoSaveStatus by remember { mutableStateOf("Todo al día") }

    val isGenerating by viewModel.isGeneratingAdvice.collectAsStateWithLifecycle()

    // 1. Initial Load of Draft from Local Shared Preferences
    LaunchedEffect(Unit) {
        val savedScore = prefs.getInt("draft_mood_score", -1)
        val savedNotes = prefs.getString("draft_mood_notes", "") ?: ""
        if (savedScore != -1) {
            selectedScore = savedScore
        }
        if (savedNotes.isNotEmpty()) {
            notes = savedNotes
        }
    }

    // 2. Debounced Auto-Save Mechanism
    LaunchedEffect(selectedScore, notes) {
        // Trigger auto-save if we have custom draft notes or if user customized the score
        if (notes.isNotEmpty() || selectedScore != 3) {
            autoSaveStatus = "Guardando borrador..."
            delay(1000) // 1 second debounce
            prefs.edit()
                .putInt("draft_mood_score", selectedScore)
                .putString("draft_mood_notes", notes)
                .apply()
            val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            autoSaveStatus = "Borrador guardado automáticamente (${sdf.format(Date())})"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(CreamBackground)
            .padding(20.dp)
            .windowInsetsPadding(WindowInsets.safeDrawing),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Screen Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Regresar",
                    tint = PrimaryDeepPurple
                )
            }
            Text(
                text = "Check-In Emocional",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = PrimaryDeepPurple,
                fontFamily = FontFamily.Serif
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (!isSubmitted) {
            Text(
                text = "¿Cómo te sientes en este instante?",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryDeepPurple,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Serif
            )
            Text(
                text = "Toma conciencia de tu respiración y califica tu estado mental actual:",
                fontSize = 13.sp,
                color = MutedSlateSub,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 6.dp, bottom = 20.dp)
            )

            // Dynamic Adaptive Tactile Grid of Large Emojis
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                val moodsSet = listOf(
                    Triple(1, "😭", "Abrumada"),
                    Triple(2, "😔", "Triste"),
                    Triple(3, "😐", "Cansada"),
                    Triple(4, "🙂", "Alegre"),
                    Triple(5, "🌸", "Plena")
                )

                moodsSet.forEach { triple ->
                    val isSelected = selectedScore == triple.first
                    val accentColor = when (triple.first) {
                        1 -> SecondaryDustPink
                        2 -> Color(0xFF6C93CD)
                        3 -> MutedSlateSub
                        4 -> SoftLila
                        5 -> TerciaryWarmTeal
                        else -> PrimaryDeepPurple
                    }
                    val itemBg = if (isSelected) accentColor.copy(alpha = 0.15f) else Color.Transparent
                    val itemBorderColor = if (isSelected) accentColor else SoftBorderPlum.copy(alpha = 0.5f)

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(itemBg)
                            .border(
                                width = if (isSelected) 2.5.dp else 1.dp,
                                color = itemBorderColor,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .clickable { selectedScore = triple.first }
                            .padding(vertical = 12.dp, horizontal = 4.dp)
                            .width(58.dp)
                    ) {
                        Text(
                            text = triple.second, 
                            fontSize = 38.sp,
                            modifier = Modifier.testTag("emoji_btn_${triple.first}")
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = triple.third,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) accentColor else DeepCharcoalText.copy(alpha = 0.8f),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Real-Time Pre-Submission Immediate Mini Recommendation (Tribu-Tip)
            val instantTip = when (selectedScore) {
                1 -> "🧘‍♀️ **Tribu-Tip: Respiración 4-7-8**\nInhala por 4s, retén por 7s y exhala lentamente por 8s. Repítelo 3 veces. Es una pauta neurológica para reducir el cortisol y calmar la ansiedad mamá hoy."
                2 -> "☕ **Tribu-Tip: Momento de autocuidado**\nPrepárate una infusión tibia o date una ducha consciente de 5 minutos, sintiendo el caer del agua fresca. Es un anclaje sensorial para validar y liberar la tristeza."
                3 -> "🧸 **Tribu-Tip: Microdescanso reparador**\nRecuéstate de espaldas por 5 minutos elevando tus pies por encima de tu pecho. Olvídate de los pendientes; recargar tu energía es prioritario."
                4 -> "🌱 **Tribu-Tip: Paseo consciente**\nDa un corto paseo, asómate a captar los colores del cielo o respira aire libre un instante. Guardar esta energía positiva sella el bienestar en tu mente."
                5 -> "✨ **Tribu-Tip: Semilla de gratitud**\n¡Qué bendición sentir esta plenitud mami! Escribe una frase de agradecimiento en tus notas o compártela con otra mamá de la Tribu para fortalecer la paz común."
                else -> ""
            }

            AnimatedVisibility(
                visible = instantTip.isNotEmpty(),
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = when (selectedScore) {
                            1 -> SecondaryDustPink.copy(alpha = 0.08f)
                            2 -> Color(0xFF6C93CD).copy(alpha = 0.08f)
                            3 -> SoftBorderPlum.copy(alpha = 0.3f)
                            4 -> SoftLila.copy(alpha = 0.15f)
                            5 -> TerciaryWarmTeal.copy(alpha = 0.08f)
                            else -> PowderPink
                        }
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = when (selectedScore) {
                            1 -> SecondaryDustPink.copy(alpha = 0.2f)
                            2 -> Color(0xFF6C93CD).copy(alpha = 0.2f)
                            3 -> MutedSlateSub.copy(alpha = 0.2f)
                            4 -> SoftLila.copy(alpha = 0.3f)
                            5 -> TerciaryWarmTeal.copy(alpha = 0.2f)
                            else -> SoftBorderPlum
                        }
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = when (selectedScore) {
                                1 -> "💡"
                                2 -> "🍵"
                                3 -> "☕"
                                4 -> "🌿"
                                5 -> "🌸"
                                else -> "✨"
                            },
                            fontSize = 24.sp,
                            modifier = Modifier.padding(end = 12.dp)
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = instantTip,
                                style = MaterialTheme.typography.bodyMedium,
                                color = DeepCharcoalText,
                                lineHeight = 19.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Notes Text Field
            Text(
                text = "Escribe un diario o notas libres sobre tus pensamientos (opcional):",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = DeepCharcoalText,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                placeholder = { 
                    Text(
                        text = "Hoy me he sentido un poco ansiosa por el descanso del bebé, pero sigo conteniendo mi mente y conectando con el presente...",
                        style = TextStyle(fontSize = 13.sp, color = MutedSlateSub)
                    ) 
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .testTag("mood_notes_field"),
                shape = RoundedCornerShape(16.dp),
                textStyle = TextStyle(fontSize = 14.sp, color = DeepCharcoalText),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryDeepPurple,
                    unfocusedBorderColor = SoftBorderPlum,
                    focusedContainerColor = WarmCardWhite.copy(alpha = 0.7f),
                    unfocusedContainerColor = WarmCardWhite.copy(alpha = 0.3f)
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Auto-Save Status Cloud Indicator Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Estado de autoguardado",
                    tint = if (autoSaveStatus.startsWith("Borrador guardado")) TerciaryWarmTeal else MutedSlateSub,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = autoSaveStatus,
                    color = MutedSlateSub,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            if (isGenerating) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = PowderPink),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            color = SecondaryDustPink,
                            strokeWidth = 3.dp,
                            modifier = Modifier.size(36.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Generando contención psicoemocional...",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryDeepPurple
                        )
                        Text(
                            text = "Nuestra IA perinatal está redactando una sugerencia de autocuidado para ti.",
                            fontSize = 11.sp,
                            color = MutedSlateSub,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            } else {
                Button(
                    onClick = {
                        viewModel.performMoodCheckIn(selectedScore, notes) { r ->
                            emotionalAdvice = r
                            isSubmitted = true
                            // Clear saved draft on successful submission
                            prefs.edit()
                                .remove("draft_mood_score")
                                .remove("draft_mood_notes")
                                .apply()
                        }
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryDeepPurple),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .testTag("save_mood_btn")
                ) {
                    Text(
                        text = "Guardar Registro Emocional", 
                        fontWeight = FontWeight.Bold, 
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }
        } else {
            // Success response screen showcasing beautiful maternal advice card
            Text(
                text = "🎉 ¡Check-In Exitoso!", 
                fontSize = 24.sp, 
                fontWeight = FontWeight.Bold, 
                color = PrimaryDeepPurple,
                fontFamily = FontFamily.Serif
            )
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = SoftLila.copy(alpha = 0.35f)),
                border = BorderStroke(1.dp, PrimaryDeepPurple.copy(alpha = 0.15f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(24.dp), 
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = when (selectedScore) {
                            1 -> "😭"
                            2 -> "😔"
                            3 -> "😐"
                            4 -> "🙂"
                            5 -> "🌸"
                            else -> "🍂"
                        },
                        fontSize = 54.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Soporte Perinatal TribuMental",
                        fontWeight = FontWeight.Bold,
                        color = PrimaryDeepPurple,
                        fontSize = 15.sp,
                        fontFamily = FontFamily.Serif
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = emotionalAdvice,
                        fontSize = 14.sp,
                        color = DeepCharcoalText,
                        lineHeight = 22.sp,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontFamily = FontFamily.Serif,
                            fontStyle = FontStyle.Italic
                        )
                    )
                }
            }

            // Display logged notes summary
            if (notes.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = WarmCardWhite),
                    border = BorderStroke(1.dp, SoftBorderPlum),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Tu anotación de hoy:",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MutedSlateSub
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "\"$notes\"",
                            fontSize = 13.sp,
                            color = DeepCharcoalText,
                            fontStyle = FontStyle.Italic,
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onBack,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryDeepPurple),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text(
                    text = "Volver al Inicio", 
                    color = Color.White, 
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
    }
}

// CALENDAR & APPOINTMENTS SCREEN (Includes adding and deleting appointments)
@Composable
fun CalendarControlScreen(
    viewModel: TribuMentalViewModel,
    onBack: () -> Unit,
    onAttachDocumentPrompt: (appointmentId: Int) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var appointmentType by remember { mutableStateOf("Control Prenatal") }
    var provider by remember { mutableStateOf("") }
    var selectedDaysOffset by remember { mutableStateOf(1) } // 1 for tomorrow
    var notes by remember { mutableStateOf("") }
    var isAdding by remember { mutableStateOf(false) }

    val appointmentsList by viewModel.appointments.collectAsStateWithLifecycle()
    val documentsList by viewModel.documents.collectAsStateWithLifecycle()

    val categories = listOf("Control Prenatal", "Pediatría", "Psicología", "Laboratorio", "Otro")
    val daySdf = remember { SimpleDateFormat("yyyyMMdd", Locale.getDefault()) }

    // Calendar navigation state
    var calendarInstance by remember { mutableStateOf(java.util.Calendar.getInstance()) }
    val year = calendarInstance.get(java.util.Calendar.YEAR)
    val month = calendarInstance.get(java.util.Calendar.MONTH) // 0-indexed

    // Currently selected day: "yyyyMMdd", or null for showing all
    var selectedDayStr by remember { mutableStateOf<String?>(null) }

    // Format selected date text for display
    var formattedSelectedDate = ""
    if (selectedDayStr != null) {
        try {
            val parsedDate = daySdf.parse(selectedDayStr!!)
            val displaySdf = SimpleDateFormat("EEEE, d 'de' MMMM", Locale("es", "ES"))
            if (parsedDate != null) {
                formattedSelectedDate = displaySdf.format(parsedDate)
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
            }
        } catch (e: Exception) {
            formattedSelectedDate = selectedDayStr!!
        }
    }

    // Filter appointments
    val filteredAppointments = if (selectedDayStr == null) {
        appointmentsList
    } else {
        appointmentsList.filter { daySdf.format(Date(it.dateTime)) == selectedDayStr }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(CreamBackground)
            .padding(20.dp)
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        // App bar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar", tint = PrimaryDeepPurple)
            }
            Text(
                text = "Calendario Materno",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = PrimaryDeepPurple,
                fontFamily = FontFamily.Serif
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // SHADCN/UI STYLE MINIMALIST CALENDAR
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = WarmCardWhite),
            border = BorderStroke(1.dp, SoftBorderPlum),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Calendar header with mouth/year and navigation
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val meses = listOf(
                        "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
                    )
                    Text(
                        text = "${meses[month]} $year",
                        fontWeight = FontWeight.Bold,
                        color = PrimaryDeepPurple,
                        fontSize = 15.sp,
                        fontFamily = FontFamily.Serif
                    )
                    Row {
                        IconButton(
                            onClick = {
                                val prevMonthInstance = (calendarInstance.clone() as java.util.Calendar).apply {
                                    add(java.util.Calendar.MONTH, -1)
                                }
                                calendarInstance = prevMonthInstance
                            },
                            modifier = Modifier
                                .size(32.dp)
                                .border(1.dp, SoftBorderPlum, RoundedCornerShape(8.dp))
                        ) {
                            Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Mes anterior", tint = PrimaryDeepPurple, modifier = Modifier.size(16.dp))
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        IconButton(
                            onClick = {
                                val nextMonthInstance = (calendarInstance.clone() as java.util.Calendar).apply {
                                    add(java.util.Calendar.MONTH, 1)
                                }
                                calendarInstance = nextMonthInstance
                            },
                            modifier = Modifier
                                .size(32.dp)
                                .border(1.dp, SoftBorderPlum, RoundedCornerShape(8.dp))
                        ) {
                            Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Mes siguiente", tint = PrimaryDeepPurple, modifier = Modifier.size(16.dp))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Weekdays header
                val diasSemana = listOf("Lu", "Ma", "Mi", "Ju", "Vi", "Sa", "Do")
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    diasSemana.forEach { label ->
                        Text(
                            text = label,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = SecondaryDustPink
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Days grid calculation
                val tempCal = java.util.Calendar.getInstance().apply {
                    set(java.util.Calendar.YEAR, year)
                    set(java.util.Calendar.MONTH, month)
                    set(java.util.Calendar.DAY_OF_MONTH, 1)
                }
                val totalDaysInMonth = tempCal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH)
                val firstDayOfWeek = tempCal.get(java.util.Calendar.DAY_OF_WEEK) // 1=Sunday, 2=Monday...
                val startOffset = (firstDayOfWeek - 2 + 7) % 7 // Monday aligned
                val totalCells = startOffset + totalDaysInMonth
                val rowsCount = (totalCells + 6) / 7

                Column(modifier = Modifier.fillMaxWidth()) {
                    for (r in 0 until rowsCount) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            for (c in 0 until 7) {
                                val cellIdx = r * 7 + c
                                if (cellIdx < startOffset || cellIdx >= totalCells) {
                                    Spacer(modifier = Modifier.weight(1f))
                                } else {
                                    val dayNum = cellIdx - startOffset + 1
                                    val cellCal = java.util.Calendar.getInstance().apply {
                                        set(java.util.Calendar.YEAR, year)
                                        set(java.util.Calendar.MONTH, month)
                                        set(java.util.Calendar.DAY_OF_MONTH, dayNum)
                                    }
                                    val cellDayStr = daySdf.format(cellCal.time)
                                    val isSelected = selectedDayStr == cellDayStr
                                    val hasEvents = appointmentsList.any { daySdf.format(Date(it.dateTime)) == cellDayStr }

                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .aspectRatio(1f)
                                            .padding(2.dp)
                                            .clip(CircleShape)
                                            .background(if (isSelected) PrimaryDeepPurple else Color.Transparent)
                                            .clickable {
                                                selectedDayStr = if (isSelected) null else cellDayStr
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                text = dayNum.toString(),
                                                fontSize = 12.sp,
                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                                color = if (isSelected) Color.White else DeepCharcoalText
                                            )
                                            if (hasEvents) {
                                                Box(
                                                    modifier = Modifier
                                                        .size(4.dp)
                                                        .background(
                                                            if (isSelected) Color.White else SecondaryDustPink,
                                                            CircleShape
                                                        )
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Active filter badge/HUD description
        if (selectedDayStr != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .background(SoftLila.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.DateRange, contentDescription = null, tint = PrimaryDeepPurple, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Filtrando: $formattedSelectedDate",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryDeepPurple
                    )
                }
                Text(
                    text = "Ver todo",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = SecondaryDustPink,
                    modifier = Modifier.clickable { selectedDayStr = null }
                )
            }
        }

        // Add/Schedule button trigger
        Button(
            onClick = { isAdding = !isAdding },
            colors = ButtonDefaults.buttonColors(containerColor = if (isAdding) MutedSlateSub else SecondaryDustPink),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(if (isAdding) Icons.Default.Close else Icons.Default.Add, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = if (isAdding) "Cancelar Nueva Cita" else "Programar Nueva Cita Médica",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // New Appointment Form
        if (isAdding) {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = WarmCardWhite),
                border = BorderStroke(1.dp, SoftBorderPlum),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Nueva Cita", fontWeight = FontWeight.Bold, color = PrimaryDeepPurple, fontSize = 15.sp)
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Título de la Cita (Ej. Ecografía 4D)") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = provider,
                        onValueChange = { provider = it },
                        label = { Text("Profesional / Clínica (Ej. Dr. Andrés)") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    // Appointment type selection
                    Text("Categoría:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MutedSlateSub)
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                        Row {
                            categories.forEach { cat ->
                                val isCatSelected = appointmentType == cat
                                FilterChip(
                                    selected = isCatSelected,
                                    onClick = { appointmentType = cat },
                                    label = { Text(cat) },
                                    modifier = Modifier.padding(horizontal = 4.dp),
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = SoftLila,
                                        selectedLabelColor = PrimaryDeepPurple
                                    )
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Schedule date HUD or selectors
                    if (selectedDayStr != null) {
                        Text(
                            text = "Se agendará para la fecha seleccionada:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            color = MutedSlateSub
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(SoftLila.copy(alpha = 0.2f), RoundedCornerShape(10.dp))
                                .padding(10.dp)
                        ) {
                            Text(
                                text = "📅  $formattedSelectedDate (Preseleccionado)",
                                fontWeight = FontWeight.Bold,
                                color = PrimaryDeepPurple,
                                fontSize = 12.sp
                            )
                        }
                    } else {
                        // Date simulation offsets if no calendar filter clicked
                        Text("Sugerencia de fecha:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MutedSlateSub)
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            listOf(Pair(1, "Mañana"), Pair(3, "En 3 días"), Pair(7, "En una semana")).forEach { pair ->
                                FilterChip(
                                    selected = selectedDaysOffset == pair.first,
                                    onClick = { selectedDaysOffset = pair.first },
                                    label = { Text(pair.second) }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        label = { Text("Notas o Síntomas (Ej. Llevar ayuno)") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                          if (title.trim().isNotBlank() && provider.trim().isNotBlank()) {
                              val finalTimestamp = if (selectedDayStr != null) {
                                  try {
                                      val parsed = daySdf.parse(selectedDayStr!!)
                                      parsed?.time ?: System.currentTimeMillis()
                                  } catch (e: Exception) {
                                      System.currentTimeMillis()
                                  }
                              } else {
                                  System.currentTimeMillis() + (selectedDaysOffset * 24 * 60 * 60 * 1000L)
                              }

                              viewModel.addAppointment(title, finalTimestamp, appointmentType, provider, notes)
                              isAdding = false
                              title = ""
                              provider = ""
                              notes = ""
                          }
                        },
                        enabled = title.trim().isNotBlank() && provider.trim().isNotBlank(),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryDeepPurple),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("save_appointment_btn")
                    ) {
                        Text("Confirmar y Guardar Cita", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Section header
        Text(
            text = if (selectedDayStr == null) "Mis Controles Programados" else "Controles del día",
            fontWeight = FontWeight.Bold,
            color = PrimaryDeepPurple,
            fontSize = 15.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // List
        if (filteredAppointments.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("📅", fontSize = 40.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("No tienes citas creadas para esta selección.", fontSize = 11.sp, color = MutedSlateSub)
                }
            }
        } else {
            filteredAppointments.forEach { appointment ->
                val attachedDocs = documentsList.filter { it.appointmentId == appointment.id }

                // SHADCN/UI HIGH-CONTRAST INDIVIDUAL APPOINTMENT CARD COMPONENT
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = WarmCardWhite),
                    border = BorderStroke(1.dp, SoftBorderPlum),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        // Badge type and Delete action Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val badgeBg = when (appointment.type) {
                                "Control Prenatal" -> SoftLila
                                "Pediatría" -> SageGreen
                                "Psicología" -> PowderPink
                                "Laboratorio" -> Color(0xFFE9F0FE)
                                else -> Color(0xFFF1EDE9)
                            }
                            val badgeColor = when (appointment.type) {
                                "Control Prenatal" -> PrimaryDeepPurple
                                "Pediatría" -> TerciaryWarmTeal
                                "Psicología" -> SecondaryDustPink
                                "Laboratorio" -> Color(0xFF2C5EBD)
                                else -> DeepCharcoalText
                            }

                            Box(
                                modifier = Modifier
                                    .background(badgeBg, RoundedCornerShape(10.dp))
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = appointment.type,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp,
                                    color = badgeColor
                                )
                            }

                            IconButton(
                                onClick = { viewModel.deleteAppointment(appointment.id) },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = "Borrar", tint = AlertSoftRed)
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = appointment.title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = PrimaryDeepPurple)
                        Spacer(modifier = Modifier.height(4.dp))
                        
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Person, contentDescription = null, tint = TerciaryWarmTeal, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Profesional: Dr(a). ${appointment.provider}", fontSize = 12.sp, color = DeepCharcoalText)
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.DateRange, contentDescription = null, tint = SecondaryDustPink, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Cuándo: ${formatTimestamp(appointment.dateTime, "EEEE d 'de' MMMM, h:mm a")}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                color = SecondaryDustPink
                            )
                        }

                        if (appointment.notes.isNotBlank()) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(BorderStroke(1.dp, SoftBorderPlum), RoundedCornerShape(8.dp))
                                    .background(CreamBackground)
                                    .padding(10.dp)
                            ) {
                                Text(
                                    text = "\"${appointment.notes}\"",
                                    fontSize = 11.sp,
                                    color = DeepCharcoalText,
                                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Divider(color = SoftBorderPlum)
                        Spacer(modifier = Modifier.height(10.dp))

                        // SUBSECTION: LINKED DOCUMENTS SHADCN/UI STYLE
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Menu, contentDescription = null, tint = PrimaryDeepPurple, modifier = Modifier.size(14.dp)) // standard icon for clip/list
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Documentos Asociados (${attachedDocs.size})",
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                color = PrimaryDeepPurple
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        if (attachedDocs.isEmpty()) {
                            Text(
                                text = "No se registran recetas, ecografías o informes asociados.",
                                fontSize = 10.sp,
                                color = MutedSlateSub,
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                modifier = Modifier.padding(bottom = 6.dp)
                            )
                        } else {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp)
                            ) {
                                attachedDocs.forEach { doc ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 3.dp)
                                            .background(SageGreen.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                                            .padding(horizontal = 10.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Icon(
                                                Icons.Default.Info, // descriptive icon representing document file
                                                contentDescription = null,
                                                tint = TerciaryWarmTeal,
                                                modifier = Modifier.size(14.dp)
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Column {
                                                Text(
                                                    text = doc.name,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 11.sp,
                                                    color = PrimaryDeepPurple
                                                )
                                                Text(
                                                    text = "Tipo: ${doc.type}",
                                                    fontSize = 9.sp,
                                                    color = DeepCharcoalText
                                                )
                                            }
                                        }
                                        IconButton(
                                            onClick = { viewModel.unlinkDocumentFromAppointment(doc.id) },
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            Icon(Icons.Default.Close, contentDescription = "Desvincular", tint = AlertSoftRed, modifier = Modifier.size(16.dp))
                                        }
                                    }
                                }
                            }
                        }

                        // Direct attachment scanning action
                        Button(
                            onClick = { onAttachDocumentPrompt(appointment.id) },
                            colors = ButtonDefaults.buttonColors(containerColor = SageGreen),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.AddCircle, contentDescription = null, tint = PrimaryDeepPurple, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Escanear & Adjuntar Documento OCR", color = PrimaryDeepPurple, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
    }
}

// MEDICAL DOCUMENTS & OCR SCANNER SCREEN
@Composable
fun DocumentsScannerScreen(
    viewModel: TribuMentalViewModel,
    onBack: () -> Unit,
    prefilledAppointmentId: Int? = null
) {
    val profileState by viewModel.userProfile.collectAsStateWithLifecycle()
    val isPremium = profileState?.isPremium == true
    var docName by remember { mutableStateOf("") }
    var docType by remember { mutableStateOf("Resultados de Laboratorio") } // Updated automatically by IA
    var selectedAppointmentId by remember { mutableStateOf(prefilledAppointmentId) }
    var mockDocContent by remember { mutableStateOf("") }
    
    var isProcessingOCR by remember { mutableStateOf(false) }
    var scanSuccessMessage by remember { mutableStateOf<String?>(null) }

    // Camera states
    val context = LocalContext.current
    var cameraPermissionGranted by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        cameraPermissionGranted = isGranted
    }

    var capturedImageBitmap by remember { mutableStateOf<Bitmap?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            capturedImageBitmap = bitmap
            if (docName.isBlank()) {
                val timeStamp = SimpleDateFormat("dd/MM HH:mm", Locale.getDefault()).format(Date())
                docName = "Captura de Cámara $timeStamp"
            }
            mockDocContent = "IMAGEN REAL ADQUIRIDA POR CÁMARA:\nEstudio clínico obstétrico, paciente perinatal. Frecuencia de latidos cardíacos embrionarios (FHR): 156 lpm. Placenta posterior alta, líquido amniótico adecuado. Hemograma general con hemoglobina de 11.2 g/dL y plaquetas normales. Ausencia de focos de sangrado."
        }
    }

    // Interactive viewfinder simulators
    var filterMode by remember { mutableStateOf("Color") } // "Color", "OCR Verde", "Monocromo"
    var isFlashOn by remember { mutableStateOf(false) }
    var activeSimulatedDoc by remember { mutableStateOf<String?>(null) } // "LAB_TEST" or "ULTRASOUND_PIC"

    // IA computed analysis states
    var aiClassificationResult by remember { mutableStateOf<String?>(null) }
    var aiAnalysisExplanation by remember { mutableStateOf<String?>(null) }

    val documentsList by viewModel.documents.collectAsStateWithLifecycle()
    val appointmentsList by viewModel.appointments.collectAsStateWithLifecycle()

    val primaryCategories = listOf("Orden Médica", "Resultados de Laboratorio", "Ecografía / Ultrasonido", "Otros Documentos")

    val infiniteTransition = rememberInfiniteTransition(label = "laser_sweep")
    val laserProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "laser_progress"
    )

    var listFilterCategory by remember { mutableStateOf("All") }
    var sortByNewest by remember { mutableStateOf(true) } // true: más reciente, false: más antiguo

    val sortedAndFilteredDocuments = remember(documentsList, listFilterCategory, sortByNewest) {
        val filtered = when (listFilterCategory) {
            "All" -> documentsList
            "Orden Médica" -> documentsList.filter { it.type == "Orden Médica" || it.type.lowercase().contains("orden") || it.type.lowercase().contains("receta") }
            "Resultados de Laboratorio" -> documentsList.filter { it.type == "Resultados de Laboratorio" || it.type.lowercase().contains("lab") || it.type.lowercase().contains("resultado") }
            "Ecografía / Ultrasonido" -> documentsList.filter { it.type == "Ecografía / Ultrasonido" || it.type.lowercase().contains("ultra") || it.type.lowercase().contains("eco") }
            "Otros Documentos" -> documentsList.filter { it.type == "Otros Documentos" || (!it.type.lowercase().contains("orden") && !it.type.lowercase().contains("lab") && !it.type.lowercase().contains("resultado") && !it.type.lowercase().contains("ultra") && !it.type.lowercase().contains("eco")) }
            else -> documentsList.filter { it.type == listFilterCategory }
        }
        if (sortByNewest) {
            filtered.sortedByDescending { it.timestamp }
        } else {
            filtered.sortedBy { it.timestamp }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(CreamBackground)
            .padding(20.dp)
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        // Aesthetic App Bar Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar", tint = PrimaryDeepPurple)
            }
            Spacer(modifier = Modifier.width(4.dp))
            Column {
                Text(
                    text = "Scanner de Documentos",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = PrimaryDeepPurple,
                    fontFamily = FontFamily.Serif
                )
                Text(
                    text = "Digitalización y Clasificación Inteligente de Reportes Médicos",
                    fontSize = 11.sp,
                    color = MutedSlateSub
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Main Document Scanner Simulator Card
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = WarmCardWhite),
            border = BorderStroke(1.5.dp, SoftBorderPlum),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                
                if (!cameraPermissionGranted) {
                    // CAMERA PERMISSION REQUEST BANNER
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("📷", fontSize = 42.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Acceso a Cámara Requerido",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = PrimaryDeepPurple
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Para tomar fotografías reales de tus documentos médicos e informes obstétricos, concede permiso de cámara.",
                            fontSize = 11.sp,
                            color = MutedSlateSub,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { permissionLauncher.launch(Manifest.permission.CAMERA) },
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryDeepPurple),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Conceder Permiso", color = Color.White)
                        }
                        
                        TextButton(
                            onClick = { cameraPermissionGranted = true }
                        ) {
                            Text("Continuar con Visor y Simulador Interno de Tribu", color = SecondaryDustPink, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                } else {
                    // Status Bar Ticker
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(if (isProcessingOCR) AlertSoftRed else TerciaryWarmTeal, CircleShape)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isProcessingOCR) "IA ANALIZANDO..." else "CÁMARA LISTA / LIVE",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isProcessingOCR) AlertSoftRed else TerciaryWarmTeal,
                                letterSpacing = 1.sp
                            )
                        }
                        Text(
                            text = "TRIBU_CAM_V3",
                            fontSize = 9.sp,
                            color = MutedSlateSub,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // VIEW FINDER WINDOW
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .border(
                                BorderStroke(
                                    2.dp,
                                    if (isProcessingOCR) AlertSoftRed.copy(alpha = 0.8f) else SoftLila
                                ),
                                RoundedCornerShape(16.dp)
                            )
                            .background(
                                if (filterMode == "Monocromo") Color.DarkGray 
                                else if (filterMode == "OCR Verde") Color(0xFF0F1E11) 
                                else Color.Black
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (capturedImageBitmap != null) {
                            // Render user's real camera snapshot
                            Image(
                                bitmap = capturedImageBitmap!!.asImageBitmap(),
                                contentDescription = "Foto capturada",
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .clip(RoundedCornerShape(8.dp))
                            )
                            
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(8.dp)
                                    .background(Color.Black.copy(alpha = 0.7f), RoundedCornerShape(12.dp))
                                    .clickable {
                                        capturedImageBitmap = null
                                        mockDocContent = ""
                                    }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Refresh, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Volver a capturar", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        } else {
                            // Viewfinder brackets canvas
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val strokeWidth = 3.dp.toPx()
                                val len = 20.dp.toPx()
                                val braceColor = if (filterMode == "OCR Verde") Color.Green else PrimaryDeepPurple
                                // Top-Left corner
                                drawLine(braceColor, Offset(10.dp.toPx(), 10.dp.toPx()), Offset(10.dp.toPx() + len, 10.dp.toPx()), strokeWidth)
                                drawLine(braceColor, Offset(10.dp.toPx(), 10.dp.toPx()), Offset(10.dp.toPx(), 10.dp.toPx() + len), strokeWidth)

                                // Top-Right corner
                                drawLine(braceColor, Offset(size.width - 10.dp.toPx(), 10.dp.toPx()), Offset(size.width - 10.dp.toPx() - len, 10.dp.toPx()), strokeWidth)
                                drawLine(braceColor, Offset(size.width - 10.dp.toPx(), 10.dp.toPx()), Offset(size.width - 10.dp.toPx(), 10.dp.toPx() + len), strokeWidth)

                                // Bottom-Left corner
                                drawLine(braceColor, Offset(10.dp.toPx(), size.height - 10.dp.toPx()), Offset(10.dp.toPx() + len, size.height - 10.dp.toPx()), strokeWidth)
                                drawLine(braceColor, Offset(10.dp.toPx(), size.height - 10.dp.toPx()), Offset(10.dp.toPx(), size.height - 10.dp.toPx() - len), strokeWidth)

                                // Bottom-Right corner
                                drawLine(braceColor, Offset(size.width - 10.dp.toPx(), size.height - 10.dp.toPx()), Offset(size.width - 10.dp.toPx() - len, size.height - 10.dp.toPx()), strokeWidth)
                                drawLine(braceColor, Offset(size.width - 10.dp.toPx(), size.height - 10.dp.toPx()), Offset(size.width - 10.dp.toPx(), size.height - 10.dp.toPx() - len), strokeWidth)
                            }

                            // Interactive template alignment helper inside live lens
                            if (activeSimulatedDoc == null) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Text("📷", fontSize = 32.sp)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Alinea un reporte en tu visor",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = if (filterMode == "OCR Verde") Color.Green else Color.White
                                    )
                                    Text(
                                        text = "Usa tu cámara real o escoge una plantilla abajo",
                                        fontSize = 10.sp,
                                        color = Color.LightGray
                                    )
                                }
                            } else {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(24.dp)
                                        .background(Color.White, RoundedCornerShape(8.dp))
                                        .border(BorderStroke(1.dp, SoftBorderPlum), RoundedCornerShape(8.dp))
                                        .padding(12.dp)
                                ) {
                                    if (activeSimulatedDoc == "LAB_TEST") {
                                        Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxSize()) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Text("LABS CLINICOS", fontWeight = FontWeight.Bold, fontSize = 7.sp, color = Color.Gray)
                                                Text("ANALÍTICA DE SANGRE", fontWeight = FontWeight.Bold, fontSize = 7.sp, color = TerciaryWarmTeal)
                                            }
                                            Divider(color = SoftBorderPlum.copy(alpha = 0.5f), thickness = 0.5.dp)
                                            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                                Text("Hemoglobina Glicada (HbA1c): 5.4% [Valores Óptimos]", fontSize = 6.sp, color = DeepCharcoalText)
                                                Text("Hierro Sérico: 85 ug/dL [Rango Normal]", fontSize = 6.sp, color = DeepCharcoalText)
                                                Text("Glucosa: 82 mg/dL [Sano, sin diabetes]", fontSize = 6.sp, color = DeepCharcoalText)
                                            }
                                            Text("Sincronización IA activa", fontSize = 5.sp, color = Color.Gray, modifier = Modifier.align(Alignment.End))
                                        }
                                    } else if (activeSimulatedDoc == "PRESCRIPTION") {
                                        Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxSize()) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Text("PLAN DE TRATAMIENTO", fontWeight = FontWeight.Bold, fontSize = 7.sp, color = Color.Gray)
                                                Text("ORDEN MÉDICA", fontWeight = FontWeight.Bold, fontSize = 7.sp, color = PrimaryDeepPurple)
                                            }
                                            Divider(color = SoftBorderPlum.copy(alpha = 0.5f), thickness = 0.5.dp)
                                            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                                Text("• Ácido fólico 400 mcg diario por vía oral [Gestación]", fontSize = 6.sp, color = DeepCharcoalText)
                                                Text("• Hierro aminoquelado 30 mg diario en la noche", fontSize = 6.sp, color = DeepCharcoalText)
                                                Text("• Curva de tolerancia oral a la glucosa en semana 24", fontSize = 6.sp, color = DeepCharcoalText)
                                            }
                                            Text("Receta & Orden Médica", fontSize = 5.sp, color = Color.Gray)
                                        }
                                    } else {
                                        Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxSize()) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Text("CENTRO MATERNO OBSTÉTRICO", fontWeight = FontWeight.Bold, fontSize = 7.sp, color = Color.Gray)
                                                Text("ECOGRAFÍA OBSTÉTRICA", fontWeight = FontWeight.Bold, fontSize = 7.sp, color = SecondaryDustPink)
                                            }
                                            Divider(color = SoftBorderPlum.copy(alpha = 0.5f), thickness = 0.5.dp)
                                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                Box(
                                                    modifier = Modifier
                                                        .size(45.dp)
                                                        .background(Color.Black, RoundedCornerShape(4.dp))
                                                        .drawBehind {
                                                            drawArc(
                                                                color = Color.White.copy(alpha = 0.6f),
                                                                startAngle = 120f,
                                                                sweepAngle = 180f,
                                                                useCenter = false,
                                                                style = Stroke(width = 2.dp.toPx())
                                                             )
                                                             drawCircle(
                                                                color = Color.White.copy(alpha = 0.8f),
                                                                radius = 5.dp.toPx(),
                                                                center = Offset(22.dp.toPx(), 20.dp.toPx())
                                                             )
                                                         }
                                                 )
                                                 Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                                     Text("Semana Gestacional: 13.2", fontSize = 6.sp, color = DeepCharcoalText, fontWeight = FontWeight.Bold)
                                                     Text("Ritmo Cardiaco Fetal: 154 lpm", fontSize = 6.sp, color = DeepCharcoalText)
                                                     Text("Líquido Amniótico: Saludable", fontSize = 6.sp, color = DeepCharcoalText)
                                                 }
                                             }
                                             Text("Estudio de Ultrasonido", fontSize = 5.sp, color = Color.Gray)
                                         }
                                    }
                                }
                            }
                        }

                        // FLASH OVERLAY EFFECT
                        if (isFlashOn) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.White.copy(alpha = 0.25f))
                            )
                        }

                        // SWEEPING GREEN LASER EFFECT ANIMATION
                        if (isProcessingOCR) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(4.dp)
                                    .align(Alignment.TopCenter)
                                    .offset(y = 200.dp * laserProgress)
                                    .background(
                                        brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                                            colors = listOf(
                                                Color.Green.copy(alpha = 0.1f),
                                                Color.Green,
                                                Color.Green.copy(alpha = 0.1f)
                                            )
                                        )
                                    )
                            )
                        }
                    }

                    // CAMERA TOOLBAR ROW
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Flash Control
                        IconButton(
                            onClick = { isFlashOn = !isFlashOn },
                            modifier = Modifier.background(if (isFlashOn) SoftLila else SoftBorderPlum.copy(alpha = 0.2f), CircleShape)
                        ) {
                            Icon(
                                imageVector = if (isFlashOn) Icons.Default.Warning else Icons.Default.PlayArrow,
                                contentDescription = "Flash Toggle",
                                tint = if (isFlashOn) PrimaryDeepPurple else DeepCharcoalText,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        // Main capture button
                        Button(
                            onClick = {
                                try {
                                    cameraLauncher.launch(null)
                                } catch (e: Exception) {
                                    // Fallback when platform forbids direct intent launch
                                    activeSimulatedDoc = "LAB_TEST"
                                    docName = "Análisis Clínico de Sangre"
                                    mockDocContent = "Resultado de Laboratorio Clínico: Hemoglobina Glicada (HbA1c) 5.4%, Hierro Sérico 85 ug/dL [Rango Óptimo], Glucosa basal en ayunas de 82 mg/dL. Sin indicios de anemia gestacional."
                                    scanSuccessMessage = "Se ha simulado la captura del visor clínico adecuadamente."
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryDeepPurple),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.testTag("camera_capture_button")
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Add, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Capturar con Cámara", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        // Lens Mode Filter Buttons
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            listOf("Color", "OCR Verde", "Monocromo").forEach { mode ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (filterMode == mode) SoftLila else SoftBorderPlum.copy(alpha = 0.15f))
                                        .clickable { filterMode = mode }
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = mode,
                                        fontSize = 8.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (filterMode == mode) PrimaryDeepPurple else DeepCharcoalText
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // SELECTION OF TEMPLATE SOURCE (IF DESIRED TO POSITION ON VIEW WINDOW)
                    Text(
                        text = "Colocar Documento en el Visor:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        color = MutedSlateSub
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    activeSimulatedDoc = "LAB_TEST"
                                    docName = "Reporte de Sangre Basal"
                                    mockDocContent = "Resultado de Laboratorio Clínico: Hemoglobina Glicada (HbA1c) 5.4%, Hierro Sérico 85 ug/dL [Rango Óptimo], Glucosa basal en ayunas de 82 mg/dL. Sin indicios de anemia gestacional."
                                },
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(
                                if (activeSimulatedDoc == "LAB_TEST") 1.8.dp else 1.dp,
                                if (activeSimulatedDoc == "LAB_TEST") TerciaryWarmTeal else SoftBorderPlum
                            ),
                            colors = CardDefaults.cardColors(
                                containerColor = if (activeSimulatedDoc == "LAB_TEST") SoftLila.copy(alpha = 0.3f) else WarmCardWhite
                            )
                        ) {
                            Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("🧪", fontSize = 18.sp)
                                Spacer(modifier = Modifier.height(2.dp))
                                Text("Resultado Lab", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = PrimaryDeepPurple, textAlign = TextAlign.Center)
                                Text("Analítica", fontSize = 7.sp, color = MutedSlateSub)
                             }
                        }

                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    activeSimulatedDoc = "ULTRASOUND_PIC"
                                    docName = "Ultrasonido Primer Trimestre"
                                    mockDocContent = "Ecografía de Ultrasonido Clínico: Edad gestacional estimada en 13 semanas y 2 días dadas las mediciones. Ritmo cardiaco embrionario rítmico a 154 latidos por minuto. Líquido amniótico en volumen normal."
                                },
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(
                                if (activeSimulatedDoc == "ULTRASOUND_PIC") 1.8.dp else 1.dp,
                                if (activeSimulatedDoc == "ULTRASOUND_PIC") SecondaryDustPink else SoftBorderPlum
                            ),
                            colors = CardDefaults.cardColors(
                                containerColor = if (activeSimulatedDoc == "ULTRASOUND_PIC") SoftLila.copy(alpha = 0.3f) else WarmCardWhite
                            )
                        ) {
                            Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("👶", fontSize = 18.sp)
                                Spacer(modifier = Modifier.height(2.dp))
                                Text("Ecografía", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = PrimaryDeepPurple, textAlign = TextAlign.Center)
                                Text("Ultrasonido", fontSize = 7.sp, color = MutedSlateSub)
                            }
                        }

                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    activeSimulatedDoc = "PRESCRIPTION"
                                    docName = "Orden de Receta Obstétrica"
                                    mockDocContent = "Plan de Tratamiento Prenatal: Ácido fólico 400 mcg, Hierro aminoquelado 30 mg diario en la noche. Perfil toxoplasmosis en la semana 24. Reposo moderado, evitar sobreesfuerzos."
                                },
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(
                                if (activeSimulatedDoc == "PRESCRIPTION") 1.8.dp else 1.dp,
                                if (activeSimulatedDoc == "PRESCRIPTION") PrimaryDeepPurple else SoftBorderPlum
                            ),
                            colors = CardDefaults.cardColors(
                                containerColor = if (activeSimulatedDoc == "PRESCRIPTION") SoftLila.copy(alpha = 0.3f) else WarmCardWhite
                            )
                        ) {
                            Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("📋", fontSize = 18.sp)
                                Spacer(modifier = Modifier.height(2.dp))
                                Text("Orden Médica", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = PrimaryDeepPurple, textAlign = TextAlign.Center)
                                Text("Receta", fontSize = 7.sp, color = MutedSlateSub)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // CUSTOM NAME CHOSEN BY THE USER
                    Text(
                        text = "Elige un nombre personalizado para guardarlo:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = PrimaryDeepPurple
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    OutlinedTextField(
                        value = docName,
                        onValueChange = { docName = it },
                        placeholder = { Text("Escribe el nombre de tu archivo (ej: Mi ecografía de Lucas, Mis exámenes tiroides)") },
                        modifier = Modifier.fillMaxWidth().testTag("custom_doc_name_input"),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = SoftBorderPlum,
                            focusedBorderColor = PrimaryDeepPurple
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // OPTIONAL APPOINTMENT LINK
                    if (appointmentsList.isNotEmpty()) {
                        Text(
                            text = "Vincular a un Control ginecológico o cita (Opcional):",
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            color = MutedSlateSub
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Box(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                appointmentsList.forEach { app ->
                                    val isSelected = selectedAppointmentId == app.id
                                    FilterChip(
                                        selected = isSelected,
                                        onClick = { selectedAppointmentId = if (isSelected) null else app.id },
                                        label = { Text(app.title, fontSize = 10.sp) },
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = SoftLila,
                                            selectedLabelColor = PrimaryDeepPurple
                                        )
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    // OCR TEXT DISPLAY (READ ONLY OR MANUALLY EDITABLE TO CUSTOM EXPLAIN)
                    Text(
                        text = "Contenido extraído listo para análisis de IA:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        color = MutedSlateSub
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = mockDocContent,
                        onValueChange = { mockDocContent = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp),
                        placeholder = { Text("El texto del reporte aparecerá aquí tras capturar o simular...", fontSize = 11.sp) },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = SoftBorderPlum
                        )
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // COMPUTE CLASSIFIER AND AI DECRYPTION
                    if (!isPremium) {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = PowderPink),
                            border = BorderStroke(1.dp, SecondaryDustPink.copy(alpha = 0.5f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("🔒", fontSize = 20.sp)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Función Premium: Escaneo OCR con IA",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = PrimaryDeepPurple
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "El escaneo y análisis clínico de recetas médicas, exámenes y ecografías usando Gemini 1.5 Flash está reservado para el Plan Premium.",
                                    fontSize = 11.sp,
                                    color = DeepCharcoalText,
                                    lineHeight = 15.sp
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Button(
                                    onClick = { viewModel.initiateWompiPayment("MENSUAL") },
                                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryDeepPurple),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "Activar Plan Premium ($9.99/mes) para Escanear",
                                        color = Color.White,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    } else if (isProcessingOCR) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator(color = SecondaryDustPink, strokeWidth = 3.dp)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Examinando con Gemini Flash 1.5 y clasificando...",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryDeepPurple
                            )
                        }
                    } else {
                        Button(
                            onClick = {
                                if (docName.trim().isNotBlank()) {
                                    isProcessingOCR = true
                                    scanSuccessMessage = null
                                    aiClassificationResult = null
                                    aiAnalysisExplanation = null
                                    
                                    val finalContent = mockDocContent.ifBlank {
                                        "Muestra de analítica para $docName"
                                    }
                                    
                                    viewModel.scanCameraAndAnalyzeDocument(
                                        name = docName.trim(),
                                        extractedContentText = finalContent,
                                        appointmentId = selectedAppointmentId
                                    ) { detectedCategory, cleanAnalysis ->
                                        isProcessingOCR = false
                                        aiClassificationResult = detectedCategory
                                        aiAnalysisExplanation = cleanAnalysis
                                        scanSuccessMessage = "¡Extracción de IA completada! Guardado bajo la sección '$detectedCategory' con el título '$docName'."
                                        
                                        // Reset fields
                                        activeSimulatedDoc = null
                                        capturedImageBitmap = null
                                        mockDocContent = ""
                                        selectedAppointmentId = prefilledAppointmentId
                                    }
                                }
                            },
                            enabled = docName.trim().isNotBlank() && (capturedImageBitmap != null || activeSimulatedDoc != null || mockDocContent.isNotBlank()),
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryDeepPurple),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("process_scan_btn")
                        ) {
                            Text(
                                text = "Examinar & Clasificar con IA",
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }

                    // Rich Gemini AI Results Section
                    if (aiClassificationResult != null && aiAnalysisExplanation != null) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = SoftLila.copy(alpha = 0.5f)),
                            border = BorderStroke(1.dp, PrimaryDeepPurple.copy(alpha = 0.4f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("🧠", fontSize = 18.sp)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Resultado de Clasificación IA:",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = PrimaryDeepPurple
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    val (badgeBgCol, badgeText) = when (aiClassificationResult) {
                                        "Orden Médica" -> Pair(SecondaryDustPink, "📋 ORDEN MÉDICA / RECETA")
                                        "Resultados de Laboratorio" -> Pair(TerciaryWarmTeal, "🧪 RESULTADO DE LABORATORIO")
                                        "Ecografía / Ultrasonido" -> Pair(PrimaryDeepPurple, "👶 ECOGRAFÍA (ULTRASONIDO)")
                                        else -> Pair(MutedSlateSub, "📄 OTROS / REPORTE GENERAL")
                                    }
                                    Box(
                                        modifier = Modifier
                                            .background(badgeBgCol, RoundedCornerShape(6.dp))
                                            .padding(horizontal = 8.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = badgeText,
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 9.sp
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "Resumen Médico Perinatal Caluroso:",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp,
                                    color = MutedSlateSub
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = aiAnalysisExplanation ?: "",
                                    fontSize = 11.sp,
                                    color = DeepCharcoalText,
                                    lineHeight = 15.sp
                                )
                            }
                        }
                    }

                    // Success Indicator Toast Banner
                    scanSuccessMessage?.let { msg ->
                        Spacer(modifier = Modifier.height(12.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(SageGreen.copy(alpha = 0.25f), RoundedCornerShape(10.dp))
                                .border(BorderStroke(1.dp, SageGreen.copy(alpha = 0.7f)), RoundedCornerShape(10.dp))
                                .padding(10.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = TerciaryWarmTeal, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(text = msg, fontSize = 11.sp, color = DeepCharcoalText, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // LIST HEADER WITH SELECTABLE FILTERS
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Carpeta Médica Personal",
                    fontWeight = FontWeight.Bold,
                    color = PrimaryDeepPurple,
                    fontSize = 15.sp,
                    fontFamily = FontFamily.Serif
                )

                // Date sorting toggle
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(PowderPink.copy(alpha = 0.4f))
                        .clickable { sortByNewest = !sortByNewest }
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = if (sortByNewest) "📅 Más Recientes ↓" else "📅 Más Antiguos ↑",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryDeepPurple
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Dynamic Category Filter Scrollable Row
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(vertical = 4.dp)
            ) {
                listOf(
                    Pair("All", "Todos 📁"),
                    Pair("Orden Médica", "Recetas / Órdenes 📋"),
                    Pair("Resultados de Laboratorio", "Resultados 🧪"),
                    Pair("Ecografía / Ultrasonido", "Ecografía 👶"),
                    Pair("Otros Documentos", "Otros 📄")
                ).forEach { pair ->
                    val cat = pair.first
                    val label = pair.second
                    val isSelected = listFilterCategory == cat
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) PrimaryDeepPurple else SoftBorderPlum.copy(alpha = 0.25f))
                            .clickable { listFilterCategory = cat }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = label,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) Color.White else PrimaryDeepPurple
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // LIST RENDERER
        if (sortedAndFilteredDocuments.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("📂", fontSize = 42.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "No hay registros bajo la categoría seleccionada.",
                        fontSize = 11.sp,
                        color = MutedSlateSub,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            sortedAndFilteredDocuments.forEach { doc ->
                val (badgeBg, indicatorColor) = when (doc.type) {
                    "Orden Médica" -> Pair(SoftLila.copy(alpha = 0.5f), SecondaryDustPink)
                    "Resultados de Laboratorio" -> Pair(SageGreen.copy(alpha = 0.4f), TerciaryWarmTeal)
                    "Ecografía / Ultrasonido" -> Pair(PowderPink.copy(alpha = 0.5f), PrimaryDeepPurple)
                    else -> Pair(SoftBorderPlum.copy(alpha = 0.2f), MutedSlateSub)
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = WarmCardWhite),
                    border = BorderStroke(1.dp, SoftBorderPlum)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .background(indicatorColor, CircleShape)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .background(badgeBg, RoundedCornerShape(8.dp))
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = doc.type.uppercase(),
                                        fontWeight = FontWeight.Bold,
                                        color = PrimaryDeepPurple,
                                        fontSize = 9.sp
                                    )
                                }
                            }

                            IconButton(
                                onClick = { viewModel.deleteDocument(doc.id) },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = "Borrar", tint = AlertSoftRed)
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = doc.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = PrimaryDeepPurple,
                            fontFamily = FontFamily.Serif
                        )
                        Text(
                            text = "Digitalizado: ${formatTimestamp(doc.timestamp)}",
                            fontSize = 11.sp,
                            color = MutedSlateSub
                        )

                        if (doc.appointmentId != null) {
                            val matchingAppointment = appointmentsList.firstOrNull { it.id == doc.appointmentId }
                            if (matchingAppointment != null) {
                                Spacer(modifier = Modifier.height(6.dp))
                                Box(
                                    modifier = Modifier
                                        .background(SoftLila.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = "📅 Cita Asociada: ${matchingAppointment.title}",
                                        fontWeight = FontWeight.Bold,
                                        color = PrimaryDeepPurple,
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Divider(color = SoftBorderPlum)
                        Spacer(modifier = Modifier.height(8.dp))

                        // Transcription output
                        Text("📝 Transmisión OCR Inteligente:", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = PrimaryDeepPurple)
                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(CreamBackground.copy(alpha = 0.6f), RoundedCornerShape(8.dp))
                                .padding(10.dp)
                        ) {
                            Text(
                                text = doc.documentText,
                                fontSize = 11.sp,
                                color = DeepCharcoalText,
                                lineHeight = 16.sp,
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                style = androidx.compose.ui.text.TextStyle(fontFamily = FontFamily.Serif)
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
    }
}

// WHATSAPP COMMUNICATOR & MESSAGES LOG SCREEN (DevOps transparency)
@Composable
fun WhatsAppLogScreen(
    viewModel: TribuMentalViewModel,
    onBack: () -> Unit
) {
    val profileState by viewModel.userProfile.collectAsStateWithLifecycle()
    val isPremium = profileState?.isPremium == true
    val logs by viewModel.whatsappLogs.collectAsStateWithLifecycle()
    val chatbotStep by viewModel.chatbotStep.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var inputText by remember { mutableStateOf("") }
    var showChatbotView by remember { mutableStateOf(false) }
    var showUpgradeNoticeDialog by remember { mutableStateOf(false) }

    // Automatically toggle to chatbot view if onboarding begins
    LaunchedEffect(chatbotStep) {
        if (chatbotStep != "IDLE") {
            showChatbotView = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
            .padding(16.dp)
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        // App bar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {
                    if (chatbotStep != "IDLE") {
                        viewModel.abortWhatsAppOnboarding()
                    }
                    onBack()
                }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar", tint = PrimaryDeepPurple)
                }
                Text(
                    text = if (chatbotStep != "IDLE") "Tribu Bot 🌸 WhatsApp" else "Comunidad & Tribu WhatsApp 🌸",
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = PrimaryDeepPurple
                )
            }
            
            // Log cleaner / view togglers
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (chatbotStep == "IDLE") {
                    TextButton(onClick = { viewModel.clearLogs() }) {
                        Text("Limpiar", color = AlertSoftRed, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                } else {
                    TextButton(onClick = { 
                        viewModel.abortWhatsAppOnboarding()
                    }) {
                        Text("Salir Bot", color = AlertSoftRed, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (chatbotStep != "IDLE") {
            // === ACTIVE CHATBOT SIMULATED INTENT VIEW ===
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = WarmCardWhite),
                border = BorderStroke(1.dp, SoftBorderPlum),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(Color(0xFF2EC4B6), CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            "Tribu Bot Autoguía",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = PrimaryDeepPurple
                        )
                        Text(
                            text = "En línea • Canal de Onboarding Oficial",
                            fontSize = 9.sp,
                            color = MutedSlateSub
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = PowderPink),
                border = BorderStroke(1.dp, SoftBorderPlum),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    // Dialog Bubbles List
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .padding(12.dp),
                        reverseLayout = false
                    ) {
                        items(logs) { item ->
                            val isSent = item.direction == "SENT" // Sent by bot
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                contentAlignment = if (isSent) Alignment.CenterStart else Alignment.CenterEnd
                            ) {
                                Card(
                                    shape = RoundedCornerShape(
                                        topStart = 14.dp,
                                        topEnd = 14.dp,
                                        bottomStart = if (isSent) 4.dp else 14.dp,
                                        bottomEnd = if (isSent) 14.dp else 4.dp
                                    ),
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (isSent) WarmCardWhite else SageGreen 
                                    ),
                                    border = BorderStroke(1.dp, SoftBorderPlum),
                                    modifier = Modifier.widthIn(max = 260.dp)
                                ) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Text(
                                            text = item.body,
                                            fontSize = 11.sp,
                                            color = DeepCharcoalText,
                                            lineHeight = 15.sp
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = if (isSent) "WhatsApp Business API" else "Tú",
                                            fontSize = 7.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MutedSlateSub,
                                            modifier = Modifier.fillMaxWidth(),
                                            textAlign = TextAlign.End
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Dynamic Quick-Replies Chips
                    Text(
                        text = "Respuestas sugeridas inteligentes:",
                        fontSize = 9.sp,
                        color = MutedSlateSub,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        val chips = when (chatbotStep) {
                            "AWAITING_NAME" -> listOf("Ana Gómez", "Sofía Pérez", "María Rodríguez")
                            "AWAITING_CONSENT" -> listOf("SÍ, acepto recibir mensajes 🌸", "NO, prefiero configurar luego")
                            "AWAITING_STAGE" -> listOf("1. Embarazada 🤰", "2. Posparto 🤱")
                            "AWAITING_WEEKS_OR_MONTHS" -> {
                                listOf("12 semanas", "20 semanas", "3 meses posparto", "6 meses posparto")
                            }
                            "AWAITING_CONCERN" -> listOf("1. Humor/Ansiedad", "2. Sueño", "3. Lactancia", "4. Dolores", "5. Soledad")
                            "AWAITING_FREQUENCY" -> listOf("1. Diario ⏰", "2. Dos veces por semana", "3. Semanal 🗓️")
                            "COMPLETED" -> listOf("✓ Onboarding Sincronizado")
                            else -> emptyList()
                        }
                        
                        chips.forEach { chipText ->
                            Box(
                                modifier = Modifier
                                    .background(TerciaryWarmTeal.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                                    .border(1.dp, TerciaryWarmTeal.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                                    .clickable {
                                        viewModel.sendUserWhatsAppMessage(chipText)
                                    }
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = chipText,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryDeepPurple
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Custom interactive input bar
                    if (chatbotStep != "COMPLETED") {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(WarmCardWhite)
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier
                                    .weight(1f)
                                    .background(PowderPink, RoundedCornerShape(12.dp))
                                    .border(1.dp, SoftBorderPlum, RoundedCornerShape(12.dp))
                                    .padding(horizontal = 12.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                BasicTextField(
                                    value = inputText,
                                    onValueChange = { inputText = it },
                                    textStyle = androidx.compose.ui.text.TextStyle(
                                        color = DeepCharcoalText,
                                        fontSize = 12.sp
                                    ),
                                    modifier = Modifier.weight(1f),
                                    decorationBox = { innerTextField ->
                                        if (inputText.isEmpty()) {
                                            Text("Responde al bot aquí...", fontSize = 12.sp, color = MutedSlateSub)
                                        }
                                        innerTextField()
                                    }
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            IconButton(
                                onClick = {
                                    if (inputText.isNotBlank()) {
                                        viewModel.sendUserWhatsAppMessage(inputText)
                                        inputText = ""
                                    }
                                },
                                modifier = Modifier
                                    .background(TerciaryWarmTeal, CircleShape)
                                    .size(36.dp)
                            ) {
                                Icon(
                                    Icons.AutoMirrored.Filled.Send,
                                    contentDescription = "Enviar",
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    } else {
                        // Onboarding completed, let's offer back to dashboard call
                        Button(
                            onClick = { 
                                viewModel.abortWhatsAppOnboarding()
                                onBack() 
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryDeepPurple),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                                .height(46.dp)
                                .testTag("onboarding_complete_dashboard_button")
                        ) {
                            Text("Comenzar Mi Maternidad", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                }
            }
        } else {
            // === MAIN WHATSAPP PORTAL (IDLE STATE) ===
            var expandTechnicalMonitor by remember { mutableStateOf(false) }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // Hero Banner Card
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = PowderPink.copy(alpha = 0.6f)),
                    border = BorderStroke(1.dp, SoftBorderPlum),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("🌸", fontSize = 24.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Acompañamiento Gestacional Seguro",
                                fontWeight = FontWeight.SemiBold,
                                color = PrimaryDeepPurple,
                                fontSize = 15.sp,
                                fontFamily = FontFamily.Serif
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Nuestra Tribu Maternal de WhatsApp te conecta de manera inmediata con contención obstétrica profesional y redes de apoyo colectivo para que nunca te sientas sola en tu viaje.",
                            fontSize = 11.sp,
                            color = DeepCharcoalText,
                            lineHeight = 16.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // OPTION 1: CHAT WITH INDIVIDUAL SPECIALIST
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = WarmCardWhite),
                    border = BorderStroke(1.dp, SoftBorderPlum),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("💬", fontSize = 20.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Canal de Contención 1-A-1", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = PrimaryDeepPurple)
                            }
                            // Online Status Indicator
                            Box(
                                modifier = Modifier
                                    .background(SageGreen, RoundedCornerShape(12.dp))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text("En Línea 🟢", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = PrimaryDeepPurple)
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Conversa directamente vía WhatsApp con nuestra psicóloga perinatal de turno. Ideal para resolver dudas graves, crisis, miedos obstétricos u obtener consuelo.",
                            fontSize = 11.sp,
                            color = MutedSlateSub,
                            lineHeight = 15.sp
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Button(
                            onClick = { 
                                if (isPremium) {
                                    viewModel.triggerWhatsAppDirectIntent(context)
                                } else {
                                    showUpgradeNoticeDialog = true
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isPremium) Color(0xFF25D366) else MutedSlateSub
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = if (isPremium) "💬 Enviar Mensaje a Especialista" else "🔒 Chat de Especialista (Premium)", 
                                color = Color.White, 
                                fontWeight = FontWeight.Bold, 
                                fontSize = 12.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // OPTION 2: JOIN COMMUNITY GROUP
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = WarmCardWhite),
                    border = BorderStroke(1.dp, SoftBorderPlum),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("👥", fontSize = 20.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Tribu Comunitario de Madres", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = PrimaryDeepPurple)
                            }
                            Box(
                                modifier = Modifier
                                    .background(SoftLila, RoundedCornerShape(12.dp))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text("Comunidad 👥", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = PrimaryDeepPurple)
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Compórtate con otras mamás en tu misma semana de embarazo o etapa posparto en un grupo colaborativo de WhatsApp. Intercambia vivencias con empatía.",
                            fontSize = 11.sp,
                            color = MutedSlateSub,
                            lineHeight = 15.sp
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Button(
                            onClick = { 
                                if (isPremium) {
                                    viewModel.triggerWhatsAppGroupIntent(context)
                                } else {
                                    showUpgradeNoticeDialog = true
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isPremium) Color(0xFF25D366) else MutedSlateSub
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = if (isPremium) "👥 Unirme al Grupo de Apoyo" else "🔒 Unirme como Miembro Premium", 
                                color = Color.White, 
                                fontWeight = FontWeight.Bold, 
                                fontSize = 12.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // TOGGLEABLE TECHNICAL DEVELOPER SUITE
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = SoftLila.copy(alpha = 0.25f)),
                    border = BorderStroke(1.dp, SoftBorderPlum),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("🛠️", fontSize = 16.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    "Simulador & Registro de Webhooks (API)",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp,
                                    color = PrimaryDeepPurple
                                )
                            }
                            TextButton(onClick = { expandTechnicalMonitor = !expandTechnicalMonitor }) {
                                Text(
                                    text = if (expandTechnicalMonitor) "Ocultar" else "Mostrar",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SecondaryDustPink
                                )
                            }
                        }

                        if (expandTechnicalMonitor) {
                            Spacer(modifier = Modifier.height(8.dp))

                            // Dual technical tabs switcher
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(PowderPink, RoundedCornerShape(10.dp))
                                    .padding(4.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .background(
                                            if (!showChatbotView) WarmCardWhite else Color.Transparent,
                                            RoundedCornerShape(6.dp)
                                        )
                                        .clickable { showChatbotView = false }
                                        .padding(vertical = 6.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        "Monitor de Logs API",
                                        fontSize = 10.sp,
                                        fontWeight = if (!showChatbotView) FontWeight.Bold else FontWeight.Normal,
                                        color = if (!showChatbotView) PrimaryDeepPurple else MutedSlateSub
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .background(
                                            if (showChatbotView) WarmCardWhite else Color.Transparent,
                                            RoundedCornerShape(6.dp)
                                        )
                                        .clickable { showChatbotView = true }
                                        .padding(vertical = 6.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        "Simulador Conversacional",
                                        fontSize = 10.sp,
                                        fontWeight = if (showChatbotView) FontWeight.Bold else FontWeight.Normal,
                                        color = if (showChatbotView) PrimaryDeepPurple else MutedSlateSub
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            if (!showChatbotView) {
                                // TECHNICAL LOGS LIST
                                Text(
                                    "Sincronización via WhatsApp Business Webhooks en tiempo real:",
                                    fontSize = 9.sp,
                                    color = MutedSlateSub
                                )
                                Spacer(modifier = Modifier.height(6.dp))

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(180.dp)
                                        .background(WarmCardWhite, RoundedCornerShape(10.dp))
                                        .border(1.dp, SoftBorderPlum, RoundedCornerShape(10.dp))
                                        .padding(8.dp)
                                ) {
                                    if (logs.isEmpty()) {
                                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                            Text("Suscripción de Webhooks vacía. Envía un mensaje arriba.", fontSize = 9.sp, color = MutedSlateSub)
                                        }
                                    } else {
                                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                                            items(logs) { item ->
                                                val isSent = item.direction == "SENT"
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(vertical = 2.dp),
                                                    horizontalArrangement = if (isSent) Arrangement.End else Arrangement.Start
                                                ) {
                                                    Card(
                                                        shape = RoundedCornerShape(8.dp),
                                                        colors = CardDefaults.cardColors(containerColor = if (isSent) SoftBorderPlum else SageGreen),
                                                        modifier = Modifier.widthIn(max = 200.dp)
                                                    ) {
                                                        Column(modifier = Modifier.padding(6.dp)) {
                                                            Text(item.body, fontSize = 9.sp, color = DeepCharcoalText)
                                                            Text(
                                                                text = if (isSent) "Simulado: ${item.status}" else "Sincronizado vía webhook",
                                                                fontSize = 6.sp,
                                                                color = PrimaryDeepPurple
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                // CHATBOT INITIATOR CARD
                                Card(
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(containerColor = WarmCardWhite),
                                    modifier = Modifier.fillMaxWidth().padding(4.dp)
                                ) {
                                    Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("🤖", fontSize = 32.sp)
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            "Onboarding Guiado por WhatsApp",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp,
                                            color = PrimaryDeepPurple
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            "Simula de forma controlada el flujo conversacional guiado de recopilación de datos clínicos sin salir de la aplicación.",
                                            fontSize = 9.sp,
                                            color = MutedSlateSub,
                                            textAlign = TextAlign.Center
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Button(
                                            onClick = { if (isPremium) viewModel.resetAndStartWhatsAppOnboarding() else { showUpgradeNoticeDialog = true } },
                                            colors = ButtonDefaults.buttonColors(containerColor = TerciaryWarmTeal),
                                            shape = RoundedCornerShape(10.dp)
                                        ) {
                                            Text(if (isPremium) "Comenzar Simulación" else "🔒 Simular Onboarding (Premium)", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
    if (showUpgradeNoticeDialog) {
        AlertDialog(
            onDismissRequest = { showUpgradeNoticeDialog = false },
            title = { Text("🌸 Función Premium de WhatsApp", color = PrimaryDeepPurple, fontWeight = FontWeight.Bold) },
            text = { 
                Text(
                    "Uso de asistentes robot integrados en WhatsApp, guardado en la nube e interacción interactiva con especialistas de guardia perinatal están optimizados para el Plan Premium.\n\n¿Deseas activar tu Plan Premium en este momento?",
                    fontSize = 13.sp, color = DeepCharcoalText
                ) 
            },
            confirmButton = {
                Button(
                    onClick = { 
                        showUpgradeNoticeDialog = false
                        viewModel.initiateWompiPayment("MENSUAL")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryDeepPurple)
                ) {
                    Text("Activar Premium ($9.99/m)", color = Color.White, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showUpgradeNoticeDialog = false }) {
                    Text("Volver", color = MutedSlateSub)
                }
            },
            shape = RoundedCornerShape(16.dp),
            containerColor = WarmCardWhite
        )
    }
}

// UPGRADE SUBSCRIPTIONS & PAYWALL CENTER
@Composable
fun SubscriptionsCenterScreen(
    viewModel: TribuMentalViewModel,
    onBack: () -> Unit
) {
    val profile by viewModel.userProfile.collectAsStateWithLifecycle()
    val isPremium = profile?.isPremium == true
    val currentPlan = profile?.billingPlan ?: "GRATIS"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(CreamBackground)
            .padding(20.dp)
            .windowInsetsPadding(WindowInsets.safeDrawing),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar", tint = PrimaryDeepPurple)
            }
            Text(
                text = "Centro de Suscripciones",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = PrimaryDeepPurple,
                fontFamily = FontFamily.Serif
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // State Badge
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isPremium) SoftLila else PowderPink
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(if (isPremium) "👑" else "🌸", fontSize = 36.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (isPremium) "TribuMental Premium Activo" else "Planteamiento Gratuito Limitado",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp,
                    color = PrimaryDeepPurple
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (isPremium) "Tu suscripción $currentPlan está completamente operativa." else "Actualiza hoy para desbloquear el bot de WhatsApp y OCR.",
                    fontSize = 11.sp,
                    color = MutedSlateSub,
                    textAlign = TextAlign.Center
                )
                
                if (isPremium) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.downgradeSubscription() },
                        colors = ButtonDefaults.buttonColors(containerColor = AlertSoftRed),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Cancelar / Degradarse a Gratis", color = DeepCharcoalText, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "Elegir un Plan de Acompañamiento",
            fontWeight = FontWeight.Bold,
            color = PrimaryDeepPurple,
            fontSize = 15.sp,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        // Grid-like listing of plans
        PricingPlanCard(
            title = "Plan Básico Limitado 🌸",
            price = "$0.00 / Gratis",
            benefits = listOf(
                "Historial limitado de check-ins (últimos 5 registros)",
                "Agenda básica local de citas médicas",
                "Fórmula de despistaje tradicional",
                "❌ Sin escaneo OCR clínico por Gemini",
                "❌ Sin bot automatizado en WhatsApp",
                "❌ Sin contacto prioritario con especialista de guardia"
            ),
            buttonText = if (currentPlan == "GRATIS") "Tu plan actual" else "Degradar a plan Gratuito",
            onPlanSelected = { viewModel.downgradeSubscription() },
            isRecommended = false
        )

        Spacer(modifier = Modifier.height(14.dp))

        PricingPlanCard(
            title = "Plan Mensual Tribu ⭐ Premium",
            price = "$9.99 / mes",
            benefits = listOf(
                "Check-ins ilimitados procesados con IA Gemini",
                "Escaneo inteligente OCR de recetas y resultados",
                "Asistente robot de WhatsApp 24/7 activo",
                "Canal directo 1-a-1 prioritario con Especialistas",
                "Historial de control infinito en la nube"
            ),
            buttonText = if (currentPlan == "MENSUAL") "Tu plan actual" else "Comprar Mensual",
            onPlanSelected = { viewModel.initiateWompiPayment("MENSUAL") },
            isRecommended = currentPlan != "MENSUAL" && currentPlan != "FAMILIAR"
        )

        Spacer(modifier = Modifier.height(14.dp))

        PricingPlanCard(
            title = "Plan Familiar Copiloto 👥 Platinum",
            price = "$14.99 / mes",
            benefits = listOf(
                "Todo el plan Mensual de soporte con IA",
                "Soporte sincronizado para pareja / cuidador",
                "Alertas S.O.S críticas automatizadas",
                "Seguimiento de vacunas del bebé lactante",
                "Soporte prioritario 24/7 disponible"
            ),
            buttonText = if (currentPlan == "FAMILIAR") "Tu plan actual" else "Comprar Copiloto",
            onPlanSelected = { viewModel.initiateWompiPayment("FAMILIAR") },
            isRecommended = currentPlan == "FAMILIAR"
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Security of billing details assured
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.background(SageGreen.copy(alpha = 0.5f), RoundedCornerShape(12.dp)).padding(10.dp)
        ) {
            Icon(Icons.Default.Lock, contentDescription = null, tint = TerciaryWarmTeal, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
            text = "Simulación segura de pasarela activa Wompi Colombia (Bancolombia, Card, PSE, Nequi). Ningún cargo real será realizado.",
                fontSize = 11.sp,
                color = DeepCharcoalText
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
    }
}

// EMERGENCY SUPPORT & CRISIS CENTER (Includes adding and deleting custom support contact)
@Composable
fun EmergencyScreen(
    viewModel: TribuMentalViewModel,
    onBack: () -> Unit
) {
    var contactName by remember { mutableStateOf("") }
    var contactPhone by remember { mutableStateOf("") }
    var contactRelation by remember { mutableStateOf("Madre") }
    var isAddingContact by remember { mutableStateOf(false) }

    val contactsList by viewModel.contacts.collectAsStateWithLifecycle()
    val profileState by viewModel.userProfile.collectAsStateWithLifecycle()
    val isPremium = profileState?.isPremium == true

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(CreamBackground)
            .padding(20.dp)
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar", tint = PrimaryDeepPurple)
            }
            Text(
                text = "Centro de Crisis & Ayuda",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = PrimaryDeepPurple,
                fontFamily = FontFamily.Serif
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Prominent Health Disclaimer
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = AlertSoftRed.copy(alpha = 0.4f)),
            border = BorderStroke(1.dp, AlertSoftRed)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Warning, contentDescription = null, tint = PrimaryDeepPurple, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("ATENCIÓN CRÍTICA: NO ES UNA CLÍNICA", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = PrimaryDeepPurple)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Si estás experimentando pensamientos de hacerte daño, una crisis emocional severa o contracciones de riesgo médico, solicita asistencia urgente o llama a emergencias inmediatamente. Esta app es únicamente un apoyo complementario.",
                    fontSize = 11.sp,
                    color = DeepCharcoalText,
                    lineHeight = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // PREMIUM WHATSAPP EMERGENCY BOT CARD
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = if (isPremium) SageGreen.copy(alpha = 0.2f) else SoftLila.copy(alpha = 0.3f)),
            border = BorderStroke(1.dp, if (isPremium) TerciaryWarmTeal else SoftBorderPlum),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("💬", fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "WhatsApp S.O.S. Copiloto",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = PrimaryDeepPurple
                        )
                    }
                    if (!isPremium) {
                        Box(
                            modifier = Modifier
                                .background(SecondaryDustPink.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "⭐ FUNCIÓN PREMIUM",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = SecondaryDustPink
                            )
                        }
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(Color(0xFF2EC4B6), CircleShape)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                "Psicólogo Activo",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = TerciaryWarmTeal
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Chatea en tiempo real por un bot de WhatsApp para recibir contención de crisis personalizada y acceso directo a psicólogos especialistas en contención perinatal ginecológica.",
                    fontSize = 12.sp,
                    color = DeepCharcoalText,
                    lineHeight = 16.sp
                )

                if (!isPremium) {
                    Spacer(modifier = Modifier.height(14.dp))
                    Divider(color = SoftBorderPlum.copy(alpha = 0.6f))
                    Spacer(modifier = Modifier.height(14.dp))
                    
                    Text(
                        text = "🔒 Acceso Restringido: Esta opción requiere Tribu Premium para conectarte con terapeutas calificados de guardia.",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = MutedSlateSub,
                        lineHeight = 15.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Button(
                        onClick = {
                            viewModel.initiateWompiPayment("MENSUAL")
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryDeepPurple),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().testTag("emergency_upgrade_premium_button")
                    ) {
                        Text(
                            "Activar Plan Premium ($9.99/mes)",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                } else {
                    // Chat is unlocked!
                    Spacer(modifier = Modifier.height(14.dp))
                    Divider(color = SoftBorderPlum.copy(alpha = 0.6f))
                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "👩‍⚕️ Psicólogo de Guardia: Dr. Gabriel Soto",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryDeepPurple
                    )
                    Text(
                        text = "Colegiado #49021 • Especialista en Trauma Gestacional y Depresión Posparto",
                        fontSize = 10.sp,
                        color = MutedSlateSub
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Simulated interactive chat within the Emergency Screen which calls Gemini with Dr. Soto's profile
                    var emergencyInputText by remember { mutableStateOf("") }
                    var isSpecialistTyping by remember { mutableStateOf(false) }
                    
                    val coroutineScope = rememberCoroutineScope()
                    val emergencyMessages = remember {
                        mutableStateListOf<Pair<String, Boolean>>( // Pair of (Message Text, isUser)
                            Pair(
                                "¡Hola! He recibido tu alerta S.O.S. de emergencia de WhatsApp. Soy el Dr. Gabriel Soto, psicólogo perinatal de guardia. Estoy aquí contigo de manera confidencial. Cuéntame, ¿qué estás sintiendo en este preciso momento?",
                                false
                            )
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 180.dp, max = 320.dp)
                            .background(WarmCardWhite, RoundedCornerShape(12.dp))
                            .border(BorderStroke(1.dp, SoftBorderPlum), RoundedCornerShape(12.dp))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp)
                        ) {
                            LazyColumn(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
                                reverseLayout = false
                            ) {
                                items(emergencyMessages) { msg ->
                                    val isUser = msg.second
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp),
                                        contentAlignment = if (isUser) Alignment.CenterEnd else Alignment.CenterStart
                                    ) {
                                        Card(
                                            shape = RoundedCornerShape(
                                                topStart = 12.dp,
                                                topEnd = 12.dp,
                                                bottomStart = if (isUser) 12.dp else 2.dp,
                                                bottomEnd = if (isUser) 2.dp else 12.dp
                                            ),
                                            colors = CardDefaults.cardColors(
                                                containerColor = if (isUser) SoftLila else SageGreen.copy(alpha = 0.3f)
                                            ),
                                            modifier = Modifier.widthIn(max = 210.dp)
                                        ) {
                                            Text(
                                                text = msg.first,
                                                fontSize = 11.sp,
                                                color = DeepCharcoalText,
                                                modifier = Modifier.padding(8.dp),
                                                lineHeight = 15.sp
                                            )
                                        }
                                    }
                                }
                                if (isSpecialistTyping) {
                                    item {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 4.dp),
                                            contentAlignment = Alignment.CenterStart
                                        ) {
                                            Card(
                                                shape = RoundedCornerShape(12.dp),
                                                colors = CardDefaults.cardColors(containerColor = SageGreen.copy(alpha = 0.15f)),
                                                modifier = Modifier.padding(horizontal = 4.dp)
                                            ) {
                                                Text(
                                                    text = "Dr. Gabriel Soto está escribiendo...",
                                                    fontSize = 10.sp,
                                                    fontStyle = FontStyle.Italic,
                                                    color = MutedSlateSub,
                                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            // Message input row
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                OutlinedTextField(
                                    value = emergencyInputText,
                                    onValueChange = { emergencyInputText = it },
                                    placeholder = { Text("Escribe una pregunta o desahógate...", fontSize = 11.sp) },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(44.dp)
                                        .testTag("emergency_specialist_input"),
                                    textStyle = TextStyle(fontSize = 11.sp),
                                    shape = RoundedCornerShape(20.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = PrimaryDeepPurple,
                                        unfocusedBorderColor = SoftBorderPlum
                                    ),
                                    singleLine = true
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                IconButton(
                                    onClick = {
                                        val text = emergencyInputText.trim()
                                        if (text.isNotBlank() && !isSpecialistTyping) {
                                            emergencyMessages.add(Pair(text, true))
                                            emergencyInputText = ""
                                            isSpecialistTyping = true
                                            
                                            coroutineScope.launch {
                                                val userName = profileState?.name ?: "mamá"
                                                val userStage = profileState?.stage ?: "EMBARAZADA"
                                                val reply = GeminiService.generateEmergencyCrisisSupport(
                                                    userMessage = text,
                                                    userName = userName,
                                                    stage = userStage
                                                )
                                                delay(1500)
                                                emergencyMessages.add(Pair(reply, false))
                                                isSpecialistTyping = false
                                            }
                                        }
                                    },
                                    modifier = Modifier
                                        .size(36.dp)
                                        .background(PrimaryDeepPurple, CircleShape)
                                        .testTag("emergency_specialist_send_button"),
                                    enabled = emergencyInputText.trim().isNotBlank() && !isSpecialistTyping
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Send,
                                        contentDescription = "Enviar",
                                        tint = Color.White,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Form to add support contact
        Button(
            onClick = { isAddingContact = !isAddingContact },
            colors = ButtonDefaults.buttonColors(containerColor = if (isAddingContact) MutedSlateSub else SecondaryDustPink),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(if (isAddingContact) Icons.Default.Close else Icons.Default.Add, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = if (isAddingContact) "Cerrar Formulario" else "Agregar Contacto de Red de Apoyo",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (isAddingContact) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = WarmCardWhite),
                border = BorderStroke(1.dp, SoftBorderPlum),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Nuevo Contacto de Confianza", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = PrimaryDeepPurple)
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = contactName,
                        onValueChange = { contactName = it },
                        label = { Text("Nombre del Contacto") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = contactPhone,
                        onValueChange = { contactPhone = it },
                        label = { Text("Número de Teléfono (Ej. +54911...)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Text("Relación:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MutedSlateSub)
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                        listOf("Pareja", "Ginecóloga", "Terapeuta", "Amiga", "Madre").forEach { rel ->
                            FilterChip(
                                selected = contactRelation == rel,
                                onClick = { contactRelation = rel },
                                label = { Text(rel) },
                                modifier = Modifier.padding(horizontal = 4.dp),
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = SoftLila,
                                    selectedLabelColor = PrimaryDeepPurple
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (contactName.trim().isNotBlank() && contactPhone.trim().isNotBlank()) {
                                viewModel.addContact(contactName, contactPhone, contactRelation)
                                isAddingContact = false
                                contactName = ""
                                contactPhone = ""
                            }
                        },
                        enabled = contactName.trim().isNotBlank() && contactPhone.trim().isNotBlank(),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryDeepPurple),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Guardar Contacto en Mi Red", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        Text(
            text = "Mi Red de Contactos de Emergencia",
            fontWeight = FontWeight.Bold,
            color = PrimaryDeepPurple,
            fontSize = 15.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        contactsList.forEach { contact ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = WarmCardWhite),
                border = BorderStroke(1.dp, SoftBorderPlum),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(contact.name, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = PrimaryDeepPurple)
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .background(SoftLila, RoundedCornerShape(6.dp))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(contact.relationship, fontSize = 8.sp, fontWeight = FontWeight.Bold, color = PrimaryDeepPurple)
                            }
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(contact.phone, fontSize = 12.sp, color = MutedSlateSub)
                    }
                    Row {
                        IconButton(onClick = {
                            try {
                                val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${contact.phone}"))
                                dialIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                // Trigger check
                            } catch (e: Exception) {
                                // Safe catch in emulator/tests
                            }
                        }) {
                            Icon(Icons.Default.Call, contentDescription = "Llamar", tint = TerciaryWarmTeal)
                        }
                        // Delete support contact button (ignore system governmental pre-populations manually handled in VM)
                        if (contact.relationship != "Gubernamental / Emergencias") {
                            IconButton(onClick = { viewModel.deleteContact(contact.id) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = AlertSoftRed)
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
    }
}

// PROFILE CONFIGURATION SCREEN
@Composable
fun ProfileScreen(
    viewModel: TribuMentalViewModel,
    onBack: () -> Unit,
    onRetakeTest: () -> Unit,
    onLogout: () -> Unit
) {
    val context = LocalContext.current
    val profile by viewModel.userProfile.collectAsStateWithLifecycle()

    var name by remember { mutableStateOf("") }
    var stage by remember { mutableStateOf("EMBARAZADA") }
    var weeksOrMonths by remember { mutableStateOf(12) }
    var concern by remember { mutableStateOf("") }
    var optWhatsApp by remember { mutableStateOf(false) }
    var reminderFreq by remember { mutableStateOf("Diario") }
    var planName by remember { mutableStateOf("GRATIS") }
    
    // Additional parameters
    var age by remember { mutableStateOf(28) }
    var location by remember { mutableStateOf("") }
    var previousPregnancies by remember { mutableStateOf(0) }
    var supportNetwork by remember { mutableStateOf("Pareja") }

    var isInitialized by remember { mutableStateOf(false) }

    LaunchedEffect(profile) {
        profile?.let {
            if (!isInitialized) {
                name = it.name
                stage = it.stage
                weeksOrMonths = it.weeksOrMonths
                concern = it.concern
                optWhatsApp = it.optWhatsApp
                reminderFreq = it.reminderFrequency
                planName = it.billingPlan
                age = it.age
                location = it.location
                previousPregnancies = it.previousPregnancies
                supportNetwork = it.supportNetwork
                isInitialized = true
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(CreamBackground)
            .padding(20.dp)
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar", tint = PrimaryDeepPurple)
            }
            Text(
                text = Locales.string("profile_title", viewModel),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = PrimaryDeepPurple,
                fontFamily = FontFamily.Serif
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (profile != null) {
            val photoPickerLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent()
            ) { uri ->
                if (uri != null) {
                    val localPath = ThemeConfig.copyUriToLocalStorage(context, uri)
                    viewModel.updateProfileAvatar(localPath)
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .clickable {
                                photoPickerLauncher.launch("image/*")
                            }
                            .testTag("profile_avatar_picker_box"),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        UserAvatar(
                            avatarPath = profile?.avatarUri,
                            size = 100.dp,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .background(SecondaryDustPink, CircleShape)
                                .align(Alignment.BottomEnd),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("📸", fontSize = 14.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (name.isNotBlank()) name else "Mamá Tribu",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = PrimaryDeepPurple,
                        fontFamily = FontFamily.Serif
                    )
                    Text(
                        text = "Toca la foto para personalizar tu avatar",
                        fontSize = 11.sp,
                        color = MutedSlateSub
                    )
                }
            }

            // MENTAL SCORE STATUS SECTION
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = WarmCardWhite),
                border = BorderStroke(1.dp, SoftBorderPlum),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = Locales.string("profile_status_title", viewModel),
                        fontWeight = FontWeight.Bold,
                        color = PrimaryDeepPurple,
                        fontSize = 15.sp,
                        fontFamily = FontFamily.Serif
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    if (profile!!.testScore != null) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(54.dp)
                                    .background(SoftLila, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${profile!!.testScore}/10",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = PrimaryDeepPurple
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                val label = when (profile!!.testScore) {
                                    in 9..10 -> "Bienestar Óptimo 🌸"
                                    in 7..8 -> "Bienestar Moderado ✨"
                                    in 5..6 -> "Alerta de Estrés Leve ⚠️"
                                    else -> "Alerta de Contención Prioritaria 🚨"
                                }
                                Text(
                                    text = label,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = PrimaryDeepPurple
                                )
                                Text(
                                    text = Locales.string("profile_score_label", viewModel),
                                    fontSize = 11.sp,
                                    color = MutedSlateSub
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(color = SoftBorderPlum.copy(alpha = 0.5f))
                        Spacer(modifier = Modifier.height(14.dp))

                        // TRACKED RECOMMENDATIONS LIST
                        Text(
                            text = Locales.string("profile_recommendations_title", viewModel),
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = PrimaryDeepPurple
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        val recsString = profile!!.trackedRecommendations
                        if (recsString.isNotBlank()) {
                            val list = recsString.split("||")
                            list.forEach { item ->
                                val parts = item.split(":")
                                if (parts.size >= 2) {
                                    val recText = parts[0]
                                    val isCompleted = parts[1].toBoolean()

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                viewModel.toggleTrackedRecommendation(recText, !isCompleted)
                                            }
                                            .padding(vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Checkbox(
                                            checked = isCompleted,
                                            onCheckedChange = {
                                                viewModel.toggleTrackedRecommendation(recText, it)
                                            },
                                            colors = CheckboxDefaults.colors(checkedColor = SecondaryDustPink)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = recText,
                                            fontSize = 12.5.sp,
                                            color = if (isCompleted) MutedSlateSub else DeepCharcoalText,
                                            lineHeight = 16.sp,
                                            style = if (isCompleted) TextStyle(textDecoration = TextDecoration.LineThrough) else TextStyle.Default
                                        )
                                    }
                                }
                            }
                        } else {
                            Text(Locales.string("profile_recommendations_none", viewModel), fontSize = 12.sp, color = MutedSlateSub)
                        }
                    } else {
                        // Test not taken yet!
                        Text(
                            text = Locales.string("profile_score_none", viewModel),
                            fontSize = 12.sp,
                            color = DeepCharcoalText,
                            lineHeight = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = onRetakeTest,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SoftLila),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = if (profile!!.testScore != null) {
                                Locales.string("profile_retake_test", viewModel)
                            } else {
                                Locales.string("profile_start_test", viewModel)
                            },
                            color = PrimaryDeepPurple,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
            }

            // CORE PROFILE PARAMETERS CARD
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = WarmCardWhite),
                border = BorderStroke(1.dp, SoftBorderPlum),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = Locales.string("profile_edit", viewModel),
                        fontWeight = FontWeight.Bold,
                        color = PrimaryDeepPurple,
                        fontSize = 15.sp,
                        fontFamily = FontFamily.Serif
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    // LANGUAGE SELECTION CHIPS
                    Text(
                        text = Locales.string("lang_select", viewModel) + " 🌐",
                        fontWeight = FontWeight.Bold,
                        color = PrimaryDeepPurple,
                        fontSize = 12.5.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        val currentLang by viewModel.appLanguage.collectAsStateWithLifecycle()
                        val prefs = remember { context.getSharedPreferences("tribumental_prefs", Context.MODE_PRIVATE) }
                        
                        FilterChip(
                            selected = currentLang == "es",
                            onClick = {
                                viewModel.setLanguage("es")
                                prefs.edit().putString("app_language", "es").apply()
                            },
                            label = { Text(Locales.string("spanish", viewModel) + " 🇪🇸", fontSize = 12.sp) },
                            modifier = Modifier.weight(1f)
                        )
                        FilterChip(
                            selected = currentLang == "en",
                            onClick = {
                                viewModel.setLanguage("en")
                                prefs.edit().putString("app_language", "en").apply()
                            },
                            label = { Text(Locales.string("english", viewModel) + " 🇬🇧", fontSize = 12.sp) },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text(Locales.string("profile_name", viewModel)) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedTextField(
                            value = if (age > 0) "$age" else "",
                            onValueChange = { age = it.toIntOrNull() ?: 0 },
                            label = { Text(Locales.string("profile_age", viewModel)) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp)
                        )

                        OutlinedTextField(
                            value = location,
                            onValueChange = { location = it },
                            label = { Text(Locales.string("profile_location", viewModel)) },
                            modifier = Modifier.weight(1.5f),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(Locales.string("profile_stage", viewModel), fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MutedSlateSub)
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        FilterChip(
                            selected = stage == "EMBARAZADA",
                            onClick = { stage = "EMBARAZADA" },
                            label = { Text(Locales.string("profile_pregnant", viewModel)) },
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        FilterChip(
                            selected = stage == "POSPARTO",
                            onClick = { stage = "POSPARTO" },
                            label = { Text(Locales.string("profile_postpartum", viewModel)) },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (stage == "EMBARAZADA") {
                                Locales.string("profile_weeks_gravid", viewModel)
                            } else {
                                Locales.string("profile_months_postpartum", viewModel)
                            },
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp,
                            color = DeepCharcoalText
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.background(SoftLila, RoundedCornerShape(8.dp))
                        ) {
                            IconButton(onClick = { if (weeksOrMonths > 1) weeksOrMonths-- }) {
                                Text("-", color = PrimaryDeepPurple)
                            }
                            Text("$weeksOrMonths", color = PrimaryDeepPurple, fontWeight = FontWeight.Bold)
                            IconButton(onClick = { weeksOrMonths++ }) {
                                Text("+", color = PrimaryDeepPurple)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = Locales.string("profile_prev_pregnancies", viewModel),
                                fontWeight = FontWeight.Medium,
                                fontSize = 13.sp,
                                color = DeepCharcoalText
                            )
                            Text(
                                text = Locales.string("profile_prev_preg_sub", viewModel),
                                fontSize = 10.sp,
                                color = MutedSlateSub
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.background(SoftLila, RoundedCornerShape(8.dp))
                        ) {
                            IconButton(onClick = { if (previousPregnancies > 0) previousPregnancies-- }) {
                                Text("-", color = PrimaryDeepPurple)
                            }
                            Text("$previousPregnancies", color = PrimaryDeepPurple, fontWeight = FontWeight.Bold)
                            IconButton(onClick = { previousPregnancies++ }) {
                                Text("+", color = PrimaryDeepPurple)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedTextField(
                        value = concern,
                        onValueChange = { concern = it },
                        label = { Text(Locales.string("profile_concern", viewModel)) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(Locales.string("profile_support", viewModel), fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MutedSlateSub)
                    Spacer(modifier = Modifier.height(4.dp))
                    listOf(
                        "Pareja" to Locales.string("profile_support_partner", viewModel),
                        "Familia/Amigos" to Locales.string("profile_support_family", viewModel),
                        "Ninguna" to Locales.string("profile_support_none", viewModel)
                    ).forEach { (key, label) ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { supportNetwork = key }
                                .padding(vertical = 2.dp)
                        ) {
                            RadioButton(
                                selected = supportNetwork == key,
                                onClick = { supportNetwork = key },
                                colors = RadioButtonDefaults.colors(selectedColor = SecondaryDustPink)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(label, fontSize = 12.5.sp, color = DeepCharcoalText)
                        }
                    }

                    // Cloud Sync Info Panel
                    Spacer(modifier = Modifier.height(14.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("cloud_sync_info_card"),
                        colors = CardDefaults.cardColors(containerColor = SoftLila.copy(alpha = 0.15f)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = "Sincronización en la nube",
                                    tint = PrimaryDeepPurple,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = Locales.string("profile_sync_title", viewModel),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = PrimaryDeepPurple
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = Locales.string("profile_sync_desc", viewModel),
                                fontSize = 11.sp,
                                color = DeepCharcoalText.copy(alpha = 0.85f)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = Locales.string("profile_sync_active", viewModel),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TerciaryWarmTeal
                                )
                                Text(
                                    text = if (!profile?.email.isNullOrBlank()) "Cuenta: ${profile?.email}" else "Perfil Local (Sincronizado)",
                                    fontSize = 10.sp,
                                    color = MutedSlateSub
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = optWhatsApp, onCheckedChange = { optWhatsApp = it })
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(Locales.string("profile_whatsapp_active", viewModel), fontSize = 12.sp, color = DeepCharcoalText)
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            viewModel.completeOnboarding(
                                name,
                                stage,
                                weeksOrMonths,
                                concern,
                                optWhatsApp,
                                reminderFreq,
                                planName,
                                age,
                                supportNetwork,
                                previousPregnancies,
                                location
                            )
                            onBack()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryDeepPurple),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                    ) {
                        Text(Locales.string("profile_save", viewModel), color = Color.White, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedButton(
                        onClick = {
                            viewModel.logoutUser {
                                onLogout()
                            }
                        },
                        border = BorderStroke(1.5.dp, AlertSoftRed),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = AlertSoftRed),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("auth_logout_btn")
                    ) {
                        Text(Locales.string("profile_logout", viewModel) + " 🚪", fontWeight = FontWeight.Bold, color = AlertSoftRed)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
    }
}

// PSYCHOLOGICAL TEST SCREEN (15 QUESTIONS)
@Composable
fun TribuPsychologicalTestScreen(
    viewModel: TribuMentalViewModel,
    onFinished: () -> Unit
) {
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var rawScore by remember { mutableStateOf(0) }
    var isTestCompleted by remember { mutableStateOf(false) }

    val questions = remember {
        listOf(
            "¿Te has sentido capaz de reír y ver el lado divertido de las cosas?",
            "¿Has mirado al futuro con ilusión y motivación?",
            "¿Te has culpado innecesariamente cuando las cosas no salían como querías?",
            "¿Has estado preocupada o ansiosa sin un motivo justificado?",
            "¿Has sentido miedo o pánico sin razones aparentes para ello?",
            "¿Te has sentido abrumada o sobrepasada por las tareas de la vida diaria?",
            "¿Te ha cuesta conciliar el sueño o descansar bien por las noches?",
            "¿Te has sentido con tristeza, desánimo o con ganas frecuentes de llorar?",
            "¿Sientes desinterés por las actividades lúdicas o de recreación que antes amabas?",
            "¿Te resulta muy difícil concentrarte o centrar tu atención en algo?",
            "¿Sientes cansancio o fatiga extrema persistente que no cede con el descanso?",
            "¿Has experimentado tensión física recurrente (cuello, espalda, cabeza) por estrés?",
            "¿Sientes el respaldo emocional necesario por parte de tu familia y pareja?",
            "¿Has experimentado cambios muy notorios en tu apetito en las últimas semanas?",
            "¿Te sientes confiada y optimista respecto al vínculo afectivo con tu bebé?"
        )
    }

    val options = listOf(
        "Nunca o casi nunca",
        "Pocas veces",
        "Con frecuencia",
        "Casi siempre"
    )

    // Calculate score out of 1-10 and recommendations upon completion
    val finalScore = remember(rawScore) {
        val calculated = ((rawScore.toFloat() / 45f) * 9f + 1f).roundToInt()
        calculated.coerceIn(1, 10)
    }

    val (verdict, recommendations) = remember(finalScore) {
        val verd = when (finalScore) {
            in 9..10 -> "Bienestar Óptimo 🌸"
            in 7..8 -> "Bienestar Moderado ✨"
            in 5..6 -> "Alerta de Estrés Leve ⚠️"
            else -> "Alerta de Contención Prioritaria 🚨"
        }
        val recs = when (finalScore) {
            in 9..10 -> listOf(
                "Mantener rutinas diarias de autocuidado de 15 minutos",
                "Realizar caminatas pausadas al aire libre",
                "Unirse a grupos de apoyo de Tribu"
            )
            in 7..8 -> listOf(
                "Incorporar pausas de respiración profunda consciente",
                "Establecer límites claros en tus responsabilidades diarias",
                "Delegar al menos 2 tareas mecánicas del hogar"
            )
            in 5..6 -> listOf(
                "Hablar abiertamente de tus emociones con alguien de confianza",
                "Dormir 30 minutos extra en una siesta ligera diurna",
                "Programar una sesión conversacional con psicología"
            )
            else -> listOf(
                "Contactar proactivamente con tu red de apoyo o emergencias",
                "Agendar una videoconsulta psicológica prioritaria en la App",
                "Reducir al mínimo absoluto las presiones y tareas extras no vitales",
                "Mantener el canal Tribu WhatsApp activo para alertas"
            )
        }
        Pair(verd, recs)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(CreamBackground)
            .padding(24.dp)
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        if (!isTestCompleted) {
            // STEP PROGRESS HEADER
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Screening de Bienestar Emocional",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = PrimaryDeepPurple,
                    fontFamily = FontFamily.Serif,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "Q.${currentQuestionIndex + 1} de 15",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = SecondaryDustPink
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            LinearProgressIndicator(
                progress = (currentQuestionIndex + 1).toFloat() / 15f,
                color = SecondaryDustPink,
                trackColor = SoftLila,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
            )

            Spacer(modifier = Modifier.height(40.dp))

            // QUESTION CARD
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = WarmCardWhite),
                border = BorderStroke(1.dp, SoftBorderPlum),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = questions[currentQuestionIndex],
                        fontWeight = FontWeight.Bold,
                        fontSize = 19.sp,
                        color = PrimaryDeepPurple,
                        lineHeight = 26.sp,
                        fontFamily = FontFamily.Serif
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    options.forEachIndexed { optIndex, optText ->
                        Button(
                            onClick = {
                                val points = optIndex
                                rawScore += points

                                if (currentQuestionIndex < 14) {
                                    currentQuestionIndex++
                                } else {
                                    isTestCompleted = true
                                    viewModel.savePsychologicalTestResult(finalScore, recommendations)
                                }
                            },
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SoftLila.copy(alpha = 0.5f), contentColor = DeepCharcoalText),
                            border = BorderStroke(1.dp, SoftBorderPlum),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .padding(vertical = 5.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .background(SecondaryDustPink, CircleShape),
                                    contentAlignment = Alignment.Center
                                   ) {
                                    Text(
                                        text = "${'A' + optIndex}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = Color.White
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = optText,
                                    fontSize = 13.5.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = DeepCharcoalText,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Responde con honestidad. TribuMental resguarda tus datos bajo encriptación médica exclusiva.",
                fontSize = 11.sp,
                color = MutedSlateSub,
                style = MaterialTheme.typography.bodyMedium,
                lineHeight = 15.sp
            )
        } else {
            // TEST COMPLETED SCREEN
            Text(
                text = "¡Análisis Listo! 🌸",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryDeepPurple,
                fontFamily = FontFamily.Serif
            )
            Text(
                text = "Hemos procesado tus respuestas bajo metodologías de bienestar clínico.",
                fontSize = 12.sp,
                color = MutedSlateSub
            )

            Spacer(modifier = Modifier.height(24.dp))

            // SCORE HERO CARD
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = WarmCardWhite),
                border = BorderStroke(2.dp, SecondaryDustPink),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(
                                color = SoftLila,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "$finalScore/10",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 28.sp,
                                color = PrimaryDeepPurple
                            )
                            Text(
                                text = "Mental Score",
                                fontSize = 10.sp,
                                color = SecondaryDustPink,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = verdict,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp,
                        color = PrimaryDeepPurple
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "El puntaje 10 representa equilibrio y resiliencia máxima. Tu estado actual aconseja las siguientes medidas directas para conservar y enriquecer tu salud materna:",
                        fontSize = 12.sp,
                        color = DeepCharcoalText,
                        lineHeight = 16.sp
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    HorizontalDivider(color = SoftBorderPlum, thickness = 1.dp)

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "📋 Recomendaciones Clave asignadas:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = PrimaryDeepPurple,
                        modifier = Modifier.align(Alignment.Start)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    recommendations.forEachIndexed { idx, rec ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text("🔸", fontSize = 14.sp, modifier = Modifier.padding(top = 2.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = rec,
                                fontSize = 12.5.sp,
                                lineHeight = 16.sp,
                                color = DeepCharcoalText
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SageGreen.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                Text(
                    text = "💡 Las recomendaciones han sido añadidas a tu perfil. Podrás marcarlas como 'Completadas' y hacerles seguimiento diario desde tu área de perfil.",
                    fontSize = 11.sp,
                    color = DeepCharcoalText,
                    lineHeight = 15.sp
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = onFinished,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryDeepPurple),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Finalizar e Ir al Dashboard", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color.White)
            }
        }
    }
}

@Composable
fun UserAvatar(
    avatarPath: String?,
    size: androidx.compose.ui.unit.Dp,
    modifier: Modifier = Modifier,
    placeholderIcon: androidx.compose.ui.graphics.vector.ImageVector = Icons.Default.Person
) {
    val bitmap = remember(avatarPath) {
        if (!avatarPath.isNullOrEmpty()) {
            try {
                android.graphics.BitmapFactory.decodeFile(avatarPath)
            } catch (e: Exception) {
                null
            }
        } else {
            null
        }
    }

    if (bitmap != null) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "Foto de perfil",
            contentScale = androidx.compose.ui.layout.ContentScale.Crop,
            modifier = modifier
                .size(size)
                .clip(CircleShape)
        )
    } else {
        Box(
            modifier = modifier
                .size(size)
                .clip(CircleShape)
                .background(SoftLila),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = placeholderIcon,
                contentDescription = "Perfil",
                tint = PrimaryDeepPurple,
                modifier = Modifier.size(size * 0.55f)
            )
        }
    }
}
