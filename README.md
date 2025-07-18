<table>
   <tr>
   <td>

   # 📱 Github & Good Practices App
   This project is a sample Android application built to showcase my knowledge of Android development.
   It's designed to be a living project, continuously updated to reflect the latest trends and technologies in the Android ecosystem.
   
   The application uses the [GitHub GraphQL API](https://docs.github.com/en/graphql) to search for and display information about repositories.
   ## 🚀 Key Technologies
   
   This project is built with the following technologies:

*   **Kotlin**: The primary programming language for the application.
*   **Jetpack Compose**: For building the user interface.
*   **Koin**: For dependency injection.
*   **Apollo**  GraphQL: For interacting with the GitHub GraphQL API.
*   **Coil**: For image loading.
*   **Timber**: For logging.
*   **Detekt & Spotless**: For code quality.
*   **Danger** (Work In Progress): For giving PR insight and niceties

   </td>
   <td>
   <img width="90%"  alt="image" src="https://github.com/user-attachments/assets/81cca498-c64b-40d4-9941-21c3169099e6" />
   </td>
   </tr>
</table>

## 🏁 Getting Started

To get started with this project, you'll currently need to add a GitHub personal token to the `gradle.properties`.

### Create a Personal Access Token

First, you need to create a Classic GitHub Personal Access Token. Here's how:

1. **Log in to your GitHub account, Navigate to Settings, Go to [Developer settings](https://github.com/settings/tokens)**
2. **Generate a new token:** Click **Generate new token** and select **Generate new token (classic)**.
3. **Configure your token & generate it:**
    *   **Scopes:** For this project, you'll need to select the `repo` scope to allow access to repositories.

Next, add the token to your `gradle.properties` file:

1.  Open `./gradle/gradle.properties`.
2.  Add the following line to the file, replacing `YOUR_API_KEY` with your actual GitHub personal token:

```
github.token=YOUR_API_KEY
```

**Note**: In the future, this manual setup will be replaced by a remote server providing a JWT for issuing access credentials. This will remove the need for developers to configure a personal GitHub token.

## 🛠️ Building

To build the project, you can use the following command:

```bash
./gradlew build
```

## 🏗️ Project Structure

The project is divided into several modules, each with a specific responsibility.

```mermaid
graph TD
    subgraph DataModules["Data Modules"]
        data["data"]
        data-apollo["data-apollo"]
    end

    subgraph DomainModules["Domain Modules"]
        domain["domain"]
    end

    subgraph CoreModules["Core Modules"]
        core-viewmodel["core:viewmodel"]
        core-ui-theme["core:ui:theme"]
        core-ui-navigation["core:ui:navigation"]
        core-ui-component["core:ui:component"]
    end

    subgraph UIModules["UI Modules"]
        repo["repo"]
        any-other-ui-modules["any-other-ui-modules"]
    end

    app["app"]
    domain-contract["domain-contract"]
%% Connections
    app --> UIModules
    app --> DomainModules
    app --> DataModules
%% UI Modules connections
    UIModules --> CoreModules
    UIModules --> DomainModules
%% Domain contract connections
    DomainModules --> domain-contract
    DataModules --> domain-contract
    data --> data-apollo
```

### Module Descriptions

#### Data Modules

*   **data**: This module is responsible for orchestrating data from various sources, such as the network or a local database. It uses the `data-apollo` module to fetch data from the GitHub API.
*   **data-apollo**: This module provides a type-safe GraphQL client for the GitHub API using Apollo.

#### Domain Modules

*   **domain**: This module contains the business logic of the application. It defines the use cases and is interacted by the data layer through the `domain-contract` module.
*   **domain-contract**: This module defines the contracts (interfaces) for the domain layer.

#### Core Modules

*   **core:viewmodel**: This module provides base `ViewModel` implementations to be used by the UI modules.
*   **core:ui:theme**: This module contains the application's theme and styling information.
*   **core:ui:navigation**: This module provides the navigation component for the application.
*   **core:ui:component**: This module contains reusable UI components that are not specific to any particular feature.

#### UI Modules

*   **repo**: This module contains the UI for the repository search feature.

#### App

*   **app**: This is the main application module that brings everything together.

## 🧩 Convention Plugins

This project uses convention plugins to simplify and standardize build configurations across modules. Instead of repeating the same setup in every `build.gradle.kts` file, we define it once in a plugin and apply it where needed. This makes our build scripts cleaner, more consistent, and easier to maintain.

The convention plugins are located [here](config/build-logic/convention) and are:

*   `AndroidConfigConventionPlugin`: Applies common Android configurations to all modules.
*   `AndroidDependenciesConventionPlugin`: Provides common dependencies to all modules.
*   `AndroidComposeConventionPlugin`: Configures Jetpack Compose for UI modules.
*   `AndroidUiConventionPlugin`: A combination of the above plugins, specifically for UI modules.
*   `CodeQualityConventionPlugin`: Configures Detekt and Spotless for code quality checks.

## ✨ Code Quality

This project uses [Detekt](https://detekt.dev/) and [Spotless](https://github.com/diffplug/spotless) to enforce code quality and style. The CI/CD pipeline is configured to run a code quality check on every pull request. **Pull requests will not be merged if the code quality check fails.**

To run all the code quality checks locally, you can use the following command:

```bash
./gradlew codeQualityCheck
```

This command will run `spotlessCheck`, `detekt`, and `lint` on all the modules.

To automatically format the code, you can run the following command:

```bash
./gradlew spotlessApply
```

It is recommended to run this command before pushing any changes to the repository.

## 🤖 CI/CD

This project uses [Bitrise](https://www.bitrise.io/) for CI/CD. The pipeline is configured with two main workflows that run on every pull request:

1.  **Code Quality Check**: This workflow runs the `./gradlew codeQualityCheck` task to ensure that the code adheres to the project's quality standards.
2.  **Unit Tests**: This workflow runs all the unit tests in the project.

A pull request can only be merged if both of these workflows pass successfully.

### Danger

The project is in the process of integrating [Danger](https://danger.systems/ruby/) to provide automated feedback on pull requests. Danger will be run in a Dockerized environment and will post comments on pull requests with information about test results, code style violations, and other useful metrics.

## 📄 License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
