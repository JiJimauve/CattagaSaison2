# Créer un mod Fabric

## Initier un mod 

* [Commencer le développement Fabric](https://docs.fabricmc.net/develop/)

* [Fabric Template generator](https://fabricmc.net/develop/template/)
* * Mod Name: ce que vous voulez
* * Package Name : fr.cattaga.modname
* * Minecraft Version : 1.20.1
* * Cocher au moins : Mojang Mappings +  Data Generation + Split client and common sources


* Dans le dossier du projet tapper : `./gradlew vscode` pour initier le projet dans intelliJ // Il n'y a pas d'intelliJ en 1.20.1
* Puis relancer votre IDE


## Commencer le dev d'un mod

### Votre IDE

Pour IntelliJ : 
* mettre un Java 1.17
* mettre l'extension : [Minecraft Development
](https://plugins.jetbrains.com/plugin/8327-minecraft-development)

* [Régler gradlew avec un JDK de 25](https://imgur.com/a/bXZMp0M)  ( pas 21 pas 27 précisément 25 sinon il veut plus )


* [Faite le build du projet](https://docs.fabricmc.net/1.20.4/develop/getting-started/setting-up-a-development-environment) `./gradlew intellij`
* Pensez a générer les sources minecraft avec `./gradlew genSources`
* Pensez a générer les sources minecraft avec `./gradlew SourcesJar` // Je maitrise pas bien l'écart entre les 2


* Lancer votre test client avec : `./gradlew runClient`



## Quelques tuto 

* [Rajouter un nouveau mob](https://www.youtube.com/watch?v=wgVnkqqBGFs)
* [Rajouter un block](https://docs.fabricmc.net/1.20.4/develop/blocks/first-block)


## Bonnes pratiques

* Créer une constante avec le mod ID ( souvent le nom du mod / nom du mod finissant par mod)


