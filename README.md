---- README is still in WIP ----

# TODO

- Unit Tests
- Add Bitrise & adds a Pull Request check step & release apk step

# Project Structure

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

## Module Descriptions

### Data Modules

- **data**: Main data layer implementation: is in charge to orchestrate **data-apollo** and other
  possible data source such as Room
- **data-apollo**: GraphQL client implementation

### Domain Modules

- **domain**: Business logic implementation
- **domain-contract**: Domain layer contracts/interfaces

### Core Modules

- **core:viewmodel**: Base ViewModel implementations
- **core:ui:theme**: UI theming and styling
- **core:ui:navigation**: Navigation components
- **core:ui:component**: Reusable UI components (not particularly related to specific UI)

### UI Modules

- **repo**: Repository related UI (components, screen)

### App

- **app**: Main application module
