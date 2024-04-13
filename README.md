# YUSEI_TETRIS

YUSEI_TETRISはJavaで実装されたシンプルなオリジナルTETRISです。

https://github.com/YuseiItoWstr/YUSEI_TETRIS/assets/131947858/4865e325-5f12-4202-88fb-5afbb60d71e0

## 概要

ブロックを積み上げて横方向のラインを埋めて、消すことが目的です。ブロックが画面の上部まで積み上がるとゲームオーバーになります。

## 実装済み機能

- ブロックの操作: 左右移動、回転、落下、テトリミノのランダム入れ替え(毎ターン一回限定)
- (現在、ホールド機能はテトリミノのランダム入れ替えになっています)
- ラインの消去(1~4ラインまで)
- スコアの計算(オマケ)

## 必要なもの

- Java Development Kit (JDK)

## インストール

1.  このリポジトリをクローン
2.  コマンドラインで`src` ディレクトリまで行く
3.  `javac -d ../classes Main.java Rules.java GameBoard.java Tetrimino.java AudioPlayer.java` でコンパイル
4.  `java -classpath ../classes Main` で起動
5.  (本当は.jarファイルを実行するだけでいいはずですが、音楽が流れません...修正中)

## ゲームの操作方法

- 左矢印キー: ブロックを左に移動
- 右矢印キー: ブロックを右に移動
- 上矢印キー: ブロックのハードドロップ(一瞬で下に落ちます)
- 下矢印キー: ブロックを落下
- スペースキー: テトリミノチェンジ(NEXTのテトリミノがやってきます)
- Xキー: 右回転
- Zキー: 左回転
- Pキー: ゲームの一時停止/再開
- Rキー: ゲームのリスタート

## ライセンス

このプロジェクトは完全フリーであり、誰でも自由に使用、変更、配布することができます。ライセンスの制限はありません。
