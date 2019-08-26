
# La structure du projet #
  
## Les bases ##  
Dans le dossier app/ressources/views/default (le dossier gloabales des bases) on trouve deux fichier twig:  
1)base_back.html.twig ( va etre utiliser pour toute les templates back (admin )  
2)base_front.html.twig ( va etre utiliser pour toute les pages front (client,freelancer et visiteur ))  
Ces deux fichiers vont comporter la liaison avec le css et le js qui se situes dans le dossier web  
  
## Les sous Bases ##  
Le projet se constitue principalement de 4 bundle Admin,Client,Freelancer et visiteur. Chaque Bundle a essentiellement  
une base qui va herité soit du base_back.html.twig ou base_front.html.twig  
  
Les sous bases comportent les navbar du top et le sidebar s'il existe (chaque bundle posside son propre version  
de navbar).  
  
## Les content ou les pages ##  
Ces pages vont etre localisé sous les views des bundles. ils heritent des sous bases et implementent le corps des pages.
