# DPE University Training

<p align="left">
<img width="10%" height="10%" src="https://user-images.githubusercontent.com/120980/174325546-8558160b-7f16-42cb-af0f-511849f22ebc.png">
</p>

## Introduction to Gradle for Build Engineers Exercise

This is a hands-on exercise to go along with the
[Introduction to Gradle for Build Engineers](https://dpeuniversity.gradle.com/app/catalog)
training module. In this exercise you will go over the following:

* Authoring a task type in buildSrc
* Creating a task using the task type
* Using inferred task dependency

---
## Prerequisites

* Finished going through the relevant sections in the training course
* JDK 1.8+ and recent version of Gradle build tool installed
    * https://gradle.org/install/
* Basic experience with JVM software development

---
## Step 1 - Author Basic Task Type and Custom Task

[Documentation reference](https://docs.gradle.org/current/userguide/implementing_custom_tasks.html#header)

1. Open the Gradle project in this repository in an editor of your choice
2. Open the file `CheckFileSize` in `buildSrc`
3. Create an `abstract` class called `CheckFileSize` that extends `DefaultTask`
4. Add a function annotated with `@TaskAction`
5. In it, use `logger.lifecycle` to print `hello`
6. Open the file `app/build.gradle.kts`
7. Use the `register` method to add a task called `validateOutputSize` using the `CheckFileSize` task type
8. Run the `validateOutputSize` task and see the `hello` output

[Solution reference](solutions/step1)

---
## Step 2 - Inputs and Validating

[Documentation reference](https://docs.gradle.org/current/userguide/implementing_custom_tasks.html#header)

1. Open the file `CheckFileSize` in `buildSrc`
2. Add an input using annotationg `@get:Input` called `maxFileSizeInBytes` that should be of type `Property<Long>`
3. Add an input file using annotationg `@get:InputFile` called `fileToValidate` that should be of type `RegularFileProperty`
4. In the action, put the following contents:

```kotlin
@TaskAction
fun action() {
    val file = File(fileToValidate.get().asFile.absolutePath)
    val length : Long = file.length()

    if (length > maxFileSizeInBytes.get()) {
        throw GradleException("Input file: ${file.absolutePath} is too large")
    } else {
        logger.lifecycle("Input file: ${file.absolutePath} is acceptable size")
    }
}
```

5. Open the file `app/build.gradle.kts`
6. Make the `validateOutputSize` depend on the task `createLargeOutputFile`
7. Set `maxFileSizeInBytes` to 1024L * 1024L * 1L:

```kotlin
maxFileSizeInBytes = 1024L * 1024L * 1L
```

8. Use the `layout` variable to set `fileToValidate` to the file `outputFile.pkg` in the build directory
9. Run the `validateOutputSize` task and see the failure output

[Solution reference](solutions/step2)

---
## Step 3 - Using Inferred Task Dependency

[Documentation reference](https://docs.gradle.org/current/userguide/implementing_custom_tasks.html#header)

1. Open the file `app/build.gradle.kts`
2. Add a `group` and `description` to the task `validateOutputSize`
3. Comment out the `dependsOn` line
4. Set `fileToValidate` to the output of the `createLargeOutputFile` task, hint:

```kotlin
// Use this as a starting point, you still need to get the output
tasks.named<GenerateRandomFile>("createLargeOutputFile").get()
```

[Solution reference](solutions/step3)