const mongoose = require('mongoose');
const User = require('./models/User');
const dotenv = require('dotenv');

dotenv.config();

const seedUsers = async () => {
    const users = [
        { username: 'Authorized', password: 'authpass', status: 'authorized'  },
        { username: 'Unauthorized', password: 'unauthpass', status: 'unauthorized' },
        { username: 'Unauthenticated', password: 'authenticate', status: 'unauthenticated' },
    ];

    try {
        await mongoose.connect(process.env.DB_URI, { useNewUrlParser: true, useUnifiedTopology: true });

        console.log('Connected to MongoDB');
        
        await User.deleteMany();

        await User.insertMany(users);

        console.log('Users seeded successfully');
        mongoose.connection.close();
    } catch (err) {
        console.error('Error seeding users:', err);
        mongoose.connection.close();
    }
};

seedUsers();