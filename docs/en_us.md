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

- Type: `boolean`
- Default value: `false`
- Categories: `VULPEUS`

### disableSOECrash

yeet the server crash caused by StackOverflowError.

- Type: `boolean`
- Default value: `false`
- Categories: `VULPEUS`

### fixedBeeNotLeavingHive (<=1.21.1)

fixed MC-168329.

- Type: `boolean`
- Default value: `false`
- Categories: `BUGFIX`, `VULPEUS`

### fixedFallingBlockCantUseNetherPortal (<=1.20.6)

fixed MC-9644.

- Type: `boolean`
- Default value: `false`
- Categories: `BUGFIX`, `VULPEUS`

### fixedTickMemoriesEntityAI (<=1.20.1)

fixed MC-254100.

- Type: `boolean`
- Default value: `false`
- Categories: `BUGFIX`, `VULPEUS`

### optmizedDragonRespawn

Optimize dragon respawn method. Ported from carpet AMS addition.
> [!WARNING]
> Couldn’t ensure same behavior as vanilla Minecraft after enabling this rule.

- Type: `boolean`
- Default value: `false`
- Categories: `OPTIMIZATION`, `VULPEUS`

## Command

### custom-load

`/custom-load` : Show current custom loading chunk list.

`/custom-load add [<dimension>] [<cx>] [<cz>] [<size>]` : add to list.

`/custom-load remove [<dimension>] [<index>]` : remove from list.

`[<dimension>]` : dimension id

`[<cx>]` : x of the chunk at the center of the loading range.

`[<cz>]` : z of the chunk at the center of the loading range.

`[<size>]` : radius

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

`/player [<name>] fill [<state>]`

`/player [<name>] clean [<state>]`

`[<name>]` : fake player name

`[<state>]` : `true` or `false`