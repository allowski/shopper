# Shopper project

An open source ecommerce platform, created with the goal of showing off skills, and therefore create a new concept.

Disclaimer: This project is currently in alpha. Please do not consider using this project in production.

### Main functions

- Product listing (/api/products)
- Checkout (/api/checkout)
- Register (/api/register)

### Coding guidelines

Before start writing code, you must read this guidelines:

- This project uses lombok libraries, so avoid generating unnecessary code
- Developt test-driven, always write the tests first before implementing a new set of functions.
- Expose only needed enpoints using security configurations.
- Any PR that doesn't follow this guideline will be rejected.
- Before submiting a PR ALWAYS run the tests.

### Implementation

This project uses the following dependencies:
- Gradle 6.2
- Java 8+
- SpringBoot + JPA + Data + Hibernate
- H2 In-memmory db
- Misc: lombok
