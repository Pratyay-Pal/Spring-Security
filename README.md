# Spring-Security
Demo Project testing different Spring Security features in both backend and frontend.

Features present:

- Multiple endpoints. Some are secured through different means.
- Using RequestMatchers to separately make few endpoints secured while publicly exposing the rest(WebSecurityConfig.class)
- InMemoryUserDetailsManager to create on the fly user creation within code(WebSecurityConfig.class). Commented out.
- UserDetailsService using default Spring User Schema for the Database(WebSecurityConfig.class). Commented out.
- UserDetailsService using custom implementation(custom_users_UserDetailService.class) of User Details. Schemas in /resources/sql. Uses Spring Data JPA for CRUD operations.
- New user registration through an endpoint.
- Password encoders using both NoOpPasswordEncoder and BCryptPasswordEncoder. Former is commented out.
- Custom implementation of AuthenticationProvider(CustomAuthenticationProvider.class) which controls custom logic during user login.
- Frontend React Project in /FRONTEND.- 
- Lot of unnecessary Logging of credentials

Endpoints:

- /insecure : Public
- /registerNewUser : Public
- /securedByLogin : Accessible after Login

