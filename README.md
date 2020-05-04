# monodiff-example-multi-project

This is an example project to use [monodiff](https://github.com/orangain/monodiff) with Gradle multi-project build. It is worth mentioning that monodiff itself does not depends on Gradle.

## Description

In this example, there are four subprojects:

* [apps/account-app](https://github.com/orangain/monodiff-example-multi-project/tree/master/apps/account-app)
* [apps/inventory-app](https://github.com/orangain/monodiff-example-multi-project/tree/master/apps/inventory-app)
* [libs/greeter](https://github.com/orangain/monodiff-example-multi-project/tree/master/libs/greeter)
* [libs/profile](https://github.com/orangain/monodiff-example-multi-project/tree/master/libs/profile)

They have the following dependency:

![Both apps/account-app and apps/inventory-app depend on libs/profile and only apps/account-app depends on libs/greeter](https://github.com/orangain/monodiff-example-multi-project/raw/master/docs/deps.svg)

By using monodiff, only changed apps can be built as follows:

* When the `apps/inventory-app` is [changed](https://github.com/orangain/monodiff-example-multi-project/pull/1), only the `apps/inventory-app` will be built.
  ![CI result](https://github.com/orangain/monodiff-example-multi-project/raw/master/docs/screenshot1.png)
* When the `libs/greeter` is [changed](https://github.com/orangain/monodiff-example-multi-project/pull/2), only the `apps/account-app` will be built.
  ![CI result](https://github.com/orangain/monodiff-example-multi-project/raw/master/docs/screenshot2.png)
* When the `libs/profile` is [changed](https://github.com/orangain/monodiff-example-multi-project/pull/3), both the `apps/account-app` and the `apps/inventory-app` will be built.
  ![CI result](https://github.com/orangain/monodiff-example-multi-project/raw/master/docs/screenshot3.png)


## Details

This dependency is declared in `build.gradle.kts` of each app.

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

Each key, e.g. `apps/account-app`, represents a directory to be rebuilt when its dependencies are changed. For example, `apps/account-app` will be rebuilt when `build.gradle.kts`, `settings.gradle.kts` or files under `apps/account-app`, `libs/greeter` or `libs/profile` are changed.

In this `monodiff.json`, only apps are listed and libs are not listed. This is because what we want to detect here is affected roots of dependency. When the affected app is built, depending libs will be automatically built by the Gradle's dependency management system.

By default, `monodiff` command outputs affected sub-projects as follows:

```
$ git diff --name-only origin/master | monodiff
apps/account-app
apps/inventory-app
```

When you use Gradle multi-project build, there are some useful options. The output of the following command can be used as an argument of `gradle` command to build only affected sub-projects.

```
$ git diff --name-only origin/master | monodiff --prefix ":" --separator ":" --suffix :build
:apps:account-app:build
:apps:inventory-app:build
```
