package assignment2;

public class Main {
    public static void main(String[] args) {
        String family = args.length == 0 ? "aws" : args[0];
        CloudEnvironment<?> environment = CloudFactoryProvider.createEnvironment(family);
        System.out.println("Provision: " + provision(environment));
        System.out.println("Backup: " + backup(environment));
        System.out.println("Scale: " + scale(environment));
        System.out.println(new BlueGreenCreator().deploy("shop-api"));
    }

    private static String provision(CloudEnvironment<?> environment) {
        return provisionUnchecked(environment);
    }

    private static String provisionUnchecked(CloudEnvironment<?> environment) {
        return environment.compute().start() + " | " + environment.database().connect() + " | " + environment.storage().store("shop-api");
    }

    private static String backup(CloudEnvironment<?> environment) {
        return environment.database().backup() + " | " + environment.storage().archive("shop-api");
    }

    private static String scale(CloudEnvironment<?> environment) {
        return environment.compute().scale(3) + " | " + environment.database().connect() + " | " + environment.storage().store("scale-record");
    }
}
