const express = require('express');
const { saveRandomNumber, getLastRandomNumber } = require('../controllers/randomNumberController');

const router = express.Router();

router.post('/save', saveRandomNumber);
router.get('/last', getLastRandomNumber);

module.exports = router;
