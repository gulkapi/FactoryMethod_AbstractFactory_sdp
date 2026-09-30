package assignment2;

class LegacyClient {
    static String create(String family) {
        if (family.equalsIgnoreCase("aws")) {
            AwsComputeNode compute = new AwsComputeNode();
            AwsDatabase database = new AwsDatabase();
            AwsObjectStorage storage = new AwsObjectStorage();
            return compute.start() + " | " + database.connect() + " | " + storage.store("legacy-app");
        } else if (family.equalsIgnoreCase("azure")) {
            AzureComputeNode compute = new AzureComputeNode();
            AzureDatabase database = new AzureDatabase();
            AzureObjectStorage storage = new AzureObjectStorage();
            return compute.start() + " | " + database.connect() + " | " + storage.store("legacy-app");
        } else if (family.equalsIgnoreCase("gcp")) {
            GcpComputeNode compute = new GcpComputeNode();
            GcpDatabase database = new GcpDatabase();
            GcpObjectStorage storage = new GcpObjectStorage();
            return compute.start() + " | " + database.connect() + " | " + storage.store("legacy-app");
        }
        throw new IllegalArgumentException("Unknown family: " + family);
    }
}
