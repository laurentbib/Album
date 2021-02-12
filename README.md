# ALBUM
## Contexte
Création d'une application dans le cadre de tests techniques 

## Choix techniques
* Clean architecture(3 modules : presentation, domain, data) => meilleur découpage, plus facilement testable et scalable. Indépendance de chaque module
presentation connnaît domain et data
data connaît domain
domain ne connaît personne

* module data : le point d'entrée du module est un repository qui va être en charge de lancer les appels réseaux (Retrofit)  et de sauvegarder les données en local (room) en suivant le principe de "single source of truth". On oberve depuis presentation uniquement les changements en base de données. Toutes les données réseaux sont ainsi sauvegardées. L'avantage essentiel c'est de pouvoir afficher des datas rapidement même durant l'appel au webservice et de gérer du offline.
Le choix de room m'a paru évident, c'est une lib jetpack, on peut observer les changement via kotlin flow, c'est facile à mettre en place.

* module domain : c'est un module sans contexte android, il contient les objets métiers, les usecases et l'interface du repository

* module presentation : J'ai choisi pour ce module de faire du MVI (Model View Intent), ça fait quelques temps que je souhaitais tester ce pattern, j'ai profité de ce test pour le faire. On entends par Intent une action (ce n'est pas l'intent d'android), souvent un input de l'utilisateur.La vue transmet cet Intent (dans le projet AlbumScreenAction par exemple) au viewModel qui lui renvoi un état immuable (AlbumScreenState) en retour. Cela représente un principe fonctionnel l'unidirectionnel Data Flow. C'est une structure assez fermé qui a de nombreux avantages : 
-  l’UI reflète à chaque instant l’état du ViewModel. 
- Les états sont prédictibles et facilement testables.
- Un développeur nouveau venu, une fois qu'il a compris le principe, peut aisément intervenir dans toute l'appli
- Tous les états de la vue et des différentes actions sont centralisés dans un fichier (AlbumScreenContract) permettant de savoir ce que fait cet écran dans sa globalité.

l'observation de la bdd dans le view model se fait via kotlin flow, c'est idéal pour ça.

J'ai fait le choix aussi d'utiliser une single activity et le navigation component, j'apprécie ce principe et sa facilité de mise en place.

## Temps passé
* Lundi -> 15 min => analyse des données du web service
* Mardi -> 4h 30m => Mise en place de l'archi (dépendances, modules, etc), création BDD et repository
* Mercredi-> 4h => Mise en place features album
* Jeudi-> 3h => Mise en place features gallery
* Vendredi> 3h => ajustement graphique, documentation
* TOTAL => environ 15h

## librairies utilisées
* appels réseaux => Retrofit
* bdd => Room
* observation data => kotlin flow
* observation Ui => livedata
* affichage des images => Coil
* affichage des loading => Shimmer layout
* navigation => navigation component

## réflexions
Avant de me lancer, j'ai commençé à bien réfléchir, au projet, en anlaysant l'api. Fallait-il que j'aille au plus rapide, utiliser des libs que je maitrise uniquement ou
profiter de ce test pour tester de nouvelles choses (tels que Coil par exemple et le MVI).
J'ai choisi de faire un projet plus complexe sans doute à la manière dont je l'aborderai avec des contraintes professionnelles et bien sùr de tenter de nouvelles choses car il faut se faire plaisir.
J'ai prêté attention au rangement des packages , au nommage des classes et fonctions. C'est important d'être clair surtout pour les autres.
Pour d'autres détails, nous pourrons en discuter oralement. Bonne lecture de code


