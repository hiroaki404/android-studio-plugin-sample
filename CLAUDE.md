# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## プロジェクト概要

Android Studio / IntelliJ IDEA プラグインのサンプルプロジェクト。IntelliJ Platform Plugin Template をベースに、Jetpack Compose UI (Jewel) を使用したツールウィンドウを実装しています。

- **対象IDE**: IntelliJ IDEA 2025.2.4（`sinceBuild = "252.25557"`）
- **言語**: Kotlin 2.1.20 + Java 21
- **UIフレームワーク**: Jewel（IntelliJ Platform向け Compose UI）
- **依存プラグイン**: `com.intellij.modules.compose`, `org.jetbrains.kotlin`, `org.jetbrains.android`

## ビルドとタスク

```bash
# プラグインをビルド
./gradlew buildPlugin

# IDE上でプラグインを実行（別のIDE インスタンスが起動）
./gradlew runIde

# テストを実行
./gradlew test

# プラグインの互換性検証
./gradlew verifyPlugin

# クリーンビルド
./gradlew clean build
```

## アーキテクチャ

### ソース構成

```
src/main/kotlin/org/example/androidstudiopluginsample/
├── MyToolWindow.kt       # ToolWindowFactory + Compose UIコンテンツ
└── MyMessageBundle.kt    # i18nメッセージバンドルのヘルパー

src/main/resources/
├── META-INF/
│   ├── plugin.xml        # プラグイン設定ファイル（拡張点・依存関係の定義）
│   └── pluginIcon.svg    # プラグインアイコン
└── messages/
    └── MyMessageBundle.properties  # UIメッセージ文字列
```

### 主要コンポーネント

- **`MyToolWindowFactory`**: `ToolWindowFactory`を実装し、`toolWindow.addComposeTab()`でJewel Compose UIタブを追加する
- **`MyMessageBundle`**: `DynamicBundle`を使った型安全なi18nアクセサ。メッセージキーは`@PropertyKey`アノテーションで管理
- **`plugin.xml`**: 拡張点として`<toolWindow>`を登録。K1/K2両方のKotlinプラグインモードをサポート

### Jewel Compose UI

IntelliJ Platform向けのCompose UIには`org.jetbrains.jewel`を使用。標準のJetpack ComposeではなくJewelのコンポーネント（`Text`, `OutlinedButton`など）を使う必要がある。

## Gradle設定

- **Configuration Cache**: 有効（`org.gradle.configuration-cache=true`）
- **Build Cache**: 有効（`org.gradle.caching=true`）
- **IntelliJ Platform Gradle Plugin**: v2.10.2
