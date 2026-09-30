package assignment2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Assignment2Test {
    @Test void awsFactoryCreatesCompute() { assertInstanceOf(AwsComputeNode.class, new AwsFactory().createComputeNode()); }
    @Test void awsFactoryCreatesDatabase() { assertInstanceOf(AwsDatabase.class, new AwsFactory().createDatabase()); }
    @Test void awsFactoryCreatesStorage() { assertInstanceOf(AwsObjectStorage.class, new AwsFactory().createStorage()); }
    @Test void azureFactoryCreatesFamily() { CloudEnvironment<AzureFamily> e = new CloudEnvironment<>(new AzureFactory()); assertTrue(e.compute().start().contains("Azure")); }
    @Test void gcpFactoryCreatesFamily() { CloudEnvironment<GcpFamily> e = new CloudEnvironment<>(new GcpFactory()); assertTrue(e.database().connect().contains("GCP")); }
    @Test void oracleFactoryCreatesFamily() { CloudEnvironment<OracleFamily> e = new CloudEnvironment<>(new OracleFactory()); assertTrue(e.storage().store("x").contains("Oracle")); }
    @Test void awsProductsAreCompatible() { CloudEnvironment<AwsFamily> e = new CloudEnvironment<>(new AwsFactory()); assertTrue(e.compute().start().contains("AWS") && e.database().connect().contains("AWS") && e.storage().store("x").contains("AWS")); }
    @Test void azureProductsAreCompatible() { CloudEnvironment<AzureFamily> e = new CloudEnvironment<>(new AzureFactory()); assertTrue(e.compute().start().contains("Azure") && e.database().connect().contains("Azure") && e.storage().store("x").contains("Azure")); }
    @Test void gcpProductsAreCompatible() { CloudEnvironment<GcpFamily> e = new CloudEnvironment<>(new GcpFactory()); assertTrue(e.compute().start().contains("GCP") && e.database().connect().contains("GCP") && e.storage().store("x").contains("GCP")); }
    @Test void oracleProductsAreCompatible() { CloudEnvironment<OracleFamily> e = new CloudEnvironment<>(new OracleFactory()); assertTrue(e.compute().start().contains("Oracle") && e.database().connect().contains("Oracle") && e.storage().store("x").contains("Oracle")); }
    @Test void runtimeAwsSelection() { assertTrue(CloudFactoryProvider.createEnvironment("aws").compute().start().contains("AWS")); }
    @Test void runtimeAzureSelection() { assertTrue(CloudFactoryProvider.createEnvironment("azure").compute().start().contains("Azure")); }
    @Test void runtimeGcpSelection() { assertTrue(CloudFactoryProvider.createEnvironment("gcp").compute().start().contains("GCP")); }
    @Test void runtimeOracleSelection() { assertTrue(CloudFactoryProvider.createEnvironment("oracle").compute().start().contains("Oracle")); }
    @Test void invalidRuntimeSelectionFails() { assertThrows(IllegalArgumentException.class, () -> CloudFactoryProvider.createEnvironment("unknown")); }
    @Test void provisionUsesMultipleProducts() { CloudEnvironment<AwsFamily> e = new CloudEnvironment<>(new AwsFactory()); String result = DeploymentService.provision(e, "shop"); assertTrue(result.contains("compute") && result.contains("database") && result.contains("stored")); }
    @Test void backupUsesDatabaseAndStorage() { CloudEnvironment<AzureFamily> e = new CloudEnvironment<>(new AzureFactory()); String result = DeploymentService.backup(e, "shop"); assertTrue(result.contains("backup") && result.contains("archived")); }
    @Test void scaleUsesMultipleProducts() { CloudEnvironment<GcpFamily> e = new CloudEnvironment<>(new GcpFactory()); String result = DeploymentService.scale(e, 4); assertTrue(result.contains("scaled to 4") && result.contains("database")); }
    @Test void standardFactoryMethodCreatesTask() { assertTrue(new StandardCreator().deploy("app").contains("Standard deployment")); }
    @Test void blueGreenFactoryMethodCreatesTask() { assertTrue(new BlueGreenCreator().deploy("app").contains("Blue-green deployment")); }
    @Test void recoveryFactoryMethodCreatesTask() { assertTrue(new RecoveryCreator().deploy("app").contains("Recovery deployment")); }
    @Test void factoryMethodRunsBusinessWorkflow() { assertEquals("Blue-green deployment completed for app", new BlueGreenCreator().deploy("app")); }
    @Test void legacyClientShowsDirectCreationBaseline() { assertTrue(LegacyClient.create("aws").contains("AWS")); }
    @Test void fourthFamilyWorksWithBusinessLogic() { CloudEnvironment<OracleFamily> e = new CloudEnvironment<>(new OracleFactory()); assertTrue(DeploymentService.provision(e, "oracle-app").contains("Oracle")); }
    @Test void clientUsesFactoryAbstraction() { CloudFactory<AwsFamily> factory = new AwsFactory(); CloudEnvironment<AwsFamily> e = new CloudEnvironment<>(factory); assertNotNull(e.compute()); }
}
