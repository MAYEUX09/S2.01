# 🤖 Simulation de Robot Minier (Java)

Ce projet Java implémente une simulation où un robot navigue sur une grille pour extraire et transporter des ressources. Il dispose de deux modes d'exécution distincts : une interface en ligne de commande (Console) et une interface graphique (GUI).

## ✨ Fonctionnalités Principales
* **Environnement de Simulation :** Génération d'un monde (`Monde.java`) composé de différents secteurs tels que des terrains classiques (`Terrain.java`), des obstacles aquatiques (`Eau.java`), des zones d'extraction (`Mine.java`) et une zone de stockage (`Entrepot.java`).
* **Collecte de Ressources :** Gestion de l'extraction de différents types de minerais (`Minerais.java`), incluant spécifiquement l'or (`OR.java`) et le nickel (`Nickel.java`).
* **Navigation Intelligente :** Intégration de l'algorithme de Dijkstra pour calculer le trajet le plus court du robot à travers la grille, avec des comportements validés par des tests dédiés (`RobotDijkstraTest.java`).
* **Interfaces Utilisateur :** Lancement au choix via un affichage texte (`MainConsole.java`) ou via une interface graphique interactive (`MainGraphique.java`, `fenetredejeu2.java`) équipée de panneaux de contrôle et d'informations (`PanelCommandes.java`, `PanelInfos.java`).

## 📂 Architecture du Code

| Dossier / Package | Description |
| :--- | :--- |
| **`src/main/java/Jeu/`** | Cœur du modèle de données et logique métier (classes du Robot, du Monde et des Minerais). |
| **`src/main/java/affichage/`** | Composants visuels pour la version graphique (`PanelGrille`, interfaces fenêtrées). |
| **`src/main/java/Test/`** | Tests unitaires vérifiant la logique des mines, du monde et l'algorithme de routage. |
| **`src/main/resources/images/`** | Textures et sprites utilisés par l'interface graphique (robot, océan, terrain, mine, entrepôt). |

## 🚀 Installation et Exécution

**1. Installation de Java (Prérequis)**
Pour exécuter ce projet, un environnement d'exécution Java (JRE) doit être installé sur votre machine.
* **Windows :** Téléchargez l'installateur depuis le site officiel d'Oracle ou utilisez le terminal : `winget install Microsoft.OpenJDK.17`
* **macOS :** Utilisez Homebrew : `brew install openjdk`
* **Linux (Debian/Ubuntu) :** Exécutez la commande : `sudo apt update && sudo apt install default-jre`
* Vérifiez l'installation en tapant `java -version` dans votre terminal.

**2. Lancement des exécutables**
Le projet contient deux archives précompilées situées dans le dossier `out/artifacts/`. Ouvrez un terminal à la racine du projet et utilisez les commandes suivantes :

* **Pour le mode Console :**
  ```bash
  java -jar out/artifacts/SAE01_jar/SAE01_console.jar

* **Pour le mode Graphique :**
  ```bash
  java -jar out/artifacts/SAE01_jar2/SAE01_graphique.jar
  ```

