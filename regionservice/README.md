# Region Service

## Model

```
Создать приложение используя Spring Boot реализующее справочник регионов
(свойства: идентификатор, наименование, сокращённое наименование)
```

[MyBatis ORM](https://mybatis.org/mybatis-3/getting-started.html) <br/>
[MyBatis Tests](http://mybatis.org/spring-boot-starter/mybatis-spring-boot-test-autoconfigure/index.html) <br/>
[H2](https://www.h2database.com/html/main.html) <br/>
Spring MVC <br/>
Spring Caching Abstraction + Caffeine

#### Region Model

| ID  | REGION_NAME | REGION_SHORT_NAME | REGION_CODE |
|-----|-------------|-------------------|-------------|

[Can be found here](https://github.com/mityavasilyev/interview-telda-regions/blob/master/regionservice/src/main/java/io/github/mityavasilyev/regionservice/model/Region.java)

## Rest API & Logic & Caching

```
предоставляющее REST-API на чтение и изменение справочника
... Преимуществом будет использование Spring Cache.
```

Requests accepted
in [RegionController](https://github.com/mityavasilyev/interview-telda-regions/blob/master/regionservice/src/main/java/io/github/mityavasilyev/regionservice/controller/ExceptionController.java)
. Business Logic can be found
in [RegionService](https://github.com/mityavasilyev/interview-telda-regions/blob/master/regionservice/src/main/java/io/github/mityavasilyev/regionservice/service/RegionServiceImpl.java)
. If there is some trouble, RegionService will throw a dedicated exception, and it will be processed by
an [ExceptionController](https://github.com/mityavasilyev/interview-telda-regions/blob/master/regionservice/src/main/java/io/github/mityavasilyev/regionservice/controller/ExceptionController.java)

**About caching**: Caffeine handles it basically. Configuration can be
found [here](https://github.com/mityavasilyev/interview-telda-regions/blob/master/regionservice/src/main/java/io/github/mityavasilyev/regionservice/config/CaffeineCacheConfig.java)

## Data and Object Mapping

```
справочник должен храниться в БД в качестве ORM необходимо использовать MyBatis. Использовать встроенные БД 
```

[MyBatis mapper](https://github.com/mityavasilyev/interview-telda-regions/blob/master/regionservice/src/main/java/io/github/mityavasilyev/regionservice/mapper/RegionMapper.java)
is used for db interactions. DB of choice: `H2`

## Extra: Tests

Can be found in `src/test` folder, I guess Basic unit tests to ensure that all functions' logic is valid, nothing
special
