# Créer un mod Fabric

## Initier un mod 

* [Commencer le développement Fabric](https://docs.fabricmc.net/develop/)

* [Fabric Template generator](https://fabricmc.net/develop/template/)
* * Mod Name: ce que vous voulez
* * Package Name : fr.cattaga.
* * Minecraft Version : 1.20.1
* * Cocher au moins : Split client and common sources

## Commencer le dev d'un mod

### Votre IDE

Pour IntelliJ : 
* mettre un Java 1.17
* mettre l'extension : [Minecraft Development
](https://plugins.jetbrains.com/plugin/8327-minecraft-development)

* [Faite le build du projet](https://docs.fabricmc.net/1.20.4/develop/getting-started/launching-the-game) `./gradlew intellij`
* Pensez a générer les sources minecraft avec `./gradlew genSources`

* Lancer votre test client avec : `./gradlew runClient`



## Quelques tuto 

* [Rajouter un nouveau mob](https://www.youtube.com/watch?v=wgVnkqqBGFs)
* [Rajouter un block](https://docs.fabricmc.net/1.20.4/develop/blocks/first-block)


## Bonnes pratiques

* Créer une constante avec le mod ID ( souvent le nom du mod / nom du mod finissant par mod)


