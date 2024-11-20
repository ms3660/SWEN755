const RandomNumber = require('../models/RandomNumber');

exports.saveRandomNumber = async (req, res) => {
    try {
        const { number } = req.body;

        if (number === undefined) {
            return res.status(400).json({ message: 'Random number is required.' });
        }

        const randomNumber = new RandomNumber({ number });
        await randomNumber.save();

        res.status(201).json({ message: 'Random number saved successfully.' });
    } catch (error) {
        console.error('Error saving random number:', error);
        res.status(500).json({ message: 'Failed to save the random number.' });
    }
};

exports.getLastRandomNumber = async (req, res) => {
    try {
        const lastNumber = await RandomNumber.findOne().sort({ createdAt: -1 });

        if (!lastNumber) {
            return res.status(404).json({ message: 'No random number found.' });
        }

        res.status(200).json({ number: lastNumber.number });
    } catch (error) {
        console.error('Error fetching the last random number:', error);
        res.status(500).json({ message: 'Failed to fetch the last random number.' });
    }
};
