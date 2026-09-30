# Assignment 2 — Factory Method & Abstract Factory

## Domain

This project models a small cloud deployment system. A cloud environment contains three related product types:

- Compute Node
- Database
- Object Storage

The system supports four compatible cloud families:

- AWS
- Azure
- Google Cloud
- Oracle Cloud

Each family has its own implementation of all three product types. The products have different behavior, so the families are not only different names.

## Requirements Coverage

### Part A — Start Without Factories

`LegacyClient` is the required baseline implementation. It creates concrete products directly and selects the family with `if/else` logic.

The direct approach demonstrates these design problems:

1. The client depends on concrete classes.
2. Family selection creates a growing conditional block.
3. Adding another family requires changing the client.
4. Creation logic is repeated.
5. Manually creating products makes incompatible combinations easier to introduce.

This baseline is kept as a separate class so it can be included in the first Git commit before the refactoring.

### Part B — Factory Method

`DeploymentCreator` is the Creator and `createTask()` is the Factory Method.

`StandardCreator`, `BlueGreenCreator`, and `RecoveryCreator` override the method and create different `DeploymentTask` implementations.

The Creator also contains real business workflow in `deploy()`: it creates a task and executes it for an application. Therefore, the Factory Method is not just a method that returns `new` without a purpose.

### Part C — Abstract Factory

`CloudFactory<F>` is the Abstract Factory. It creates three related products:

- `ComputeNode<F>`
- `Database<F>`
- `ObjectStorage<F>`

The concrete factories are:

- `AwsFactory`
- `AzureFactory`
- `GcpFactory`
- `OracleFactory`

Every concrete factory creates one compatible product family.

### Part D — Compatibility Rule

The product interfaces use a generic family marker:

- `ComputeNode<F>`
- `Database<F>`
- `ObjectStorage<F>`

For example, AWS products use `AwsFamily` as their type parameter. `CloudEnvironment<AwsFamily>` can therefore contain only AWS product types.

This uses the type system instead of a runtime `if (wrongFamily)` check. A mixed environment such as an AWS compute node with an Azure database cannot be passed to `CloudEnvironment<AwsFamily>` without breaking the generic type contract.

### Part E — Runtime Factory Selection

`CloudFactoryProvider` selects the factory from an external command-line value.

Supported values:

```text
aws
azure
gcp
oracle
```

The main application receives the value from the command line. After the factory is selected, the deployment operations work through `CloudEnvironment` and product abstractions rather than concrete family classes.

### Part F — Business Scenario

`DeploymentService` in `Business.java` provides three realistic multi-product operations:

1. `provision()` starts compute, connects the database, and stores the application.
2. `backup()` creates a database backup and archives the application in storage.
3. `scale()` scales compute and records the scaling operation through the database and storage resources.

The products therefore collaborate as parts of one cloud environment.

### Part G — Add a New Family

Oracle Cloud is the fourth family added after the original AWS, Azure, and GCP families.

The new family contains:

- `OracleComputeNode`
- `OracleDatabase`
- `OracleObjectStorage`
- `OracleFactory`

The existing `DeploymentService` does not need an Oracle-specific method. Only the new family implementation and one runtime-provider case are needed.

### Part H — UML

The UML source is stored in `docs/uml.puml`. The diagram marks both the Factory Method and Abstract Factory parts.

A PNG version is provided separately as `uml.png`.

### Part I — Automated Tests

`Assignment2Test` contains 25 automated tests. The tests cover:

- original product families;
- concrete product creation;
- compatibility;
- runtime factory selection;
- three business behaviors;
- invalid runtime input;
- Factory Method behavior;
- the fourth family;
- abstraction-based client usage.

Run the tests with:

```bash
mvn test
```

### Part J — Git History

The project uses nine meaningful commits. The commit sequence follows the development order required by the assignment and keeps the initial direct-creation implementation in history.

## Project Structure

```text
Assignment2/
├── src/
│   ├── main/java/assignment2/
│   │   ├── CloudProducts.java
│   │   ├── Factories.java
│   │   ├── Deployment.java
│   │   ├── Business.java
│   │   ├── Legacy.java
│   │   └── Main.java
│   └── test/java/assignment2/
│       └── Assignment2Test.java
├── docs/
│   └── uml.puml
├── README.md
├── pom.xml
└── .gitignore
```

The project intentionally keeps related classes in a small number of files. This makes the assignment easier to read and defend while preserving the required design patterns.

## How to Run

Build and test:

```bash
mvn test
```

Run with a selected family:

```bash
mvn exec:java -Dexec.mainClass=assignment2.Main -Dexec.args="aws"
```

Replace `aws` with `azure`, `gcp`, or `oracle`.

## Detailed Report

### 1. Problem

The initial application directly creates concrete cloud products. This couples the client to implementation classes and forces family selection into business code.

### 2. Initial Design

`LegacyClient` represents the system before refactoring. It uses conditional logic and direct `new` expressions. This makes the initial problem visible in Git history instead of hiding it.

### 3. Factory Method

The Factory Method is used for deployment tasks. `DeploymentCreator` owns the deployment workflow, while subclasses decide which `DeploymentTask` is created.

The pattern is useful because the workflow stays unchanged when a new deployment strategy is introduced. Only the creator subclass and task implementation need to be added.

### 4. Abstract Factory

The Abstract Factory solves a different problem: creating a complete family of compatible cloud resources.

`CloudFactory` defines creation methods for compute, database, and storage. Each concrete factory implements all three methods for one cloud family.

The client does not choose individual concrete products. It chooses one factory and receives a consistent family.

### 5. Compatibility

The generic family parameter is the main architectural protection against mixing products from different families.

For example:

```text
CloudEnvironment<AwsFamily>
    ComputeNode<AwsFamily>
    Database<AwsFamily>
    ObjectStorage<AwsFamily>
```

The corresponding Azure environment uses `AzureFamily`. Because the family type is part of the product type, incorrect combinations are rejected by Java's type system rather than by a manual family check.

### 6. Runtime Selection

The command-line argument is read by `Main` and passed to `CloudFactoryProvider`. The provider selects the correct factory. The rest of the application uses the returned abstraction.

This satisfies the requirement that the family can be selected dynamically and that the main business logic does not repeatedly instantiate concrete factories.

### 7. Business Operations

The three operations in `Business.java` show meaningful collaboration:

- provisioning needs compute, database, and storage;
- backup coordinates database and storage;
- scaling changes compute and records the operation using the other resources.

Therefore, the products belong to one coherent system rather than being isolated demonstration classes.

### 8. Fourth Family Extension

Oracle Cloud demonstrates extensibility. The original deployment service is unchanged when Oracle products are added. The new factory supplies the three required product types, and the runtime provider adds the new external selection value.

### 9. Testing

The tests focus on behavior instead of only constructors. They check concrete creation, family consistency, runtime selection, invalid input, business collaboration, Factory Method behavior, and the new family.

### 10. Conclusion

Factory Method separates deployment workflow from the concrete deployment strategy. Abstract Factory separates cloud-family creation from business logic and keeps related products consistent. Together, the patterns reduce coupling and make adding a new product family straightforward.

## Git Commit Commands

### 1. Initial domain and baseline

```bash
git init
git add pom.xml .gitignore src/main/java/assignment2/Legacy.java
git commit -m "Create initial cloud deployment baseline"
```

### 2. Product abstractions and families

```bash
git add src/main/java/assignment2/CloudProducts.java
git commit -m "Add cloud product abstractions and families"
```

### 3. Factory Method

```bash
git add src/main/java/assignment2/Deployment.java
git commit -m "Introduce Factory Method for deployment tasks"
```

### 4. Abstract Factory

```bash
git add src/main/java/assignment2/Factories.java
git commit -m "Introduce Abstract Factory for cloud products"
```

### 5. Runtime application

```bash
git add src/main/java/assignment2/Main.java
git commit -m "Add runtime cloud family selection"
```

### 6. Business behavior and tests

```bash
git add src/main/java/assignment2/Business.java src/test/java/assignment2/Assignment2Test.java
git commit -m "Add business operations and automated tests"
```

### 7. Fourth family

```bash
git add src/main/java/assignment2/CloudProducts.java src/main/java/assignment2/Factories.java
git commit -m "Add Oracle Cloud product family"
```

### 8. UML

```bash
git add docs/uml.puml
git commit -m "Add UML design diagram"
```

### 9. Documentation

```bash
git add README.md
git commit -m "Complete assignment report and documentation"
```

Check the final history with:

```bash
git log --oneline --reverse
```

## Fourth Product Family

Oracle Cloud is supported as the fourth product family. It provides compatible compute, database, and object storage products through the same Abstract Factory interface.
