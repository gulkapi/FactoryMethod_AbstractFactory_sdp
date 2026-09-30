package assignment2;

interface CloudFactory<F> {
    ComputeNode<F> createComputeNode();
    Database<F> createDatabase();
    ObjectStorage<F> createStorage();
}

class AwsFactory implements CloudFactory<AwsFamily> {
    public ComputeNode<AwsFamily> createComputeNode() { return new AwsComputeNode(); }
    public Database<AwsFamily> createDatabase() { return new AwsDatabase(); }
    public ObjectStorage<AwsFamily> createStorage() { return new AwsObjectStorage(); }
}

class AzureFactory implements CloudFactory<AzureFamily> {
    public ComputeNode<AzureFamily> createComputeNode() { return new AzureComputeNode(); }
    public Database<AzureFamily> createDatabase() { return new AzureDatabase(); }
    public ObjectStorage<AzureFamily> createStorage() { return new AzureObjectStorage(); }
}

class GcpFactory implements CloudFactory<GcpFamily> {
    public ComputeNode<GcpFamily> createComputeNode() { return new GcpComputeNode(); }
    public Database<GcpFamily> createDatabase() { return new GcpDatabase(); }
    public ObjectStorage<GcpFamily> createStorage() { return new GcpObjectStorage(); }
}

class OracleFactory implements CloudFactory<OracleFamily> {
    public ComputeNode<OracleFamily> createComputeNode() { return new OracleComputeNode(); }
    public Database<OracleFamily> createDatabase() { return new OracleDatabase(); }
    public ObjectStorage<OracleFamily> createStorage() { return new OracleObjectStorage(); }
}

class CloudEnvironment<F> {
    private final ComputeNode<F> compute;
    private final Database<F> database;
    private final ObjectStorage<F> storage;

    CloudEnvironment(CloudFactory<F> factory) {
        compute = factory.createComputeNode();
        database = factory.createDatabase();
        storage = factory.createStorage();
    }

    ComputeNode<F> compute() { return compute; }
    Database<F> database() { return database; }
    ObjectStorage<F> storage() { return storage; }
}

class CloudFactoryProvider {
    static CloudEnvironment<?> createEnvironment(String family) {
        return switch (family.toLowerCase()) {
            case "aws" -> new CloudEnvironment<>(new AwsFactory());
            case "azure" -> new CloudEnvironment<>(new AzureFactory());
            case "gcp" -> new CloudEnvironment<>(new GcpFactory());
            case "oracle" -> new CloudEnvironment<>(new OracleFactory());
            default -> throw new IllegalArgumentException("Unknown cloud family: " + family);
        };
    }
}
