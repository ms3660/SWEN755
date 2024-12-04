const express = require('express');
const { register, login, logout, getUser } = require('../controllers/authController');
const { isAuthenticated } = require('../middleware/authMiddleware');

const router = express.Router();

router.post('/login', login);
router.post('/logout', logout);
router.get('/user', isAuthenticated, getUser);

module.exports = router;
