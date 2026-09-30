package assignment2;

interface DeploymentTask {
    String execute(String application);
}

class StandardDeployment implements DeploymentTask {
    public String execute(String application) { return "Standard deployment completed for " + application; }
}

class BlueGreenDeployment implements DeploymentTask {
    public String execute(String application) { return "Blue-green deployment completed for " + application; }
}

class RecoveryDeployment implements DeploymentTask {
    public String execute(String application) { return "Recovery deployment completed for " + application; }
}

abstract class DeploymentCreator {
    final String deploy(String application) {
        DeploymentTask task = createTask();
        return task.execute(application);
    }

    protected abstract DeploymentTask createTask();
}

class StandardCreator extends DeploymentCreator {
    protected DeploymentTask createTask() { return new StandardDeployment(); }
}

class BlueGreenCreator extends DeploymentCreator {
    protected DeploymentTask createTask() { return new BlueGreenDeployment(); }
}

class RecoveryCreator extends DeploymentCreator {
    protected DeploymentTask createTask() { return new RecoveryDeployment(); }
}
