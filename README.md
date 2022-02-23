# MiscSk

## Syntax

### Condiciones

Saber si un HUD de ItemsAdder existe o no

```vbscript
hud %string% [of|from] %player% [does] exist[s]
hud %string% [of|from] %player% does(n't| not) exist

'Ejemplo:
if hud "magiccraft:mana_bar" of player exists:
    message "Tienes mana, amigo."
```

Saber si un usuario esta en combate  o no

```vbscript
%player% is in combat
%player% is(n't| not) in combat

'Ejemplo:
if player is in combat:
    message "No puedes hacer eso en combate!"
```

### Efectos

Mostrar u ocultar el HUD de un jugador

```vbscript
show [ItemsAdder] hud %string% [to|for] %players%
hide [ItemsAdder] hud %string% [to|for] %players%
        
'Ejemplo 1:
show hud "magiccraft:mana_bar" to player
message "Has activado el HUD de mana"
        
'Ejemplo 2:
hide hud "magiccraft:mana_bar" for player
message "Has desactivado el HUD de mana"
```

Actualizar los HUDs de un jugador

```vbscript
update [ItemsAdder] huds [of|from] %players%

'Ejemplo:
update huds of player
message "Has actualizado tus HUDs"
```

Poner un bloque de ItemsAdder en una locacion

```vbscript
place i[tems]a[dder] block %string% at %location%

'Ejemplo:
place ia block "ruby_ore" at location(0, 25, 0)
```

### Expresiones

Obtener/Agregar/Remover/Modificar el valor de un HUD de un jugador.

```vbscript
[the] [ItemsAdder] %player%'s hud %string% quantity

'Ejemplos:
set {_mana} to player's hud "magiccraft:mana_bar" quantity
add 1 to player's hud "magiccraft:mana_bar" quantity
remove 2 from player's hud "magiccraft:mana_bar" quantity
reset player's hud "magiccraft:mana_bar" quantity
set player's hud "magiccraft:mana_bar" quantity to 5
```

Obtener/Agregar/Remover/Modificar la experiencia (MMOCore) de un jugador.

```vbscript
[the] MMO[Core] exp[erience] of %player% [with hologram at %-location%]
[the] %player%'s MMO[Core] exp[erience] [with hologram at %-location%]

'Ejemplos:
set {_xp} to player's mmo exp
add 1 to player's mmo exp with hologram at location at player
add 5 to player's mmo exp
remove 3 from player's mmo exp
reset player's mmo exp
set player's mmo exp to 10
```

Obtener/Agregar/Remover/Modificar el nivel (MMOCore) de un jugador.

```vbscript
[the] MMO[Core] (lvl|level) of %player%
[the] %player%'s MMO[Core] (lvl|level)

'Ejemplos:
set {_nivel} to mmo lvl of player
add 1 to player's mmo lvl
remove 2 from player's mmo lvl
reset player's mmo level
set mmo level of player to 25
```

Obtener/Agregar/Remover/Modificar el Stellium (MMOCore) de un jugador.

```vbscript
[the] [MMO[Core]] stellium of [the] %player%
%player%'s [MMO[Core]] stellium

'Ejemplos:
set {_stellium} to player's stellium
add 10 to mmocore stellium of player
remove 15 from player's mmo stellium
reset player's stellium
set player's stellium to 50
```

Obtener/Agregar/Remover/Modificar la Stamina (MMOCore) de un jugador.

```vbscript
[the] [MMO[Core]] stamina of [the] %player%
%player%'s [MMO[Core]] stamina

'Ejemplos:
set {_stamina} to player's stamina
add 25 to player's mmo stamina
remove 10 from stamina of the player
reset player's mmocore stamina
set player's mmo stamina to 250
```

Obtener/Agregar/Remover/Modificar el Mana (MMOCore) de un jugador.

```vbscript
[the] MMO[Core] mana of [the] %player%
%player%'s MMO[Core] mana

'Ejemplos:
set {_mana} to mmo mana of player
add 10 to player's mmo mana
remove 5 from player's mmo mana
reset player's mmo mana
set mmo mana of the player to 10
```

Obtener la experiencia necesaria para subir de nivel de un jugador.

```vbscript
MMO[Core] exp[erience] [needed] to level up [of|from] %player%

'Ejemplo
set {_expRequerida} to mmo exp needed to level up from player
```

### Eventos

Detectar cuando un jugador pone un Bloque de ItemsAdder

```vbscript
I[tems]A[dder] block place [of %-strings%]

'Ejemplos:
on ia block place:
	message "Has colocado el bloque: %event-string%" to player
	
on ia block place of "itemsadder:ruby_block":
	message "Has puesto un bloque de Amatista. Que genial!" to player
```

Valores de Evento

|     Valor      | Descripcion                                                  |
| :------------: | ------------------------------------------------------------ |
|  event-player  | Obtienes al jugador envuelto en el evento                    |
|  event-string  | Obtienes el nombre del bloque de ItemsAdder envuelto en el evento |
|  event-block   | Obtienes el bloque (vanilla) envuelto en el evento           |
| event-location | Obtienes la locacion envuelta en el evento                   |
|   event-item   | Obtienes el item que dropea el bloque                        |



Detectar cuando un jugador interactua con un Bloque de ItemsAdder

```vbscript
I[tems]A[dder] block interact [of %-strings%]

'Ejemplos:
on ia block interact:
	message "Has tocado un bloque de ItemsAdder" to player
	
on ia block interact of "itemsadder:ruby_block":
	message "Has tocado un bloque de amatista... Ahora sientes el poder" to player
```

Valores de Evento

|     Valor      | Descripcion                                                  |
| :------------: | ------------------------------------------------------------ |
|  event-player  | Obtienes al jugador envuelto en el evento                    |
|  event-string  | Obtienes el nombre del bloque de ItemsAdder envuelto en el evento |
|  event-block   | Obtienes el bloque (vanilla) envuelto en el evento           |
| event-location | Obtienes la locacion envuelta en el evento                   |
|   event-item   | Obtienes el item que dropea el bloque                        |



Detectar cuando un jugador rompe un Bloque de ItemsAdder

```vbscript
I[tems]A[dder] block break [of %-strings%]

'Ejemplos:
on ia block break:
	message "Has roto un bloque de ItemsAdder" to player
	
on ia block interact of "itemsadder:ruby_block":
	message "Has minado 1 ruby! Has ganado 2 de experiencia" to player
	add 2 to player's mmo exp with hologram at event-location
```

Valores de Evento

|     Valor      | Descripcion                                                  |
| :------------: | ------------------------------------------------------------ |
|  event-player  | Obtienes al jugador envuelto en el evento                    |
|  event-string  | Obtienes el nombre del bloque de ItemsAdder envuelto en el evento |
|  event-block   | Obtienes el bloque (vanilla) envuelto en el evento           |
| event-location | Obtienes la locacion envuelta en el evento                   |
|   event-item   | Obtienes el item que dropea el bloque                        |

