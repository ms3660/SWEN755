
# Architecture Breakers Documentation

## Four Architecture Breakers

### 1. Session Fixation
- **Description**: The session ID is not regenerated after login, making the system vulnerable to session fixation attacks.
- **Example**: In `authController.js`, the session ID remains unchanged after login.
- **Mitigation**: Regenerate the session ID during login to prevent attacks.

### 2. Session Timeout Mismanagement
- **Description**: Sessions do not expire after inactivity, increasing the risk of unauthorized access.
- **Example**: In `app.js`, missing or improperly configured `maxAge` allows sessions to persist indefinitely.
- **Mitigation**: Configure session cookies with an appropriate timeout (e.g., 30 minutes of inactivity).

### 3. Unencrypted Session Data
- **Description**: Sensitive session data (e.g., user roles, identifiers) is stored in plaintext.
- **Example**: `req.session.user` contains plaintext information.
- **Mitigation**: Avoid storing sensitive information directly in sessions. Use server-side secure stores.

### 4. Weak Cookie Security
- **Description**: Cookies are not marked `Secure` or `HttpOnly`, making them vulnerable to interception.
- **Example**: In `app.js`, the cookie configuration lacks `secure: true`.
- **Mitigation**: Use secure cookies (`secure: true`, `httpOnly: true`) in production.

---

## Tested Breakers

### 1. Session Fixation
- **Test Name**: `should regenerate session ID after login`.
- **Result**: The test fails because the session ID remains unchanged due to the introduced breaker.
- **Mitigation**: Add session regeneration logic in `authController.js` using `req.session.regenerate`.

### 2. Session Timeout Mismanagement
- **Test Name**: `should expire session after timeout`.
- **Result**: The test passes because the session cookie is configured with a 3-second timeout (`maxAge`).
- **Fix Location**: Updated session configuration in `app.js`.

---

## How to Use the Documentation
- The README provides a high-level overview of the project setup, frameworks used, and how to run the code.
- The architecture breakers are documented in detail with examples and mitigations.
- Both tested breakers (session fixation and session timeout) are explained, including test results and fixes.
