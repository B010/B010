package com.b010.reelsblocker

import android.accessibilityservice.AccessibilityService
import android.os.SystemClock
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Toast

/**
 * Observa Instagram e YouTube. Quando a tela de Reels/Shorts aparece, aperta "voltar".
 * Os IDs de view abaixo podem mudar quando os apps atualizam — basta adicionar novos aqui.
 */
class BlockerService : AccessibilityService() {

    private val instagramIds = listOf(
        "com.instagram.android:id/clips_viewer_view_pager",
        "com.instagram.android:id/clips_viewer_container",
        "com.instagram.android:id/clips_video_container",
        "com.instagram.android:id/clips_media_component",
    )
    private val youtubeIds = listOf(
        "com.google.android.youtube:id/reel_recycler",
        "com.google.android.youtube:id/reel_watch_player",
        "com.google.android.youtube:id/reel_player_page_container",
        "com.google.android.youtube:id/reel_time_bar",
    )

    private var lastBlock = 0L

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        val pkg = event.packageName?.toString() ?: return
        val ids = when {
            pkg == "com.instagram.android" && Prefs.get(this, Prefs.INSTAGRAM) -> instagramIds
            pkg == "com.google.android.youtube" && Prefs.get(this, Prefs.YOUTUBE) -> youtubeIds
            else -> return
        }
        val now = SystemClock.uptimeMillis()
        if (now - lastBlock < 800) return
        val root = rootInActiveWindow ?: return
        if (ids.any { hasVisible(root, it) }) {
            lastBlock = now
            performGlobalAction(GLOBAL_ACTION_BACK)
            Prefs.inc(this)
            Toast.makeText(this, "Reels/Shorts bloqueado 🚫", Toast.LENGTH_SHORT).show()
        }
    }

    private fun hasVisible(root: AccessibilityNodeInfo, id: String): Boolean =
        root.findAccessibilityNodeInfosByViewId(id).any { it.isVisibleToUser }

    override fun onInterrupt() {}
}
