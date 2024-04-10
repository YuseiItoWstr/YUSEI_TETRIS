# YUSEI_TETRIS

YUSEI_TETRISはJavaで実装されたシンプルなテトリスだってばよ。

https://github.com/YuseiItoWstr/YUSEI_TETRIS/assets/131947858/23d48411-a40b-49ec-bf44-441435e6584e

## 概要

ブロックを横に並べてラインを消すことが目的だってばよ。ブロックが画面の上部まで積み上がるとゲームオーバーになるじゃん。

## 実装済み機能

- ブロックの操作: 左右移動、回転、落下、テトリミノのランダム入れ替え(毎ターン一回限定)
- ホールド機能ないじゃんって言わないで、これから頑張るからｺﾚｪ
- ラインの消去(1~4ラインまで)
- スコアの計算(オマケみたいなものよ！しゃーんなろー！)

## 必要なもの

- Java Development Kit (JDK)

## インストール

1.  このリポジトリをクローンするってばよ。
2.  コマンドラインで`src` ディレクトリまで行くじゃん。
3.  `javac -d classes Main.java Rules.java GameBoard.java Tetrimino.java AudioPlayer.java` でコンパイルするってばさ。
4.  `java -classpath ../classes Main` で起動できる！青春だ！
5.  (本当は.jarファイルを実行するだけでいいはずなんだが、音楽が流れん...)

## ゲームの操作方法

- 左矢印キー: ブロックを左に移動
- 右矢印キー: ブロックを右に移動
- 上矢印キー: ブロックのハードドロップ(一瞬で下に落ちるﾜﾈ)
- 下矢印キー: ブロックを落下
- スペースキー: テトリミノチェンジ
- Cキー: ブロックのホールド
- Pキー: ゲームの一時停止/再開
- Rキー: ゲームのリスタート

## ライセンス

そんなものはないってばね。