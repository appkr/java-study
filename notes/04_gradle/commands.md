## Gradle Commands

- https://www.youtube.com/watch?v=vxKN2VSqTMg&list=PLXY8okVWvwZ2dgXK16SjzDyHgqIDtXWCB

```bash
$ pwd
/Users/appkr/workspace/spring-study/cloud-stream3
```

```bash
$ ./gradlew tasks

> Task :tasks

------------------------------------------------------------
All tasks runnable from root project
------------------------------------------------------------

Application tasks
-----------------
bootRun - Runs this project as a Spring Boot application.

Build tasks
-----------
assemble - Assembles the outputs of this project.
bootJar - Assembles an executable jar archive containing the main classes and their dependencies.
build - Assembles and tests this project.
buildDependents - Assembles and tests this project and all projects that depend on it.
buildNeeded - Assembles and tests this project and all projects it depends on.
classes - Assembles main classes.
clean - Deletes the build directory.
jar - Assembles a jar archive containing the main classes.
testClasses - Assembles test classes.

Build Setup tasks
-----------------
init - Initializes a new Gradle build.
wrapper - Generates Gradle wrapper files.

Documentation tasks
-------------------
javadoc - Generates Javadoc API documentation for the main source code.

Help tasks
----------
buildEnvironment - Displays all buildscript dependencies declared in root project 'cloud-stream3'.
components - Displays the components produced by root project 'cloud-stream3'. [incubating]
dependencies - Displays all dependencies declared in root project 'cloud-stream3'.
dependencyInsight - Displays the insight into a specific dependency in root project 'cloud-stream3'.
dependencyManagement - Displays the dependency management declared in project ':sink'.
dependentComponents - Displays the dependent components of components in root project 'cloud-stream3'. [incubating]
help - Displays a help message.
model - Displays the configuration model of root project 'cloud-stream3'. [incubating]
projects - Displays the sub-projects of root project 'cloud-stream3'.
properties - Displays the properties of root project 'cloud-stream3'.
tasks - Displays the tasks runnable from root project 'cloud-stream3' (some of the displayed tasks may belong to subprojects).

Verification tasks
------------------
check - Runs all checks.
test - Runs the unit tests.

To see all tasks and more detail, run gradlew tasks --all

To see more detail about a task, run gradlew help --task <task>

BUILD SUCCESSFUL in 0s
1 actionable task: 1 executed
```

```bash
$ ./gradlew buildEnvironment

> Task :buildEnvironment

------------------------------------------------------------
Root project
------------------------------------------------------------

classpath
\--- org.springframework.boot:spring-boot-gradle-plugin:2.1.5.RELEASE
     +--- org.springframework.boot:spring-boot-loader-tools:2.1.5.RELEASE
     |    +--- org.springframework:spring-core:5.1.7.RELEASE
     |    |    \--- org.springframework:spring-jcl:5.1.7.RELEASE
     |    \--- org.apache.commons:commons-compress:1.18
     +--- io.spring.gradle:dependency-management-plugin:1.0.6.RELEASE
     \--- org.apache.commons:commons-compress:1.18

(*) - dependencies omitted (listed previously)

A web-based, searchable dependency report is available by adding the --scan option.

BUILD SUCCESSFUL in 0s
1 actionable task: 1 executed

```

```bash
$ ./gradlew properties
> Task :properties

------------------------------------------------------------
Root project
------------------------------------------------------------

allprojects: [root project 'cloud-stream3', project ':sink', project ':source']
ant: org.gradle.api.internal.project.DefaultAntBuilder@47600ed1
antBuilderFactory: org.gradle.api.internal.project.DefaultAntBuilderFactory@5d1b15fc
artifacts: org.gradle.api.internal.artifacts.dsl.DefaultArtifactHandler_Decorated@f41d83d
asDynamicObject: DynamicObject for root project 'cloud-stream3'
baseClassLoaderScope: org.gradle.api.internal.initialization.DefaultClassLoaderScope@1b35f5f2
buildDir: /Users/appkr/workspace/spring-study/cloud-stream3/build
buildFile: /Users/appkr/workspace/spring-study/cloud-stream3/build.gradle
buildPath: :
buildScriptSource: org.gradle.groovy.scripts.TextResourceScriptSource@5c6466c3
buildscript: org.gradle.api.internal.initialization.DefaultScriptHandler@59552c46
childProjects: {sink=project ':sink', source=project ':source'}
class: class org.gradle.api.internal.project.DefaultProject_Decorated
classLoaderScope: org.gradle.api.internal.initialization.DefaultClassLoaderScope@58d48761
components: SoftwareComponentInternal set
configurationActions: org.gradle.configuration.project.DefaultProjectConfigurationActionContainer@6cfa63ec
configurationTargetIdentifier: org.gradle.configuration.ConfigurationTargetIdentifier$1@13d742ce
configurations: configuration container
convention: org.gradle.api.internal.plugins.DefaultConvention@63906bee
defaultTasks: []
deferredProjectConfiguration: org.gradle.api.internal.project.DeferredProjectConfiguration@1f030772
dependencies: org.gradle.api.internal.artifacts.dsl.dependencies.DefaultDependencyHandler_Decorated@2059e27d
dependencyLocking: org.gradle.internal.locking.DefaultDependencyLockingHandler_Decorated@931058a
depth: 0
description: null
displayName: root project 'cloud-stream3'
ext: org.gradle.api.internal.plugins.DefaultExtraPropertiesExtension@691ed059
extensions: org.gradle.api.internal.plugins.DefaultConvention@63906bee
fileOperations: org.gradle.api.internal.file.DefaultFileOperations@6e21629f
fileResolver: org.gradle.api.internal.file.BaseDirFileResolver@1043f2c
gradle: build 'cloud-stream3'
group:
identityPath: :
inheritedScope: org.gradle.api.internal.ExtensibleDynamicObject$InheritedDynamicObject@9867d3
layout: org.gradle.api.internal.file.DefaultProjectLayout@1f1be381
listenerBuildOperationDecorator: org.gradle.configuration.internal.DefaultListenerBuildOperationDecorator@7acb4f67
logger: org.gradle.internal.logging.slf4j.OutputEventListenerBackedLogger@386d9956
logging: org.gradle.internal.logging.services.DefaultLoggingManager@5a96f24d
modelRegistry: org.gradle.model.internal.registry.DefaultModelRegistry@19f3a033
modelSchemaStore: org.gradle.model.internal.manage.schema.extract.DefaultModelSchemaStore@5c322014
module: org.gradle.api.internal.artifacts.ProjectBackedModule@18bfa614
name: cloud-stream3
normalization: org.gradle.normalization.internal.DefaultInputNormalizationHandler_Decorated@59345f67
objects: org.gradle.api.internal.model.DefaultObjectFactory@79249351
parent: null
parentIdentifier: null
path: :
pluginManager: org.gradle.api.internal.plugins.DefaultPluginManager_Decorated@14cd12a2
plugins: [org.gradle.api.plugins.HelpTasksPlugin@103bda30]
processOperations: org.gradle.api.internal.file.DefaultFileOperations@6e21629f
project: root project 'cloud-stream3'
projectConfigurator: org.gradle.api.internal.project.BuildOperationCrossProjectConfigurator@6b0f7037
projectDir: /Users/appkr/workspace/spring-study/cloud-stream3
projectEvaluationBroadcaster: ProjectEvaluationListener broadcast
projectEvaluator: org.gradle.configuration.project.LifecycleProjectEvaluator@366cc81c
projectPath: :
projectRegistry: org.gradle.api.internal.project.DefaultProjectRegistry@4a489471
properties: {...}
providers: org.gradle.api.internal.provider.DefaultProviderFactory@5c0d67ad
repoPass: 84especezUsewune
repoUser: deploy
repositories: repository container
resourceLoader: org.gradle.internal.resource.transfer.DefaultUriTextResourceLoader@46c2d52
resources: org.gradle.api.internal.resources.DefaultResourceHandler@14727b20
rootDir: /Users/appkr/workspace/spring-study/cloud-stream3
rootProject: root project 'cloud-stream3'
script: false
scriptHandlerFactory: org.gradle.api.internal.initialization.DefaultScriptHandlerFactory@440e7d9a
scriptPluginFactory: org.gradle.configuration.ScriptPluginFactorySelector@4aeb8276
serviceRegistryFactory: org.gradle.internal.service.scopes.ProjectScopeServices$4@2f0830c4
services: ProjectScopeServices
springBootVersion: 2.1.5.RELEASE
springCloudVersion: Greenwich.SR1
standardOutputCapture: org.gradle.internal.logging.services.DefaultLoggingManager@5a96f24d
state: project state 'EXECUTED'
status: release
subprojects: [project ':sink', project ':source']
tasks: task set
version: unspecified

BUILD SUCCESSFUL in 0s
1 actionable task: 1 executed
```