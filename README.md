# Serveur CATTAGA S2

### Lancement local

- Il vous faudra Docker et Docker compose (me demandez pas comment on l'installe sous Windows, je sais pas mdr),
- Il vous faudra aussi le dernier modpack au format mrpack. Demandez à un membre du staff, ou utilisez celui de Cattaga S1 (à ce stade il marche encore), 
- Renommez le fichier `.mrpack` en `Cattaga-2.0.0-server.mrpack` 

```bash
docker compose up -d
```

#### Connexion au serveur local
```bash
docker compose attach cattaga-creative
```
*(Ctrl+P puis Ctrl+Q pour sortir sans tuer le serveur)*

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
