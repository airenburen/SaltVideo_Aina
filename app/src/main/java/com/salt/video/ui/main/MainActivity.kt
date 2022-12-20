package com.salt.video.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.documentfile.provider.DocumentFile
import com.dylanc.activityresult.launcher.OpenDocumentLauncher
import com.dylanc.activityresult.launcher.OpenDocumentTreeLauncher
import com.kongzue.dialogx.dialogs.BottomMenu
import com.kongzue.dialogx.dialogs.InputDialog
import com.kongzue.dialogx.dialogs.PopMenu
import com.salt.video.R
import com.salt.video.databinding.ActivityMainBinding
import com.salt.video.databinding.ActivityPlayerBinding
import com.salt.video.ui.player.PlayerActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    /** SAF 选择 */
    private val openDocumentTreeLauncher = OpenDocumentTreeLauncher(this)
    private val openDocumentLauncher = OpenDocumentLauncher(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.root.setOnApplyWindowInsetsListener { v, insets ->
            binding.clTitleBar.updateLayoutParams<ConstraintLayout.LayoutParams> {
                topMargin = insets.systemWindowInsetTop
            }
            return@setOnApplyWindowInsetsListener insets
        }

        binding.ivAdd.setOnClickListener {
            PopMenu.show(
                listOf(
                    "本地视频",
                    "本地音乐",
                    "网络音视频"
                )
            )
                .setOnMenuItemClickListener { dialog, text, index ->
                    when (index) {
                        0 -> openDocumentLauncher("video/*")
                        1 -> openDocumentLauncher("audio/*")
                        2 -> openDialog()
                    }
                    return@setOnMenuItemClickListener false
                }

        }
    }

    private fun openDocumentLauncher(vararg input: String) {
        openDocumentLauncher.launch(*input) { uri ->
            if (uri != null) {
                val documentFile = DocumentFile.fromSingleUri(this, uri)
                PlayerActivity.start(this, uri.toString(), documentFile?.name ?: "")
            }
        }
    }

    private fun openDialog() {
        InputDialog(
            "网络视频",
            "输入网络地址",
            "确定",
            "取消",
            ""
        )
            .setOkButton { dialog, v, inputStr ->
                val url = inputStr
                PlayerActivity.start(this, url, url)
                return@setOkButton false
            }
            .show()
    }
}