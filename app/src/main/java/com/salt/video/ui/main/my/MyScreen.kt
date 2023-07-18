package com.salt.video.ui.main.my

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.moriafly.salt.ui.Item
import com.moriafly.salt.ui.ItemSpacer
import com.moriafly.salt.ui.ItemSwitcher
import com.moriafly.salt.ui.ItemText
import com.moriafly.salt.ui.ItemTitle
import com.moriafly.salt.ui.RoundedColumn
import com.moriafly.salt.ui.SaltTheme
import com.moriafly.salt.ui.SaltUILogo
import com.moriafly.salt.ui.TitleBar
import com.moriafly.salt.ui.UnstableSaltApi
import com.salt.video.App
import com.salt.video.BuildConfig
import com.salt.video.R
import com.salt.video.util.Config

@OptIn(UnstableSaltApi::class)
@Composable
fun MyScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        TitleBar(
            onBack = { },
            text = stringResource(id = R.string.my),
            showBackBtn = false
        )
        MyScreenContent()
    }
}

@OptIn(UnstableSaltApi::class)
@Composable
fun ColumnScope.MyScreenContent() {
    Column(
        modifier = Modifier
            .weight(1f)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        RoundedColumn {
            ItemTitle(text = "椒盐视频开发体验版本")
            ItemSpacer()
            ItemText(text = "此为开发体验版本，所有功能都可能在后续版本变更或移除，加入 QQ 群聊 639298754")
            ItemSpacer()
        }

        RoundedColumn {
            ItemTitle(text = "资源")

            var fileListShowHiddenFolders by remember {
                mutableStateOf(App.mmkv.decodeBool(Config.FILE_LIST_SHOW_HIDDEN_FOLDERS, Config.FILE_LIST_SHOW_HIDDEN_FOLDERS_DEFAULT))
            }
            ItemSwitcher(
                state = fileListShowHiddenFolders,
                onChange = {
                    fileListShowHiddenFolders = it
                    App.mmkv.encode(Config.FILE_LIST_SHOW_HIDDEN_FOLDERS, it)
                },
                text = stringResource(id = R.string.file_list_show_hidden_folders),
                sub = "显示以 . 开头的隐藏文件夹",
                iconPainter = painterResource(id = R.drawable.ic_hide_video),
                iconColor = Color(0xFF75878A)
            )

//            ItemSwitcher(
//                state = false,
//                onChange = {
//
//                },
//                text = "文件列表显示隐藏文件",
//                sub = "显示以 . 开头的隐藏文件",
//                iconPainter = painterResource(id = R.drawable.ic_hide_file),
//                iconColor = Color(0xFF75878A)
//            )
        }

        RoundedColumn {
            ItemTitle(text = stringResource(id = R.string.video))

            var mediaCodec by remember { mutableStateOf(App.mmkv.decodeBool(Config.MEDIA_CODEC, false)) }
            ItemSwitcher(
                state = mediaCodec,
                onChange = {
                    mediaCodec = it
                    App.mmkv.encode(Config.MEDIA_CODEC, it)
                },
                text = "硬件解码",
                iconPainter = painterResource(id = R.drawable.ic_cpu),
                iconColor = Color(0xFFC9DD22)
            )

//            Item(
//                onClick = {
//
//                },
//                text = "渲染控件",
//                sub = "SurfaceView",
//                iconPainter = painterResource(id = R.drawable.ic_loading),
//                iconColor = Color(0xFF1BD1A5)
//            )
        }

        RoundedColumn {
            ItemTitle(text = "法律信息")
//            Item(
//                onClick = {
//
//                },
//                text = "软件使用条例",
//                iconPainter = painterResource(id = R.drawable.ic_license),
//                iconColor = SaltTheme.colors.highlight
//            )
//
//            Item(
//                onClick = {
//
//                },
//                text = "隐私协议",
//                iconPainter = painterResource(id = R.drawable.ic_license),
//                iconColor = SaltTheme.colors.highlight
//            )

            var openSourceDialog by remember { mutableStateOf(false) }
            Item(
                onClick = {
                    openSourceDialog = true
                },
                text = "开放源代码许可",
                iconPainter = painterResource(id = R.drawable.ic_copyright),
                iconColor = SaltTheme.colors.highlight
            )
            if (openSourceDialog) {
                OpenSourceDialog(
                    onDismissRequest = {
                        openSourceDialog = false
                    }
                )
            }
        }

        SaltUILogo()

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ItemSpacer()
            Icon(
                painter = painterResource(id = R.drawable.ic_salt_video),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(color = SaltTheme.colors.highlight)
                    .padding(10.dp),
                tint = Color.White
            )
            ItemSpacer()
            Text(
                text = "${BuildConfig.VERSION_NAME}(${BuildConfig.VERSION_CODE})",
                style = SaltTheme.textStyles.sub
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Copyright © 2022-2023 Moriafly. All Rights Reserved.",
                style = SaltTheme.textStyles.sub
            )
            ItemSpacer()
        }
    }
}