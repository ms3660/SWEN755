# Random Number Generator App

## Overview
This application provides a role-based random number generation and retrieval system. It distinguishes between three user roles:
- Unauthenticated Users: These users cannot interact with the app until they are authenticated.
- Unauthorized Users: These users can only view the last generated random number.
- Authorized Users (Admins): These users can generate and save random numbers in the backend, with the ability to view the latest generated number as well.
- The backend is built using Node.js and MongoDB, while the frontend uses React.js. The app ensures seamless communication between the backend and frontend through APIs, with session management and role-based access control.

## Features
- Role-Based Access Control: User roles determine the functionality available (view-only vs. number generation).
- Random Number Generation: Authorized users can generate and save a random number to the backend.
- Last Generated Number Retrieval: Unauthorized users can view the last generated random number from the backend.
- Error Handling: Displays user-friendly error messages for any backend or network issues.

## Prerequisites
- Node.js: Ensure that Node.js (v16 or higher) is installed on your system.
- MongoDB: A running instance of MongoDB is required. Use a cloud-based MongoDB service (e.g., MongoDB Atlas) or a local installation.
- npm: Node Package Manager is required to install dependencies.


### Running the Application
Start Backend:
```
cd assignment-4\secure-session-management\backend
npm install
npm start
```

Start Frontend:
```
cd assignment-4\secure-session-management\frontend
npm install
npm start
```

Open your browser and navigate to http://localhost:3000 to use the app.

### Tech stack
Frontend: React.js
Backend: Node.js, Express.js
Database: MongoDB
Authentication: Session-based using Express-Session
