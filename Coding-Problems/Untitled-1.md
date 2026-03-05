| Step | File                         | What Happens                                                                 |
|------|------------------------------|------------------------------------------------------------------------------|
| 1    | SecurityConfig               | Defines which URLs are public/protected and registers the JWT filter       |
| 2    | JwtAuthenticationFilter      | Intercepts every request, extracts and validates the Bearer token          |
| 3    | AuthController               | Handles `/api/auth/login` POST request and receives user credentials       |
| 4    | AuthService                  | Validates credentials via AuthenticationManager and generates JWT          |
| 5    | UserDetailsServiceImpl       | Loads user from DB for Spring Security to verify authentication            |
| 6    | JwtUtil                      | Signs, verifies, and parses JWT tokens                                     |