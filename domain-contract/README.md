# Domain Contract Module

## Purpose
The Domain Contract module is inspired by the talking we had about Clean Architecture by Uncle Bob. 
It serves as a boundary layer between the domain and data layers in this Clean Architecture implementation. 
It defines interfaces (contracts) that the data layer must implement and that the domain layer depends on, enabling a clean separation of concerns and dependency inversion.

## Key Concepts

### Dependency Inversion
By defining contracts in a separate module, we ensure that:
- The domain layer depends on abstractions (contracts), not concrete implementations
- The data layer implements these contracts, but doesn't dictate the domain's needs
- Both layers depend on the contract module, but the contract doesn't depend on either

### Clean Architecture Benefits
This approach provides several advantages:
- **Testability**: Domain logic can be tested with mock implementations of the contracts
- **Modularity**: Implementations can be swapped without changing the domain logic
- **Separation of Concerns**: Clear boundaries between layers with well-defined responsibilities

### Dependency Injection
The contracts are wired up through dependency injection (using Koin in this project)

## Best Practices
- Keep contracts simple and focused on a single responsibility
- Define contracts based on domain needs, not data layer capabilities
- Use function types for simple operations (like `() -> List<BrandContract>`)