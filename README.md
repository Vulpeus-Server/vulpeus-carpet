# Vulpeus carpet addition

carpet addition made for vulpeus.
Supported version is only main version of our server.

## rule

Show rules only in the latest release or current development version.

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

### optmizedDragonRespawn

Optimize dragon respawn method. Ported from carpet AMS addition.
> [!WARNING]
> Couldn’t ensure same behavior as vanilla Minecraft after enabling this rule.

- Type: `boolean`
- Default value: `false`
- Categories: `OPTIMIZATION`, `VULPEUS`

## command

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