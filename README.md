# Justifications Architecturale

### Swagger déployé:
Je pense qu'un service devrait publier la manière d'interragir avec. HATEOAS est une possibilité, comme Swagger, ou WSDL.

Il est possible de le générer durant le build, pour pouvoir déployer ce swagger vers un API Gateway aussi.

### Spring Security:

Un tel service se doit d'être sécurisé.

login: user

password: regardez dans le log

### Code first:
Parce que j'en avais envie. L'approche API-first (en générant du code à partir d'un swagger pré-existant) est tout aussi valide.

### Couches JPA, métier et Rest isolées avec des mappers entre chaque:
Cela permet à chaque couche d'évoluer sans impacter les autres parties. Il peut facilement y avoir plusieurs versions du même service, ajouter une UI, ajouter des champs en BD qui n'apparaissent pas dans le service, etc.

### Flyway:

Liquibase est aussi parfaitement valide. Ces deux outils permettent de versionner et controller les mises à jour de la base de donnée.

### Génération de code JPA:
Je ne l'ai pas fait, mais il est aussi possible de générer les entités hibernate à partir des scripts Flyway durant le build.

### Tests d'intégration:
Pas eu le temps d'en ajouter, mais possible avec divers outils: Spring Boot Test, Cucumber, etc.