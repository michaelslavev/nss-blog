# welcome to NSS-BLOGO
# Společné požadavky:
- vyber vhodne technologie a jazyka: Java/SpringBoot, Java, C#, …
- readme v gitu s popisem co je hotove a kde se funkcionalita nachazi
- vyuziti spolecne DB (relacni nebo grafova)
- vyuziti cache (napriklad Hazelcast)
- vyuziti messaging principu (Kafka nebo JMS)
- aplikace bude zabezpecena pomoci bud basic authorization nebo pomoci OAuth2
- vyuziti Inteceptors (alespon jedna trida) - napriklad na logovani (prijde request a zapiseme ho do
logu)
- vyuziti jedne z technologie: SOAP, REST, graphQL, Java RMI, Corba, XML-RPC
- nasazeni na produkcni server napriklad Heroku
- vyber vhodne architektury (event base, pipe and filter, ...)
- inicializacni postup (jak aplikaci deploynout, kde jsou zakladni data do nove DB typu admin apod)
- vyuziti elasticsearch
- pouziti alespon 5 design patternu (musi davat smysl :) )
- za kazdeho clena tymu 2 UC (use cases - aby SW nebyl trivialni)

# Hotové požadavky:
- vyber vhodne technologie a jazyka: Java/SpringBoot  --| (DONE)
- readme v gitu s popisem co je hotove a kde se funkcionalita nachazi --| (IN PROGRESS)
- vyuziti spolecne DB: relační na Amazon cloud PostgreSQL --| (DONE)
- vyuziti cache (napriklad Hazelcast) --| (DONE snad)
- vyuziti messaging principu (Kafka nebo JMS) --| (NEIMPLEMENTUJEME)
- aplikace bude zabezpecena: Spring Security Basic Authentication --| (DONE)
- vyuziti Inteceptors: prijde request a zapiseme ho do logu --| (//TODO FOR MATESIK)
- vyuziti jedne z technologie: REST --| (DONE)
- nasazeni na produkcni server Heroku --| (DONE)
- vyber vhodne architektury (event base) --| (DONE snad)
- inicializacni postup: autodeploy by Heroku, PostgreSQL Amazon by Heroku --| (DONE)
- vyuziti elasticsearch --| (//TODO asi)
- pouziti alespon 5 design patternu (musi davat smysl :) ) --| (//Chain of responsibility, Fasade (hazel cast), Dependency injection, //todo factory na dao, )
- za kazdeho clena tymu 2 UC --| (//VYCUCAME SI TO Z PRSTU)
