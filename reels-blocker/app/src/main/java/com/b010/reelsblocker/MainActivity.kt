package com.b010.reelsblocker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView

class MainActivity : Activity() {
    private lateinit var status: TextView
    private lateinit var counter: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pad = (24 * resources.displayMetrics.density).toInt()
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(pad, pad * 2, pad, pad)
        }
        root.addView(TextView(this).apply { text = "Reels Blocker"; textSize = 28f })
        status = TextView(this).apply { textSize = 16f; setPadding(0, pad, 0, pad) }
        root.addView(status)
        root.addView(Button(this).apply {
            text = "Abrir configurações de Acessibilidade"
            setOnClickListener { startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)) }
        })
        root.addView(toggle("Bloquear Reels do Instagram", Prefs.INSTAGRAM))
        root.addView(toggle("Bloquear Shorts do YouTube", Prefs.YOUTUBE))
        counter = TextView(this).apply { textSize = 16f; gravity = Gravity.CENTER; setPadding(0, pad, 0, 0) }
        root.addView(counter)
        setContentView(root)
    }

    private fun toggle(label: String, key: String) = Switch(this).apply {
        text = label
        textSize = 18f
        setPadding(0, 24, 0, 24)
        isChecked = Prefs.get(this@MainActivity, key)
        setOnCheckedChangeListener { _, v -> Prefs.set(this@MainActivity, key, v) }
    }

    override fun onResume() {
        super.onResume()
        val enabled = Settings.Secure.getString(contentResolver, Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES)
            ?.contains("$packageName/") == true
        status.text = if (enabled) "✅ Bloqueador ATIVO"
        else "⚠️ Bloqueador inativo.\nToque no botão abaixo → Apps instalados → Reels Blocker → Ativar."
        counter.text = "Bloqueios até agora: ${Prefs.count(this)}"
    }
}
