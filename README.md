# monodiff-example-multi-project

This is an example project to use [monodiff](https://github.com/orangain/monodiff) with Gradle multi-project build. It is worth mentioning that monodiff itself does not depends on Gradle.

In this example, there are four subprojects:

* [apps/account-service](https://github.com/orangain/monodiff-example-multi-project/tree/master/apps/account-service)
* [apps/inventory-service](https://github.com/orangain/monodiff-example-multi-project/tree/master/apps/inventory-service)
* [libs/greeter](https://github.com/orangain/monodiff-example-multi-project/tree/master/libs/greeter)
* [libs/profile](https://github.com/orangain/monodiff-example-multi-project/tree/master/libs/profile)

They have the following dependency:

![Both apps/account-service and apps/inventory-service depend on libs/profile and only apps/account-service depends on libs/greeter](https://github.com/orangain/monodiff-example-multi-project/raw/master/docs/deps.svg)

This dependency is declared in `build.gradle.kts` of each app as following:

```kts
dependencies {
    implementation(project(":libs:greeter"))
    implementation(project(":libs:profile"))
    // ...
}
```

Aside from this, [monodiff.json](https://github.com/orangain/monodiff-example-multi-project/tree/master/monodiff.json) declares dependency for monodiff. In monodiff.json, only apps are listed. This is because monodiff is used to detect affected roots of dependency.

```json
{
  "apps/account-service": {
    "deps": ["build.gradle.kts", "settings.gradle.kts", "libs/greeter", "libs/profile"]
  },
  "apps/inventory-service": {
    "deps": ["build.gradle.kts", "settings.gradle.kts", "libs/profile"]
  }
}
```
