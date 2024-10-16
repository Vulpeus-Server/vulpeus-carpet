<a href="https://vulpeus.com/" target="_blank">
    <img src="https://vulpeus.com/vulpeus_logo.svg/" alt="https://vulepus.com/" width="35">ㅤ
    <b>
        <font size="7" color="white">
            Vulpeus Carpet
        </font>
    </b>
</a>

- - -

<p align="left">
    <a href="http://www.gnu.org/licenses/lgpl-3.0.html">
        <img src="https://img.shields.io/github/license/Vulpeus-Server/vulpeus-carpet.svg" alt="License">
    </a>
    <a href="https://github.com/Vulpeus-Server/vulpeus-carpet/issues">
        <img src="https://img.shields.io/github/issues/Vulpeus-Server/vulpeus-carpet.svg" alt="Issues">
    </a>
    <a href="https://github.com/Vulpeus-Server/vulpeus-carpet/actions/workflows/gradle.yml">
        <img src="https://github.com/Vulpeus-Server/vulpeus-carpet/actions/workflows/gradle.yml/badge.svg" alt="workflow">
    </a>
    <a href="https://modrinth.com/mod/vulpeus-carpet">
        <img src="https://img.shields.io/modrinth/dt/aZhtZo3k?label=Modrinth%20Downloads&logo=Modrinth" alt="Modrinth">
    </a>
    <a href="https://discord.gg/tjayanzYMf">
        <img src="https://img.shields.io/discord/1157213775791935539?logo=Discord" alt="Discord">
    </a>
</p>

## Our contents

<iframe width="560" height="315" src="https://www.youtube-nocookie.com/embed/42bYQgV5A3o" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" allowfullscreen>
</iframe>

## Features

Check [HERE](https://github.com/Vulpeus-Server/vulpeus-carpet/blob/master/docs/en_us.md) to see the document of all available options.

[こちら](https://github.com/Vulpeus-Server/vulpeus-carpet/blob/master/docs/ja_jp.md) にすべての機能、設定が記述されているのでご確認ください。

## All rules

<details>
<summary>English</summary>

## Rules

### commandCustomLoad

Enable `/custom-load` command. Inspired by AMS blockChunkLoader.

- Type: `String`
- Default value: `ops`
- Allowed options: `true`, `false`, `ops`, `0`, `1`, `2`, `3`, `4`
- Categories: `SURVIVAL`, `COMMAND`, `VULPEUS`

### commandHat

Enable `/hat` command. Ported from essential addons.

- Type: `String`
- Default value: `ops`
- Allowed options: `true`, `false`, `ops`, `0`, `1`, `2`, `3`, `4`
- Categories: `SURVIVAL`, `COMMAND`, `VULPEUS`

### commandSit

Enable `/sit` command. Ported from PCA.

- Type: `String`
- Default value: `ops`
- Allowed options: `true`, `false`, `ops`, `0`, `1`, `2`, `3`, `4`
- Categories: `SURVIVAL`, `COMMAND`, `VULPEUS`

### commandView

Enable `/view` command to change viewDistance of carpet.

- Type: `String`
- Default value: `ops`
- Allowed options: `true`, `false`, `ops`, `0`, `1`, `2`, `3`, `4`
- Categories: `SURVIVAL`, `COMMAND`, `VULPEUS`

### defaultOpLevel

Set the OP level granted when you join the server. can be re-granted by rejoining and can
override `op-permission-level` in the server.properties.

- Type: `int`
- Default value: `0`
- Categories: `CREATIVE`,`VULPEUS`

### disableCCECrash

yeet the server crash caused by ClassCastException.

> <font size="3" color="#ff4500">WARNING</font><br>
> Faster than others, but a more dangerous.

- Type: `boolean`
- Default value: `false`
- Categories: `VULPEUS`

### disableIAECrash

yeet the server crash caused by IllegalArgumentException.

> <font size="3" color="#ff4500">WARNING</font><br>
> Faster than others, but a more dangerous.

- Type: `boolean`
- Default value: `false`
- Categories: `VULPEUS`

### disableSOECrash

yeet the server crash caused by StackOverflowError.

> <font size="3" color="#ff4500">WARNING</font><br>
> Faster than others, but a more dangerous.

- Type: `boolean`
- Default value: `false`
- Categories: `VULPEUS`

### fixedBeeNotLeavingHive (<=1.21.1)

fixed [MC-168329](https://bugs.mojang.com/browse/MC-168329).

> <font size="3" color="#1f6feb">NOTE</font><br>
> This will not work when used with carpet-fixes.<br>
> Please use carpet-fixes `beeNotLeavingHiveFix`.

- Type: `boolean`
- Default value: `false`
- Categories: `BUGFIX`, `VULPEUS`

### fixedFallingBlockCantUseNetherPortal (<=1.20.6)

fixed [MC-9644](https://bugs.mojang.com/browse/MC-9644).

- Type: `boolean`
- Default value: `false`
- Categories: `BUGFIX`, `VULPEUS`

### fixedTickMemoriesEntityAI (<=1.20.1)

fixed [MC-254100](https://bugs.mojang.com/browse/MC-254100).

- Type: `boolean`
- Default value: `false`
- Categories: `BUGFIX`, `VULPEUS`

### optmizedDragonRespawn

Optimize dragon respawn method. Ported from carpet AMS addition.
> <font size="3" color="#ff4500">WARNING</font><br>
> Couldn’t ensure same behavior as vanilla Minecraft after enabling this rule.

- Type: `boolean`
- Default value: `false`
- Categories: `OPTIMIZATION`, `VULPEUS`

### visibleSpectators

Makes spectators visible to non-spectator players. Ported from totos carpet tweaks.

![visibleSpectators](https://cdn.modrinth.com/data/cached_images/49e833cebc68a62101d5077aaa6933a89eb815ad_0.webp)

- Type: `boolean`
- Default value: `false`
- Categories: `SURVIVAL`, `VULPEUS`

## Command

### custom-load

`/custom-load` : Show current custom loading chunk list.

`/custom-load add [<dimension>] [<cx>] [<cz>] [<radius>]` : add to list.

`/custom-load remove [<dimension>] [<index>]` : remove from list.

`[<dimension>]` : dimension id

`[<cx>]` : x of the chunk at the center of the loading range.

`[<cz>]` : z of the chunk at the center of the loading range.

`[<radius>]` : radius

`[<index>]` : index of the list. you can check it with `/custom-load`.

### hat

`/hat` : Equip the item you have on your own head. Totem of undying or non-empty shulker box cannot
be equipped.Also you cannot use this command if you already equip the item enchanted curse of
binding.

### hat

`/hat` : Equip the item you have on your own head. Totem of undying or non-empty shulker box cannot
be equipped.Also you cannot use this command if you already equip the item enchanted curse of
binding.

### sit

`/sit` : sit on the spot.

### view

`/view` :  display current viewDistance

`/view [<distance>]` : change the value of viewDistance in carpet

`[<distance>]` : must be between `0` to `32`

### playerActions

> <font size="3" color="#1f6feb">NOTE</font><br>
> Only use fake players

`/player [<name>] fill [<state>]`

`/player [<name>] clean [<state>]`

`[<name>]` : fake player name

`[<state>]` : `true` or `false`

## Logger

### autosave

`/log autosave`

Simple logger that display when the server will autosave and how long ago the last autosave was.
This logger does not directly detect autosave, but rather a calculated value based on the time elapsed since the server started, which could be incorrect.
Ported from essential addons.

### entity_count

![logEntityCount](https://cdn.modrinth.com/data/cached_images/27633e09c9bf35e99a0df3b5aaab025671f0bfab.png)

`/log entity_count`

`/log entity_count [<entities>]`

Display counts of entities per dimension and the total number in the player list.

`[<entities>]` : entity id. you can input multiple values separated by commas.

※ if `:` is included, argument must be enclosed in double quotes.
```
/log entity_count player
/log entity_count "minecraft:player,minecraft:iron_golem"
```

</details>

<details>
<summary>日本語</summary>

## ルール

### commandCustomLoad

`/custom-load`コマンドの有効化。AMS の blockChunkLoader から発想を得ました。

- 型式: `String`
- 既定値: `ops`
- 使用可能な値: `true`, `false`, `ops`, `0`, `1`, `2`, `3`, `4`
- 分類: `SURVIVAL`, `COMMAND`, `VULPEUS`

### commandHat

`/hat`コマンドの有効化。essential addons からの移植。

- 型式: `String`
- 既定値: `ops`
- 使用可能な値: `true`, `false`, `ops`, `0`, `1`, `2`, `3`, `4`
- 分類: `SURVIVAL`, `COMMAND`, `VULPEUS`

### commandSit

`/sit`コマンドの有効化。PCA からの移植。

- 型式: `String`
- 既定値: `ops`
- 使用可能な値: `true`, `false`, `ops`, `0`, `1`, `2`, `3`, `4`
- 分類: `SURVIVAL`, `COMMAND`, `VULPEUS`

### commandView

`/view`コマンドの有効化。
carpet の viewDistance を変更することができる。

- 型式: `String`
- 既定値: `ops`
- 使用可能な値: `true`, `false`, `ops`, `0`, `1`, `2`, `3`, `4`
- 分類: `SURVIVAL`, `COMMAND`, `VULPEUS`

### defaultOpLevel

サーバーに参加したときにデフォルトでOPを付与する。
server.properties の`op-permission-level`を上書き可能。

- 型式: `int`
- 既定値: `0`
- 分類: `CREATIVE`,`VULPEUS`

### disableCCECrash

ClassCastException によるサーバーのクラッシュの無効化。

> <font size="3" color="#ff4500">WARNING</font><br>
> 他と比べてより速いが、より危険。

- 型式: `boolean`
- 既定値: `false`
- 分類: `VULPEUS`

### disableIAECrash

IllegalArgumentException によるサーバーのクラッシュの無効化。

> <font size="3" color="#ff4500">WARNING</font><br>
> 他と比べてより速いが、より危険。

- 型式: `boolean`
- 既定値: `false`
- 分類: `VULPEUS`

### disableSOECrash

> <font size="3" color="#ff4500">WARNING</font><br>
> 他と比べてより速いが、より危険。

StackOverflowError によるサーバーのクラッシュの無効化。

- 型式: `boolean`
- 既定値: `false`
- 分類: `VULPEUS`

### fixedBeeNotLeavingHive (<=1.21.1)

[MC-168329](https://bugs.mojang.com/browse/MC-168329) の修正。

> <font size="3" color="#1f6feb">NOTE</font><br>
> carpet-fixesと併用した場合、機能しなくなります。<br>
> carpet-fixesの`beeNotLeavingHiveFix`を使用してください。

- 型式: `boolean`
- 既定値: `false`
- 分類: `BUGFIX`, `VULPEUS`

### fixedFallingBlockCantUseNetherPortal (<=1.20.6)

[MC-9644](https://bugs.mojang.com/browse/MC-9644) の修正。

- 型式: `boolean`
- 既定値: `false`
- 分類: `BUGFIX`, `VULPEUS`

### fixedTickMemoriesEntityAI (<=1.20.1)

[MC-254100](https://bugs.mojang.com/browse/MC-254100) の修正

- 型式: `boolean`
- 既定値: `false`
- 分類: `BUGFIX`, `VULPEUS`

### optmizedDragonRespawn

エンダードラゴンのリスポーン処理の最適化。carpet AMS addition からの移植。

> <font size="3" color="#ff4500">WARNING</font><br>
> このルールはバニラと同一の挙動を保証しません。

- 型式: `boolean`
- 既定値: `false`
- 分類: `OPTIMIZATION`, `VULPEUS`

### visibleSpectators

スペクテーターのプレイヤーをスペクテーターではないプレイヤーから見えるようにする。totots carpet tweaks からの移植。

![visibleSpectators](https://cdn.modrinth.com/data/cached_images/49e833cebc68a62101d5077aaa6933a89eb815ad_0.webp)

- 型式: `boolean`
- 既定値: `false`
- 分類: `SURVIVAL`, `VULPEUS`

## コマンド

### custom-load

`/custom-load` : 現在の CustomLoadingChunk のリストを表示。

`/custom-load add [<dimension>] [<cx>] [<cz>] [<radius>]` : リストに追加

`/custom-load remove [<dimension>] [<index>]` : リストから削除

`[<dimension>]` : ディメンションID

`[<cx>]` : ロード範囲の中心チャンクの ChunkX

`[<cz>]` : ロード範囲の中心チャンクの ChunkZ

`[<radius>]` : 半径

`[<index>]` : 要素のインデックス。`/custom-load`で確認できます。

### hat

`/hat` : 頭に所持しているアイテムを装備します。不死のトーテムや空でないシュルカーボックスは装備できません。また、束縛の呪いが付いた装備すでにしている場合も同様です。

### sit

`/sit` : 座ります。

### view

`/view` : 現在の描画距離設定を表示します。

`/view [<distance>]` : carpet での viewDistance を変更します。

`[<distance>]` : 0 から 32 でなければなりません

### playerActions

> <font size="3" color="#1f6feb">NOTE</font><br>
> fakePlayer でのみ利用可能です。

`/player [<name>] fill [<state>]` : コンテナを開いたときにインベントリの中身をすべて入れる。

`/player [<name>] clean [<state>]` : コンテナを開いたときにコンテナの中身をすべて捨てる。

`[<name>]` : fake player の名前

`[<state>]` : `true` か `false`

## ロガー

### autosave

`/log autosave`

オートセーブされてからの時間と、次のオートセーブまでの時間をプレイヤーリストに表示。
このロガーはサーバー開始からのチックから算出されるため、正確ではない場合があることに留意してください。
essential addons からの移植。

### entity_count

![logEntityCount](https://cdn.modrinth.com/data/cached_images/27633e09c9bf35e99a0df3b5aaab025671f0bfab.png)

`/log entity_count`

`/log entity_count [<entities>]`

ディメンション毎と合計のエンティティの数をプレイヤーリストに表示。

`[<entities>]` : エンティティID。カンマ区切りで複数入力することも可能。`minecraft:`の接頭語は省略可。

※ `:`を含める場合は引数全体をダブルクォーテーションで囲う必要があります。
```
/log entity_count player
/log entity_count "minecraft:player,minecraft:iron_golem"
```

</details>
