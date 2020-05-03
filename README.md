# monodiff-example-multi-project

This is an example project to use [monodiff](https://github.com/orangain/monodiff) with Gradle multi-project build. It is worth mentioning that monodiff itself does not depends on Gradle.

In this example, there are four subprojects:

* [apps/account-app](https://github.com/orangain/monodiff-example-multi-project/tree/master/apps/account-app)
* [apps/inventory-app](https://github.com/orangain/monodiff-example-multi-project/tree/master/apps/inventory-app)
* [libs/greeter](https://github.com/orangain/monodiff-example-multi-project/tree/master/libs/greeter)
* [libs/profile](https://github.com/orangain/monodiff-example-multi-project/tree/master/libs/profile)

They have the following dependency:

![Both apps/account-app and apps/inventory-app depend on libs/profile and only apps/account-app depends on libs/greeter](https://github.com/orangain/monodiff-example-multi-project/raw/master/docs/deps.svg)

This dependency is declared in `build.gradle.kts` of each app as following:

```kts
dependencies {
    implementation(project(":libs:greeter"))
    implementation(project(":libs:profile"))
    // ...
}
```

Aside from this, [monodiff.json](https://github.com/orangain/monodiff-example-multi-project/tree/master/monodiff.json) declares dependency for monodiff.

```json
{
  "apps/account-app": {
    "deps": ["build.gradle.kts", "settings.gradle.kts", "libs/greeter", "libs/profile"]
  },
  "apps/inventory-app": {
    "deps": ["build.gradle.kts", "settings.gradle.kts", "libs/profile"]
  }
}
```

Each key, e.g. `apps/account-app`, represents directory to be rebuilt when its dependencies are changed.

`deps` represents dependencies. Each item is a path to a file or directory. When the file or a file in the directory is changed, a build for the key's directory will be triggered. A path in the `deps` should be relative to the root of Git repository.

In monodiff.json, only apps are listed and libs are not. This is because monodiff is used to detect affected roots of dependency. When an app is built, depending libs will be automatically built thanks to Gradle's dependency management system.
