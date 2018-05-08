# Cloudfoundry Task Demo Kotlin

This is a Kotlin/Gradle port of the Java/Maven cf-task-demo project which can be found [here](https://github.com/mikespiering/cf-task-demo)

I compiled it on Java 10 (make sure you're using Kotlin 1.2.30 or newer), and it additionally uses junit 5

NOTE: These instructions are replicated from the original demo except where Gradle is used to build the jar and the path to the produced jar

```
./gradlew clean build -x test
```

Deploy the Spring Cloud Task-based application to the platform. This is a Spring Cloud Task-based application. It has no web endpoint. The platform's health check will try to ascertain the health of the application by checking whether it's responding to an HTTP request. We could also test that the application is bound to a non-HTTP port. Neither apply here, though. This task will start and then stop. So, when you deploy it make sure that there is no health-check specified.


```
cf push --health-check-type none -p build/libs/cftaskdemokotlin-0.0.1-SNAPSHOT.jar runner 
```

Runs the task using the Task runner. This support for tasks is built into the platform.

```
cf run-task runner ".java-buildpack/open_jdk_jre/bin/java org.springframework.boot.loader.JarLauncher endpoint1" --name my-task
```

You can schedule tasks using the PCS job scheduler. Create a new instance of the schduler service, as you would normally:

```
cf cs scheduler-for-pcf standard scheduler-joshlong
```

and then bind the service to the task.

```
cf bs runner scheduler-joshlong
```

Once you have the scheduler installed, you'll need [the Pivotal Cloud Foundry job scheduler plugin for the `cf` CLI](https://network.pivotal.io/products/p-scheduler-for-pcf). Once the `cf` CLI plugin is installed, you can create jobs.

```
cf create-job runner RunEndpoint1 ".java-buildpack/open_jdk_jre/bin/java org.springframework.boot.loader.JarLauncher endpoint1"
cf create-job runner RunEndpoint2 ".java-buildpack/open_jdk_jre/bin/java org.springframework.boot.loader.JarLauncher endpoint2"
```

You can run a job manually to verify the job configuration is working:

```
cf run-job RunEndpoint1
```

You can schedule the job using a CRON expression of the following form: `MIN HOUR DAY-OF-MONTH MONTH DAY-OF-WEEK`.

```
cf schedule-job RunEndpoint2 "* * ? * *"