### **Project Name**

Secure Session Management Testing

------

### **Description**

This project implements a secure session management system for a web application and includes test cases to verify the effectiveness of the secure session tactics. The tests focus on identifying and addressing architecture breakers, demonstrating how secure design decisions impact the application's functionality.

------

### **Features**

- **Session Management**: Secure handling of user sessions.
- **Authentication & Authorization**: User authentication and role-based access control.
- **Testing**: Automated test cases for session fixation and session timeout scenarios.

------

### **Setup Instructions**

1. **Install Dependencies**:

   - Ensure Node.js and npm are installed.

   - Run the following command to install project dependencies:

     ```
     npm install
     ```

2. **Setup Environment Variables**:

   - Create a .env file in the project root with the following:

     ```
     DB_URI=mongodb://localhost:27017/secure-session-db
     SESSION_SECRET=your_secret_key
     COOKIE_SECRET=your_cookie_secret
     ```

3. **Run the Application**:

   - Start MongoDB locally:

     ```
     mongod
     ```

   - Start the server:

     ```
     npm start
     ```

4. **Run Tests**:

   - To run all tests:

     ```
     npm test
     ```

   - Alternatively, run specific test files:

     ```
     npx mocha tests/sessionFixationTest.js --timeout 5000
     npx mocha tests/sessionTimeoutTest.js --timeout 5000
     ```

------

### **Frameworks and Libraries**

#### **Backend**

- **Express.js**: Web framework for building REST APIs.
- **Mongoose**: MongoDB object modeling for Node.js.
- **Express-Session**: Middleware for session management.
- **Cookie-Parser**: Middleware for parsing cookies.

#### **Testing**

- **Mocha**: Test framework.
- **Chai**: Assertion library for writing tests.
- **Chai-HTTP**: HTTP integration for testing REST APIs.

------

### **Endpoints**

#### **Authentication**

- `POST /api/auth/login`: Log in a user.
- `POST /api/auth/logout`: Log out a user.
- `GET /api/auth/user`: Fetch the logged-in user's data.

#### **Random Numbers**

- `POST /api/random/save`: Save a random number.
- `GET /api/random/last`: Retrieve the last saved random number.