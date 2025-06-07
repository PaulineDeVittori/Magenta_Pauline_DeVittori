# Magenta – Application Android dédiée à la couleur

**Magenta** est une application Android développée en **Kotlin** avec **Jetpack Compose**
Elle permet d’explorer et de rechercher des fiches couleurs. 
Les utilisateurs peuvent mettre leurs couleurs préférées en favoris, obtenir une couleur aléatoire, et consulter les dernières couleurs ajoutées.

---

## Fonctionnalités

- **Dictionnaire de couleurs** : Parcourir une large sélection de fiches couleurs.
- **Filtrage par lettre** : Filtrer les couleurs par la première lettre de leur nom.
- **Favoris** : Mettre des couleurs en favori. Les favoris sont enregistrés en **local** grâce à **SharedPreferences**.
- **Couleur aléatoire** : Découvrir une couleur au hasard.
- **Accueil** : Afficher les dernières couleurs ajoutées.
- **Recherche** : Trouver une couleur par son nom, par code hexa ou code RGB.
- **Fiche détail** : Afficher les informations HEX et RGB d'une couleur, une définition ainsi qu'une citation.

---

## Technologies utilisées

- **Langage** : Kotlin
- **UI** : Jetpack Compose
- **Persistance des favoris** : SharedPreferences
- **Backend** : Firebase Firestore (lecture des couleurs uniquement)

---

## Données

Les couleurs sont récupérées depuis **Firebase Firestore**. Chaque couleur est définie par :

```kotlin
data class ColorEntity(
    val name: String,
    val hex: String,
    val red: Int,
    val green: Int,
    val blue: Int,
    val isFavorite: Boolean = false
)
```

(l'attribut isFavorite est obsolète car il est maintenant géré par SharedPreferences)

---

## Faire fonctionner l'application

Cloner le repo :

```bash
https://github.com/PaulineDeVittori/Magenta_Pauline_DeVittori.git
```

Ouvrir le projet dans Android Studio.

Lancer l’application sur un émulateur ou appareil Android.

