const mongoose = require('mongoose');

const randomNumberSchema = new mongoose.Schema({
    number: { type: Number, required: true },
    createdAt: { type: Date, default: Date.now }
});

module.exports = mongoose.model('RandomNumber', randomNumberSchema);
