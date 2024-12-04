const User = require('../models/User');

exports.register = async (req, res) => {
    try {
        const { username, password } = req.body;
        const user = new User({ username, password });
        await user.save();
        res.status(201).json({ message: 'User created', userId: user._id });
    } catch (error) {
        console.error('Registration error:', error);
        res.status(500).json({ message: 'Registration failed' });
    }
};

exports.login = async (req, res) => {
    try {
        const { username, password } = req.body;
        const user = await User.findOne({ username, password });

        if (!user) {
            return res.status(401).json({ message: 'Invalid credentials' });
        }

        // Breaker: Reuse session ID without regenerating it
        req.session.user = { id: user._id, username: user.username };
        res.json({ 
            message: 'Logged in successfully',
            userId: user._id,
            sessionId: req.sessionID
        });
    } catch (error) {
        console.error('Login error:', error);
        res.status(500).json({ message: 'Login failed' });
    }
};

exports.logout = (req, res) => {
    req.session.destroy((err) => {
        if (err) {
            return res.status(500).json({ message: 'Logout failed' });
        }
        res.clearCookie('connect.sid');
        res.json({ message: 'Logged out successfully' });
    });
};

exports.getUser = async (req, res) => {
    const identifier = req.headers['client-identifier'];  
    if (!identifier) {
        return res.status(401).json({ message: 'Identifier missing' });
    }
    
    const user = await User.findOne({ identifier });
    if (!user) {
        return res.status(401).json({ message: 'User not found' });
    }

    if (user.status === 'unauthenticated') {
        return res.status(403).json({ message: 'User is not authenticated' });
    }

    if (user.role === 'admin') {
        return res.json({ message: 'Admin privileges', user });
    }

    return res.json({ message: 'User authorized', user });
};
