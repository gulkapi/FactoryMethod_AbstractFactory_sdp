package assignment2;

class DeploymentService {
    static <F> String provision(CloudEnvironment<F> environment, String application) {
        return environment.compute().start() + " | " + environment.database().connect() + " | " + environment.storage().store(application);
    }

    static <F> String backup(CloudEnvironment<F> environment, String application) {
        return environment.database().backup() + " | " + environment.storage().archive(application);
    }

    static <F> String scale(CloudEnvironment<F> environment, int instances) {
        return environment.compute().scale(instances) + " | " + environment.database().connect() + " | " + environment.storage().store("scale-record");
    }
}
