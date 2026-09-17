# Serveur CATTAGA S2

### Lancement local

#### Installer Docker :

- Il vous faudra Docker et Docker compose
- - Installation coté Linux voir avec Xurei

- - Pour l'installer sur windows (note de Jimauve)
- - Télécharger Docker : https://docs.docker.com/desktop/setup/install/windows-install/ -> Docker Desktop for Windows - x86_64
- - Lancer un Terminal et tapper dedans `docker compose`
- - Vous devriez avoir en réponse : `Run 'docker compose COMMAND --help' for more information on a command.`
- - Tapper `wsl --install` pour installer le [Windows SubSystem Linux](https://docs.docker.com/desktop/setup/install/windows-install/?uuid=E519AB9D-0C2F-460A-9303-530C2B04F627#wsl-verification-and-setup), un outil nécessaire au fonctionnement de docker dans Windows.
- - Puis `wsl --update`
- - Puis `wsl --set-default-version 2`
- - Lancer Docker Desktop
- - Docker Desktop devrait afficher : Your running container show up there
- - - Si vous avez une erreur Aller dans le bios et regarder si dans l'overclocking du Processeur vous avez bien activé la virtualisation (SVM pour ma carte mère MSI)

Trouble Shooting Installing Windows Docker :

- - - Si vous avez : Activer « Plateforme d’ordinateur virtuel » en exécutant : wsl.exe --install --no-distribution > Aller dans le bios et regarder si dans l'overclocking du Processeur vous avez bien activé la virtualisation (SVM pour ma carte mère MSI)

- - Puis `wsl --install --no-distribution`
- - Puis `wsl --install -d Ubuntu-26.04`

#### Le dernier ModPack

- Il vous faudra aussi le dernier modpack au format mrpack. Demandez à un membre du staff, ou utilisez celui de Cattaga S1 (à ce stade il marche encore),
- Déposez le dans le dossier Git ( la où est le docker docker-compose.yml )
- Renommez le fichier `.mrpack` en `Cattaga-2.0.0-server.mrpack`
- Pour lancer le Docker :

```
bash docker compose up -d --build
```

#### Connexion au serveur local

```bash
    docker compose attach cattaga-creative
```

_(Ctrl+P puis Ctrl+Q pour sortir sans tuer le serveur)_

#### Extinction du serveur

```bash
docker compose attach cattaga-creative
```

puis

```
stop
```

##### OU (force kill)

```bash
docker compose down
```
