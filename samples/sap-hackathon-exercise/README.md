# EDC DataManagement API Hands-On Exercise

# Prerequisite 1: Clone Eclipse Dataspace Connector GitHub Repository

As until now the EDC doesn’t publish its modules as artifacts to a maven registry, any hands-on exercise requires a copy of the source code repository.
To jump-start our exercise, the EDC repo was forked and enhanced. Execute the following command:

```bash
git clone https://github.com/alexandrudanciu/DataSpaceConnector.git
```

Go to samples/sap-hackathon-exercise and follow the instructions on the README.md page.

# Prerequisite 2: Install Postman

# Exercise 1: Start control plane

From the project root execute the following command:

```bash
./gradlew clean samples:sap-hackathon-exercise:provider-control-plane:build

java -Dedc.fs.config=samples/sap-hackathon-exercise/provider-control-plane/config.properties -Dedc.vault=samples/sap-hackathon-exercise/provider-control-plane/src/main/resources/provider-vault.properties -Dedc.keystore=samples/sap-hackathon-exercise/provider-control-plane/src/main/resources/certs/cert.pfx -Dedc.keystore.password=123456 -jar samples/sap-hackathon-exercise/provider-control-plane/build/libs/provider-control-plane.jar
```

# Exercise 2: List all registered assets

The EDC data management api uses the following configuration:
* the service is registered to the port specified by the configuration parameter *web.http.data.port*
* the api base path is specified by the configuration parameter *web.http.data.path*
* authentication is based on the api key specified by the parameter *edc.api.auth.key*

Data sets offered by the EDC are called assets. Create a new Postman HTTP request for calling the data management api endpoint *GET /assets* and list all currently registered assets.

*Hint: below resources/openapi you will find a documentation of all REST APIs provided by the EDC*

# Exercise 3: Inspect asset attributes

EDC assets are described by properties and data addresses. 
Visit the source code of the java classes *org.eclipse.dataspaceconnector.spi.types.domain.asset.Asset* and *org.eclipse.dataspaceconnector.spi.types.domain.DataAddress* and inspect the characteristics of data assets.

# Exercise 4: Create new asset

The REST API itself expects as payload an asset structure as specified by the *org.eclipse.dataspaceconnector.api.datamanagement.asset.model.AssetEntryDto*. Inspect this class to understand how to construct a corresponding payload.

Create a new Postman HTTP request for calling the data management api endpoint *POST /assets*.

# Exercise 5: Get an asset by its id

Create a new Postman HTTP request for calling the data management api endpoint *GET /assets/{id}* to fetch the previously created asset.

# Exercise 6: Explore the contract definition api

Internally, the EDC implements a lifecycle for contracts going beyond the IDS concepts of offer and agreement by introducing the concept of contract definitions serving as abstract templates. Concrete offers are then calculated on demand for a data asset based on a definition targeted towards a requesting participant.

Contract definitions and data assets are created and managed independently of each other. For deriving offers on demand, the definition specifies the following aspects:
* Asset selector: Using a set of criterions, the asset selector is used to evaluate if a data asset is subject to the corresponding definition.
* Access policy: By specifying a set of rules, the access policy support to determine, if a data asset should be offered to a specific consumer at all.

Add the following dependency to the gradle build file of the provider-control-plane.

```
api(project(":extensions:api:data-management:contractdefinition"))
```

Build and run the provider control plane using the commands listed in exercise 1 and then explore the endpoints provided by the contract definition api.
