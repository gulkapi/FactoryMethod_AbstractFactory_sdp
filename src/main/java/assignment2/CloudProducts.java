package assignment2;

interface ComputeNode<F> {
    String start();
    String scale(int instances);
}

interface Database<F> {
    String connect();
    String backup();
}

interface ObjectStorage<F> {
    String store(String object);
    String archive(String object);
}

interface AwsFamily {}
interface AzureFamily {}
interface GcpFamily {}
interface OracleFamily {}

class AwsComputeNode implements ComputeNode<AwsFamily> {
    public String start() { return "AWS compute started"; }
    public String scale(int instances) { return "AWS compute scaled to " + instances; }
}

class AwsDatabase implements Database<AwsFamily> {
    public String connect() { return "AWS database connected"; }
    public String backup() { return "AWS database backup created"; }
}

class AwsObjectStorage implements ObjectStorage<AwsFamily> {
    public String store(String object) { return "AWS stored " + object; }
    public String archive(String object) { return "AWS archived " + object; }
}

class AzureComputeNode implements ComputeNode<AzureFamily> {
    public String start() { return "Azure compute started"; }
    public String scale(int instances) { return "Azure compute scaled to " + instances; }
}

class AzureDatabase implements Database<AzureFamily> {
    public String connect() { return "Azure database connected"; }
    public String backup() { return "Azure database backup created"; }
}

class AzureObjectStorage implements ObjectStorage<AzureFamily> {
    public String store(String object) { return "Azure stored " + object; }
    public String archive(String object) { return "Azure archived " + object; }
}

class GcpComputeNode implements ComputeNode<GcpFamily> {
    public String start() { return "GCP compute started"; }
    public String scale(int instances) { return "GCP compute scaled to " + instances; }
}

class GcpDatabase implements Database<GcpFamily> {
    public String connect() { return "GCP database connected"; }
    public String backup() { return "GCP database backup created"; }
}

class GcpObjectStorage implements ObjectStorage<GcpFamily> {
    public String store(String object) { return "GCP stored " + object; }
    public String archive(String object) { return "GCP archived " + object; }
}

class OracleComputeNode implements ComputeNode<OracleFamily> {
    public String start() { return "Oracle compute started"; }
    public String scale(int instances) { return "Oracle compute scaled to " + instances; }
}

class OracleDatabase implements Database<OracleFamily> {
    public String connect() { return "Oracle database connected"; }
    public String backup() { return "Oracle database backup created"; }
}

class OracleObjectStorage implements ObjectStorage<OracleFamily> {
    public String store(String object) { return "Oracle stored " + object; }
    public String archive(String object) { return "Oracle archived " + object; }
}
