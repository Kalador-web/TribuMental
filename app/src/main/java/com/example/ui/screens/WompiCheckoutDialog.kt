package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
<<<<<<< HEAD
import androidx.compose.ui.platform.LocalContext
=======
>>>>>>> d1242aa4b6f034d485b2b338743c91cab3206719
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
<<<<<<< HEAD
import androidx.browser.customtabs.CustomTabsIntent
import android.net.Uri
import android.content.Context
=======
>>>>>>> d1242aa4b6f034d485b2b338743c91cab3206719
import com.example.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID

// Signature Wompi Colors
val WompiPurple = Color(0xFF6F00FF)
val WompiPurpleLight = Color(0xFFECF0FF)
val WompiGrayBg = Color(0xFFF4F6F9)

<<<<<<< HEAD
/**
 * Función que abre la pasarela de pago real de Wompi usando Android Custom Tabs.
 */
fun abrirPasarelaPagoReal(context: Context, plan: String, emailUsuario: String) {
    val miLlavePublica = com.example.BuildConfig.WOMPI_PUBLIC_KEY
    val valorEnCentavos = if (plan == "MENSUAL") "3990000" else if (plan == "FAMILIAR") "5990000" else "29990000"
    val referenciaUnica = "ID_TRIBU_${System.currentTimeMillis()}"
    val moneda = "COP"

    // URL de cobro web estandarizada de Wompi
    val urlWompi = "https://checkout.wompi.co/p/?public-key=$miLlavePublica" +
            "&currency=$moneda" +
            "&amount-in-cents=$valorEnCentavos" +
            "&reference=$referenciaUnica" +
            "&customer-email=$emailUsuario" +
            "&redirect-url=https://tu-app-maternal.web.app/pago-exitoso"

    val intent = CustomTabsIntent.Builder()
        .setToolbarColor(android.graphics.Color.parseColor("#6F00FF")) // Color WompiPurple
        .setShowTitle(true)
        .build()
    intent.launchUrl(context, Uri.parse(urlWompi))
}

=======
>>>>>>> d1242aa4b6f034d485b2b338743c91cab3206719
@Composable
fun WompiCheckoutDialog(
    planName: String,
    onDismiss: () -> Unit,
    onPaymentSuccess: (transactionId: String, method: String) -> Unit
) {
    var checkoutStep by remember { mutableStateOf("FORM") } // "FORM", "PROCESSING", "OTP", "VERIFYING_OTP", "SUCCESS"
    var paymentMethod by remember { mutableStateOf("CARD") } // "CARD", "PSE", "NEQUI"

    // Card Fields
    var cardNumber by remember { mutableStateOf("") }
    var cardExpiry by remember { mutableStateOf("") }
    var cardCvc by remember { mutableStateOf("") }
    var cardHolder by remember { mutableStateOf("") }
    var cardEmail by remember { mutableStateOf("") }
    var cardCuotas by remember { mutableStateOf("1") }

    // PSE Fields
    var pseBank by remember { mutableStateOf("Bancolombia") }
    var pseDocType by remember { mutableStateOf("C.C.") }
    var pseDocNum by remember { mutableStateOf("") }
    var pseName by remember { mutableStateOf("") }
    var pseEmail by remember { mutableStateOf("") }

    // Nequi Fields
    var nequiPhone by remember { mutableStateOf("") }

    // OTP/Signature Verification
    var otpCode by remember { mutableStateOf("") }

    // Shared Statuses
    var acceptTerms by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var generatedTxId by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()
<<<<<<< HEAD
    val context = LocalContext.current
=======
>>>>>>> d1242aa4b6f034d485b2b338743c91cab3206719

    val planPriceText = when (planName) {
        "MENSUAL" -> "$39.900 COP / mes"
        "FAMILIAR" -> "$59.900 COP / mes"
        "ANUAL" -> "$299.900 COP / año"
        else -> "$15.000 COP / mes"
    }

    val finalPriceTextNumberOnly = when (planName) {
        "MENSUAL" -> "$39.900 COP"
        "FAMILIAR" -> "$59.900 COP"
        "ANUAL" -> "$299.900 COP"
        else -> "$15.000 COP"
    }

    Dialog(
        onDismissRequest = { if (checkoutStep == "FORM" || checkoutStep == "SUCCESS") onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.6f))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 12.dp)
                    .border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(24.dp)),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    // Header logo Wompi
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(WompiPurple),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "w",
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Serif
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "wompi",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = Color(0xFF1F1F1F)
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .background(SageGreen.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                Icons.Default.Lock,
                                contentDescription = null,
                                tint = TerciaryWarmTeal,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                "Pago Seguro",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = DeepCharcoalText
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    if (checkoutStep == "FORM") {
                        // Subtitle summary of subscription
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(WompiPurpleLight, RoundedCornerShape(14.dp))
                                .padding(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "Suscripción TribuMental",
                                        fontSize = 11.sp,
                                        color = WompiPurple,
                                        fontWeight = FontWeight.Normal
                                    )
                                    Text(
                                        text = if (planName == "MENSUAL") "Plan Mensual ⭐ Premium" else "Plan Familiar 👥 Platinum",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF1E293B)
                                    )
                                }
                                Text(
                                    text = planPriceText,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = WompiPurple
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Tab selectors
                        Text(
                            "Selecciona tu medio de pago:",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF475569),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(WompiGrayBg)
                                .padding(3.dp)
                        ) {
                            val tabs = listOf("CARD" to "💳 Tarjeta", "PSE" to "🏦 PSE", "NEQUI" to "📱 Nequi")
                            tabs.forEach { (id, label) ->
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (paymentMethod == id) Color.White else Color.Transparent)
                                        .border(
                                            width = if (paymentMethod == id) 1.dp else 0.dp,
                                            color = if (paymentMethod == id) Color(0xFFE2E8F0) else Color.Transparent,
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .clickable { paymentMethod = id }
                                        .padding(horizontal = 4.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = label,
                                        fontSize = 11.sp,
                                        fontWeight = if (paymentMethod == id) FontWeight.Bold else FontWeight.Medium,
                                        color = if (paymentMethod == id) WompiPurple else Color(0xFF64748B)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Tab Contents
                        when (paymentMethod) {
                            "CARD" -> {
                                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                    OutlinedTextField(
                                        value = cardNumber,
                                        onValueChange = { if (it.length <= 16) cardNumber = it },
                                        label = { Text("Número de Tarjeta", fontSize = 11.sp) },
                                        placeholder = { Text("4000 1234 5678 9010", fontSize = 11.sp) },
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                        modifier = Modifier.fillMaxWidth(),
                                        singleLine = true,
                                        shape = RoundedCornerShape(10.dp)
                                    )

                                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                        OutlinedTextField(
                                            value = cardExpiry,
                                            onValueChange = { cardExpiry = it },
                                            label = { Text("EXP (MM/AA)", fontSize = 11.sp) },
                                            placeholder = { Text("12/29", fontSize = 11.sp) },
                                            modifier = Modifier.weight(1f),
                                            singleLine = true,
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                        OutlinedTextField(
                                            value = cardCvc,
                                            onValueChange = { if (it.length <= 4) cardCvc = it },
                                            label = { Text("CVC", fontSize = 11.sp) },
                                            placeholder = { Text("123", fontSize = 11.sp) },
                                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                            modifier = Modifier.weight(1f),
                                            singleLine = true,
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                    }

                                    OutlinedTextField(
                                        value = cardHolder,
                                        onValueChange = { cardHolder = it },
                                        label = { Text("Nombre del Tarjetahabiente", fontSize = 11.sp) },
                                        modifier = Modifier.fillMaxWidth(),
                                        singleLine = true,
                                        shape = RoundedCornerShape(10.dp)
                                    )

                                    OutlinedTextField(
                                        value = cardEmail,
                                        onValueChange = { cardEmail = it },
                                        label = { Text("Correo Electrónico", fontSize = 11.sp) },
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                        modifier = Modifier.fillMaxWidth(),
                                        singleLine = true,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                }
                            }
                            "PSE" -> {
                                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                    // Simulated dropdown
                                    Text(
                                        "Banco seleccionado:",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF64748B)
                                    )
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .border(1.dp, Color(0xFFCBD5E1), RoundedCornerShape(10.dp))
                                            .clickable { /* Simula toggle */ }
                                            .padding(12.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(pseBank, fontSize = 12.sp, color = Color(0xFF1E293B))
                                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                                    }

                                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                        listOf("Bancolombia", "Nequi", "Daviplata", "Davivienda", "BBVA").forEach { bank ->
                                            Box(
                                                modifier = Modifier
                                                    .border(
                                                        width = 1.dp,
                                                        color = if (pseBank == bank) WompiPurple else Color(0xFFE2E8F0),
                                                        shape = RoundedCornerShape(8.dp)
                                                    )
                                                    .background(if (pseBank == bank) WompiPurpleLight else Color.Transparent)
                                                    .clickable { pseBank = bank }
                                                    .padding(horizontal = 8.dp, vertical = 6.dp)
                                            ) {
                                                Text(bank, fontSize = 10.sp, color = if (pseBank == bank) WompiPurple else Color(0xFF475569))
                                            }
                                        }
                                    }

                                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                        OutlinedTextField(
                                            value = pseDocType,
                                            onValueChange = { pseDocType = it },
                                            label = { Text("Tipo Doc", fontSize = 11.sp) },
                                            modifier = Modifier.weight(0.4f),
                                            singleLine = true,
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                        OutlinedTextField(
                                            value = pseDocNum,
                                            onValueChange = { pseDocNum = it },
                                            label = { Text("Número de Documento", fontSize = 11.sp) },
                                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                            modifier = Modifier.weight(0.6f),
                                            singleLine = true,
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                    }

                                    OutlinedTextField(
                                        value = pseName,
                                        onValueChange = { pseName = it },
                                        label = { Text("Nombre del Titular de la Cuenta", fontSize = 11.sp) },
                                        modifier = Modifier.fillMaxWidth(),
                                        singleLine = true,
                                        shape = RoundedCornerShape(10.dp)
                                    )

                                    OutlinedTextField(
                                        value = pseEmail,
                                        onValueChange = { pseEmail = it },
                                        label = { Text("Correo Electrónico Registrado", fontSize = 11.sp) },
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                        modifier = Modifier.fillMaxWidth(),
                                        singleLine = true,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                }
                            }
                            "NEQUI" -> {
                                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                    Text(
                                        "Ingresa el número celular de tu cuenta Nequi para recibir una notificación push de pago inmediata en tu app.",
                                        fontSize = 11.sp,
                                        color = Color(0xFF64748B),
                                        lineHeight = 15.sp
                                    )

                                    OutlinedTextField(
                                        value = nequiPhone,
                                        onValueChange = { if (it.length <= 10) nequiPhone = it },
                                        label = { Text("Número de Celular Nequi", fontSize = 11.sp) },
                                        placeholder = { Text("3123456789", fontSize = 11.sp) },
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                                        modifier = Modifier.fillMaxWidth(),
                                        singleLine = true,
                                        shape = RoundedCornerShape(10.dp)
                                    )

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(CreamBackground, RoundedCornerShape(8.dp))
                                            .padding(10.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text("📱", fontSize = 20.sp)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            "Recibirás una notificación en Nequi para autorizar el cobro recurrente mensual de forma activa.",
                                            fontSize = 9.5.sp,
                                            color = DeepCharcoalText,
                                            lineHeight = 13.sp
                                        )
                                    }
                                }
                            }
                        }

                        errorMessage?.let { msg ->
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "⚠️ $msg",
                                color = Color.Red,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Terms box
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clickable { acceptTerms = !acceptTerms }
                                .padding(vertical = 4.dp)
                        ) {
                            Checkbox(
                                checked = acceptTerms,
                                onCheckedChange = { acceptTerms = it },
                                colors = CheckboxDefaults.colors(checkedColor = WompiPurple)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Acepto los términos, condiciones y el reglamento de Wompi.",
                                fontSize = 10.sp,
                                color = Color(0xFF475569)
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Bottom Action Button
                        Button(
                            onClick = {
<<<<<<< HEAD
=======
                                when (paymentMethod) {
                                    "CARD" -> {
                                        if (cardNumber.length < 13) {
                                            errorMessage = "Número de tarjeta inválido."
                                            return@Button
                                        }
                                        if (cardExpiry.isEmpty() || cardCvc.length < 3) {
                                            errorMessage = "Verifica la expiración y código CVC."
                                            return@Button
                                        }
                                        if (cardHolder.isEmpty()) {
                                            errorMessage = "Nombre del titular requerido."
                                            return@Button
                                        }
                                    }
                                    "PSE" -> {
                                        if (pseDocNum.isEmpty() || pseName.isEmpty()) {
                                            errorMessage = "Por favor completa tu Documento y Nombre."
                                            return@Button
                                        }
                                    }
                                    "NEQUI" -> {
                                        if (nequiPhone.length < 10) {
                                            errorMessage = "Número de celular Nequi incompleto."
                                            return@Button
                                        }
                                    }
                                }
>>>>>>> d1242aa4b6f034d485b2b338743c91cab3206719
                                if (!acceptTerms) {
                                    errorMessage = "Debes aceptar los términos de la pasarela."
                                    return@Button
                                }

<<<<<<< HEAD
                                // INTEGRACIÓN REAL: Abrir Pasarela Wompi en Custom Tab
                                abrirPasarelaPagoReal(
                                    context = context,
                                    plan = planName,
                                    emailUsuario = cardEmail.ifBlank { pseEmail.ifBlank { "usuario@tribumental.com" } }
                                )
                                onDismiss() // Cerramos el diálogo mientras el usuario paga en el navegador
=======
                                errorMessage = null
                                generatedTxId = "wmp_tx_" + UUID.randomUUID().toString().take(12)
                                checkoutStep = "PROCESSING"

                                coroutineScope.launch {
                                    delay(1800)
                                    checkoutStep = "OTP"
                                }
>>>>>>> d1242aa4b6f034d485b2b338743c91cab3206719
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = WompiPurple),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                        ) {
                            Text(
                                "Pagar con Wompi " + finalPriceTextNumberOnly,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        TextButton(
                            onClick = onDismiss,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text("Cancelar pago", color = MutedSlateSub, fontSize = 11.sp)
                        }
                    }

                    if (checkoutStep == "PROCESSING") {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 40.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(color = WompiPurple, strokeWidth = 3.dp)
                            Spacer(modifier = Modifier.height(18.dp))
                            Text(
                                "Procesando pago seguro...",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1E293B),
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                "Conectando con el core de Wompi de Bancolombia...",
                                color = Color(0xFF64748B),
                                fontSize = 11.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    if (checkoutStep == "OTP") {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Lock, contentDescription = null, tint = WompiPurple, modifier = Modifier.size(24.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    "Simulador Wompi SafePass",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = Color(0xFF1E293B)
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                "Hemos enviado un código dinámico OTP al teléfono celular/correo registrado por seguridad. Transacción segura activa.",
                                fontSize = 11.sp,
                                color = Color(0xFF475569),
                                lineHeight = 15.sp
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            OutlinedTextField(
                                value = otpCode,
                                onValueChange = { if (it.length <= 6) otpCode = it },
                                label = { Text("Código de Seguridad Dinámico (6 dígitos)", fontSize = 11.sp) },
                                placeholder = { Text("Ingresa cualquier número", fontSize = 11.sp) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                shape = RoundedCornerShape(10.dp)
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            Button(
                                onClick = {
                                    checkoutStep = "VERIFYING_OTP"
                                    coroutineScope.launch {
                                        delay(1500)
                                        checkoutStep = "SUCCESS"
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = WompiPurple),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(46.dp)
                            ) {
                                Text("Confirmar Pago en Wompi", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                        }
                    }

                    if (checkoutStep == "VERIFYING_OTP") {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 40.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(color = WompiPurple, strokeWidth = 3.dp)
                            Spacer(modifier = Modifier.height(18.dp))
                            Text(
                                "Validando Firma Dinámica...",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1E293B),
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                "Verificando fondos y emitiendo recibo electrónico...",
                                color = Color(0xFF64748B),
                                fontSize = 11.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    if (checkoutStep == "SUCCESS") {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(54.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFDCFCE7)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Check,
                                    contentDescription = null,
                                    tint = Color(0xFF15803D),
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                "¡Transacción APROBADA!",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF166534),
                                fontSize = 18.sp
                            )
                            Text(
                                "Pasarela Activa de Wompi Colombia",
                                color = Color(0xFF166534),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold
                            )

                            Spacer(modifier = Modifier.height(18.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFFF8FAFC), RoundedCornerShape(12.dp))
                                    .padding(14.dp)
                            ) {
                                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("Estado:", fontSize = 11.sp, color = Color(0xFF64748B))
                                        Text("APROBADA", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Color(0xFF166534))
                                    }
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("ID Wompi:", fontSize = 11.sp, color = Color(0xFF64748B))
                                        Text(generatedTxId, fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Color(0xFF1E293B))
                                    }
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("Plan Surtido:", fontSize = 11.sp, color = Color(0xFF64748B))
                                        Text(if (planName == "MENSUAL") "Tribu Premium" else "Familiar Platinum", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Color(0xFF1E293B))
                                    }
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("Monto:", fontSize = 11.sp, color = Color(0xFF64748B))
                                        Text(finalPriceTextNumberOnly, fontWeight = FontWeight.Bold, fontSize = 11.sp, color = WompiPurple)
                                    }
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("Medio:", fontSize = 11.sp, color = Color(0xFF64748B))
                                        Text(
                                            when (paymentMethod) {
                                                "CARD" -> "Tarjeta de Crédito"
                                                "PSE" -> "Ahorros PSE - $pseBank"
                                                else -> "Nequi Directo"
                                            },
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 11.sp,
                                            color = Color(0xFF1E293B)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            Button(
                                onClick = {
                                    onPaymentSuccess(generatedTxId, paymentMethod)
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = WompiPurple),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(46.dp)
                            ) {
                                Text("Comenzar Acompañamiento", color = Color.White, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}
