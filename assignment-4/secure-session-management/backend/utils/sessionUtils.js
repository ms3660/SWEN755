const crypto = require('crypto');

const sessions = {};

const createSession = (userId) => {
    const sessionId = crypto.randomBytes(16).toString('hex');
    sessions[sessionId] = userId;
    return sessionId;
};

const getUserFromSession = (sessionId) => sessions[sessionId];

const sessionHandler = (req, res, next) => {
    const sessionId = req.cookies.sessionId;
    if (sessionId && sessions[sessionId]) {
        req.userId = sessions[sessionId];
    }
    next();
};

module.exports = { createSession, getUserFromSession, sessionHandler };
