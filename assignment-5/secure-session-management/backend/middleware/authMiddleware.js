const sessionUtils = require('../utils/sessionUtils');

const isAuthenticated = (req, res, next) => {
    if (!req.session || !req.session.user) {
        return res.status(401).json({ message: 'Not authenticated' });
    }
    next();
};

module.exports = { isAuthenticated };
