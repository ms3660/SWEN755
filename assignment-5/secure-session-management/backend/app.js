const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
const dotenv = require('dotenv');
const cookieParser = require('cookie-parser');
const session = require('express-session');
const authRoutes = require('./routes/authRoutes');
const randomNumberRoutes = require('./routes/randomNumberRoutes');

dotenv.config();
const app = express();

// CORS configuration
app.use(cors({
    origin: 'http://localhost:3000',
    credentials: true,
    methods: ['GET', 'POST', 'PUT', 'DELETE', 'OPTIONS'],
    allowedHeaders: ['Content-Type', 'Authorization', 'Cookie'],
    exposedHeaders: ['Set-Cookie']
}));

app.use(express.json());
app.use(cookieParser(process.env.COOKIE_SECRET));

// Session configuration with proper timeout settings
app.use(session({
    secret: process.env.SESSION_SECRET || 'app-secret-key',
    resave: true,
    saveUninitialized: false,
    cookie: {
        httpOnly: true,
        secure: false, // Set to true in production
        sameSite: 'lax',
        maxAge: 3000 // 3 seconds for testing purposes
    },
    name: 'sessionId'
}));

// Routes
app.use('/api/auth', authRoutes);
app.use('/api/random', randomNumberRoutes);

// MongoDB connection
mongoose.connect(process.env.DB_URI)
    .then(() => console.log('Connected to MongoDB'))
    .catch(err => console.error('MongoDB connection error:', err));

const PORT = process.env.PORT || 5000;
app.listen(PORT, () => console.log(`Server running on port ${PORT}`));

module.exports = app;
