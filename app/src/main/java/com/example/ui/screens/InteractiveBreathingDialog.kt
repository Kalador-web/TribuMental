package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.*
import com.example.viewmodel.TribuMentalViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun InteractiveBreathingDialog(
    viewModel: TribuMentalViewModel,
    onDismiss: () -> Unit
) {
    var step by remember { mutableStateOf(0) } 
    // step: 0 = Intro, 
    // 1 = Inhala (4s), 
    // 2 = Retén (7s), 
    // 3 = Exhala (8s), 
    // 4 = Loop prompt, 
    // 5 = Q1 Color/Tensión, 
    // 6 = Q2 Hombros/Cuello, 
    // 7 = Q3 Score 1-10, 
    // 8 = AI Loading, 
    // 9 = AI Result

    var countdown by remember { mutableStateOf(4) }
    var runningActive by remember { mutableStateOf(false) }

    // Answers
    var answerQ1 by remember { mutableStateOf("") }
    var answerQ2 by remember { mutableStateOf("") }
    var answerQ3 by remember { mutableStateOf(5) } // slider 1-10

    val feedbackResult by viewModel.breathingAdviceResult.collectAsState()
    val isAnalyzing by viewModel.isAnalyzingBreathingAnswers.collectAsState()

    // Countdown state machine logic
    LaunchedEffect(step, runningActive) {
        if (!runningActive) return@LaunchedEffect
        if (step == 1) {
            countdown = 4
            while (countdown > 0) {
                delay(1000)
                countdown--
            }
            step = 2
        } else if (step == 2) {
            countdown = 7
            while (countdown > 0) {
                delay(1000)
                countdown--
            }
            step = 3
        } else if (step == 3) {
            countdown = 8
            while (countdown > 0) {
                delay(1000)
                countdown--
            }
            step = 4
            runningActive = false
        }
    }

    Dialog(
        onDismissRequest = { if (step == 0 || step == 4 || step == 9) onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = WarmCardWhite),
                border = BorderStroke(1.5.dp, SoftLila),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Title Bar with close action
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("🧘‍♀️", fontSize = 24.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Respiración Guiada con IA",
                                fontWeight = FontWeight.Bold,
                                color = PrimaryDeepPurple,
                                fontSize = 16.sp,
                                fontFamily = FontFamily.Serif
                            )
                        }
                        IconButton(onClick = onDismiss) {
                            Icon(Icons.Default.Close, contentDescription = "Cerrar", tint = MutedSlateSub)
                        }
                    }

                    Divider(color = SoftBorderPlum.copy(alpha = 0.6f), modifier = Modifier.padding(vertical = 12.dp))

                    AnimatedContent(
                        targetState = step,
                        label = "breathing_step_transition"
                    ) { currentStep ->
                        when (currentStep) {
                            0 -> { // Intro
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(76.dp)
                                            .clip(CircleShape)
                                            .background(SageGreen.copy(alpha = 0.5f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text("🌸", fontSize = 36.sp)
                                    }
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(
                                        "Acompañamiento de Mindfulness",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 17.sp,
                                        color = PrimaryDeepPurple,
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        "Hola mamá. Soy tu guía de bienestar TribuMental, potenciada por IA Gemini.\n\nTomémonos unos momentos de quietud para equilibrar tu sistema nervioso con la respiración 4-7-8, un método clínicamente probado para reducir el cortisol y calmar la mente.\n\n¿Deseas iniciar tu ejercicio guiado interactivo?",
                                        fontSize = 12.5.sp,
                                        color = DeepCharcoalText,
                                        lineHeight = 18.sp,
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(modifier = Modifier.height(24.dp))
                                    Button(
                                        onClick = {
                                            step = 1
                                            runningActive = true
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = TerciaryWarmTeal),
                                        shape = RoundedCornerShape(14.dp),
                                        modifier = Modifier.fillMaxWidth().height(48.dp)
                                    ) {
                                        Text(
                                            "Comenzar Respiración Guiada",
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White,
                                            fontSize = 13.5.sp
                                        )
                                    }
                                }
                            }
                            1, 2, 3 -> { // Main Breathing Routine Loops
                                val stepTitle = when (currentStep) {
                                    1 -> "¡Inhala!"
                                    2 -> "Retén el Aire..."
                                    else -> "Exhala con Calma..."
                                }
                                val stepInstructions = when (currentStep) {
                                    1 -> "Inhala aire puro por la nariz, expandiendo suavemente tu diafragma y abdomen..."
                                    2 -> "Mantén el aire adentro. Imagina la quietud fluyendo hacia cada filamento de tu pecho..."
                                    else -> "Suelta el aire por la boca lentamente, liberando todo el cansancio y las dudas del posparto..."
                                }
                                val stateCircleScale = when (currentStep) {
                                    1 -> 1.35f
                                    2 -> 1.35f
                                    else -> 0.9f
                                }

                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.padding(vertical = 12.dp)
                                ) {
                                    Text(
                                        stepTitle,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 22.sp,
                                        color = SecondaryDustPink
                                    )
                                    Spacer(modifier = Modifier.height(18.dp))
                                    
                                    // Visual Expanding Breathing Circle
                                    Box(
                                        modifier = Modifier.size(160.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        // Outer pulsing soft rings
                                        Box(
                                            modifier = Modifier
                                                .size((140 * stateCircleScale).dp)
                                                .clip(CircleShape)
                                                .background(SoftLila.copy(alpha = 0.35f))
                                        )
                                        Box(
                                            modifier = Modifier
                                                .size((105 * stateCircleScale).dp)
                                                .clip(CircleShape)
                                                .background(SageGreen.copy(alpha = 0.6f))
                                        )
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(
                                                text = "$countdown s",
                                                fontWeight = FontWeight.Black,
                                                fontSize = 28.sp,
                                                color = PrimaryDeepPurple
                                            )
                                            Text(
                                                text = if (currentStep == 1) "Inhalar" else if (currentStep == 2) "Retener" else "Soltar",
                                                fontSize = 11.sp,
                                                color = DeepCharcoalText,
                                                fontWeight = FontWeight.Medium
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(18.dp))
                                    Text(
                                        text = stepInstructions,
                                        fontSize = 13.sp,
                                        color = DeepCharcoalText,
                                        lineHeight = 18.sp,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(horizontal = 8.dp)
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    TextButton(
                                        onClick = {
                                            runningActive = false
                                            step = 4
                                        }
                                    ) {
                                        Text("Saltar ejercicio e ir a preguntas", color = MutedSlateSub, fontSize = 11.sp)
                                    }
                                }
                            }
                            4 -> { // Loop decision or go to Questions
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.padding(vertical = 12.dp)
                                ) {
                                    Text("¡Completaste el Ciclo de Respiración! 🌬️", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = PrimaryDeepPurple)
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        "Tu mente y cuerpo agradecen estas pausas conscientes, mamá. ¿Deseas hacer un segundo ciclo para profundizar el bienestar o prefieres pasar a la evaluación asistida por IA sobre cómo te sientes?",
                                        fontSize = 12.sp,
                                        color = DeepCharcoalText,
                                        lineHeight = 17.sp,
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(modifier = Modifier.height(20.dp))
                                    Button(
                                        onClick = {
                                            step = 1
                                            runningActive = true
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = SageGreen.copy(alpha = 0.9f)),
                                        modifier = Modifier.fillMaxWidth().height(45.dp),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Text("🔁 Respirar de nuevo", color = PrimaryDeepPurple, fontWeight = FontWeight.Bold, fontSize = 12.5.sp)
                                    }
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Button(
                                        onClick = { step = 5 },
                                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryDeepPurple),
                                        modifier = Modifier.fillMaxWidth().height(45.dp),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text("Comenzar Preguntas de la IA", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.5.sp)
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = Color.White)
                                        }
                                    }
                                }
                            }
                            5 -> { // Question 1
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                ) {
                                    Text("Pregunta 1 de 3 🌸", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = SecondaryDustPink)
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        "¿Cómo te sentías emocionalmente antes de la respiración y de qué color te imaginas esa tensión acumulada?",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = PrimaryDeepPurple,
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(modifier = Modifier.height(14.dp))

                                    // Quick tags options
                                    val q1Options = listOf(
                                        "Agobiada de color Gris ceniza",
                                        "Ansiosa de color Rojo fuego",
                                        "Cansada de color Azul opaco",
                                        "Algo tensa de color Morado oscuro",
                                    )
                                    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                                        q1Options.forEach { opt ->
                                            val isSelected = answerQ1 == opt
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .border(
                                                        width = 1.5.dp,
                                                        color = if (isSelected) TerciaryWarmTeal else SoftBorderPlum,
                                                        shape = RoundedCornerShape(12.dp)
                                                    )
                                                    .background(if (isSelected) SageGreen.copy(alpha = 0.4f) else Color.Transparent)
                                                    .clickable { answerQ1 = opt }
                                                    .padding(12.dp)
                                            ) {
                                                Text(opt, fontSize = 12.sp, color = DeepCharcoalText)
                                            }
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(12.dp))
                                    OutlinedTextField(
                                        value = answerQ1,
                                        onValueChange = { answerQ1 = it },
                                        placeholder = { Text("Escribe tu propia respuesta aquí...", fontSize = 11.sp) },
                                        modifier = Modifier.fillMaxWidth(),
                                        maxLines = 2,
                                        shape = RoundedCornerShape(10.dp)
                                    )

                                    Spacer(modifier = Modifier.height(20.dp))
                                    Button(
                                        onClick = {
                                            if (answerQ1.isEmpty()) {
                                                answerQ1 = "Un poco de agobio de color gris"
                                            }
                                            step = 6
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryDeepPurple),
                                        shape = RoundedCornerShape(12.dp),
                                        modifier = Modifier.fillMaxWidth().height(46.dp)
                                    ) {
                                        Text("Continuar", color = Color.White, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                            6 -> { // Question 2
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                ) {
                                    Text("Pregunta 2 de 3 🌸", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = SecondaryDustPink)
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        "Durante las pausas de retención y exhalación del ejercicio, ¿conseguiste aflojar el pecho y cuello, o sentiste dificultad?",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = PrimaryDeepPurple,
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(modifier = Modifier.height(14.dp))

                                    // Quick tags options
                                    val q2Options = listOf(
                                        "Sí, logré aflojar por completo y sentir ligereza ✨",
                                        "Siento el pecho todavía un poco apretado 🌸",
                                        "Se siente ligera rigidez persistente en el cuello",
                                        "Me costó retener, pero exhalar fue liberador"
                                    )
                                    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                                        q2Options.forEach { opt ->
                                            val isSelected = answerQ2 == opt
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .border(
                                                        width = 1.5.dp,
                                                        color = if (isSelected) TerciaryWarmTeal else SoftBorderPlum,
                                                        shape = RoundedCornerShape(12.dp)
                                                    )
                                                    .background(if (isSelected) SageGreen.copy(alpha = 0.4f) else Color.Transparent)
                                                    .clickable { answerQ2 = opt }
                                                    .padding(12.dp)
                                            ) {
                                                Text(opt, fontSize = 12.sp, color = DeepCharcoalText)
                                            }
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(12.dp))
                                    OutlinedTextField(
                                        value = answerQ2,
                                        onValueChange = { answerQ2 = it },
                                        placeholder = { Text("Describe detalles de tus sensaciones corporales...", fontSize = 11.sp) },
                                        modifier = Modifier.fillMaxWidth(),
                                        maxLines = 2,
                                        shape = RoundedCornerShape(10.dp)
                                    )

                                    Spacer(modifier = Modifier.height(20.dp))
                                    Button(
                                        onClick = {
                                            if (answerQ2.isEmpty()) {
                                                answerQ2 = "Logré aflojar un poco la tensión del cuello"
                                            }
                                            step = 7
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryDeepPurple),
                                        shape = RoundedCornerShape(12.dp),
                                        modifier = Modifier.fillMaxWidth().height(46.dp)
                                    ) {
                                        Text("Continuar", color = Color.White, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                            7 -> { // Question 3
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                ) {
                                    Text("Pregunta 3 de 3 🌸", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = SecondaryDustPink)
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        "Del 1 al 10 (donde 10 es paz absoluta), ¿cómo calificarías tu quietud y flujo de calma interior en este momento?",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = PrimaryDeepPurple,
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(modifier = Modifier.height(24.dp))

                                    Text(
                                        text = "$answerQ3 / 10",
                                        fontSize = 32.sp,
                                        fontWeight = FontWeight.Black,
                                        color = TerciaryWarmTeal
                                    )
                                    Text(
                                        text = when(answerQ3) {
                                            in 1..3 -> "Tensión levemente persistente"
                                            in 4..6 -> "Calma moderada y receptiva"
                                            in 7..8 -> "Profunda relajación y alivio ✨"
                                            else -> "Paz absoluta y armonía radiante 🌸"
                                        },
                                        fontSize = 11.sp,
                                        color = DeepCharcoalText,
                                        fontWeight = FontWeight.Medium
                                    )

                                    Spacer(modifier = Modifier.height(14.dp))
                                    Slider(
                                        value = answerQ3.toFloat(),
                                        onValueChange = { answerQ3 = it.roundToInt() },
                                        valueRange = 1f..10f,
                                        steps = 8,
                                        colors = SliderDefaults.colors(
                                            thumbColor = TerciaryWarmTeal,
                                            activeTrackColor = TerciaryWarmTeal,
                                            inactiveTrackColor = SoftBorderPlum
                                        ),
                                        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
                                    )

                                    Spacer(modifier = Modifier.height(30.dp))
                                    Button(
                                        onClick = {
                                            step = 8
                                            viewModel.analyzeBreathingAnswers(answerQ1, answerQ2, answerQ3)
                                            // Check outcome
                                            // When advice is loaded from VM, it redirects step to 9 via a LaunchedEffect below
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryDeepPurple),
                                        shape = RoundedCornerShape(12.dp),
                                        modifier = Modifier.fillMaxWidth().height(46.dp)
                                    ) {
                                        Text("Consultar Análisis de la IA", color = Color.White, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                            8 -> { // AI Loading
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.padding(vertical = 32.dp)
                                ) {
                                    CircularProgressIndicator(color = TerciaryWarmTeal)
                                    Spacer(modifier = Modifier.height(20.dp))
                                    Text(
                                        "La IA de TribuMental está analizando tus respuestas...",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = PrimaryDeepPurple,
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        "Sincronizando de forma segura para darte una validación cálida y personalizada con Gemini.",
                                        fontSize = 11.sp,
                                        color = MutedSlateSub,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(horizontal = 12.dp)
                                    )
                                    
                                    // LaunchedEffect inside loader redirecting step to 9 when result is obtained
                                    LaunchedEffect(feedbackResult) {
                                        if (feedbackResult != null) {
                                            step = 9
                                        }
                                    }
                                }
                            }
                            9 -> { // AI Result
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(50.dp)
                                            .clip(CircleShape)
                                            .background(Color(0xFFE0F2FE)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text("✨", fontSize = 24.sp)
                                    }
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(
                                        "Eco de Calma de la IA",
                                        fontWeight = FontWeight.Bold,
                                        color = PrimaryDeepPurple,
                                        fontSize = 16.sp
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))

                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(SageGreen.copy(alpha = 0.4f), RoundedCornerShape(14.dp))
                                            .border(1.dp, SoftBorderPlum, RoundedCornerShape(14.dp))
                                            .padding(14.dp)
                                    ) {
                                        Text(
                                            text = feedbackResult ?: "Excelente sesión de autoregulación. La madre asume la pauta con madurez emocional.",
                                            fontSize = 12.5.sp,
                                            color = DeepCharcoalText,
                                            lineHeight = 18.sp,
                                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(20.dp))
                                    Button(
                                        onClick = {
                                            onDismiss()
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = TerciaryWarmTeal),
                                        shape = RoundedCornerShape(12.dp),
                                        modifier = Modifier.fillMaxWidth().height(46.dp)
                                    ) {
                                        Text("Guardar y Volver a Tribu", color = Color.White, fontWeight = FontWeight.Bold)
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
