const mongoose = require('mongoose');
const { v4: uuidv4 } = require('uuid');

const userSchema = new mongoose.Schema({
    username: { type: String, required: true, unique: true },
    password: { type: String, required: true },
    status: { type: String, enum: ['authorized', 'unauthorized', 'unauthenticated']},
    identifier: { type: String, unique: true, default: () => uuidv4() },
});

module.exports = mongoose.model('User', userSchema);