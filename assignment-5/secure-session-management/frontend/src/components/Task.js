import React, { useState, useEffect } from 'react';
import axios from 'axios';

function Task({ user }) {
    const [taskMessage, setTaskMessage] = useState('');
    const [randomNumber, setRandomNumber] = useState(null);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchRandomNumber = async () => {
            try {
                // Handle user status logic
                if (user.status === 'unauthenticated') {
                    setTaskMessage('Your account has not been authenticated.');
                } else if (user.status === 'unauthorized') {
                    const response = await axios.get('http://localhost:5000/api/random/last');
                    const { number } = response.data;
                    setTaskMessage(
                        'Welcome, User!'
                    );
                    setRandomNumber(number);
                } else if (user.status === 'authorized') {
                    setTaskMessage('Welcome, Admin! You can generate a random number.');
                }
                setError(null);
            } catch (err) {
                setError(err.response?.data?.message || 'An error occurred while fetching data.');
            }
        };

        fetchRandomNumber();
    }, [user.status]);

    const generateRandomNumber = async () => {
        try {
            const number = Math.floor(Math.random() * 100); // Random number between 0 and 99
            await axios.post('http://localhost:5000/api/random/save', { number }); 
            setRandomNumber(number); 
            setTaskMessage(`Random number ${number} generated and saved successfully.`);
            setError(null);
        } catch (err) {
            console.error('Error saving random number:', err);
            setError('Failed to save the random number. Please try again.');
        }
    };

    return (
        <div className="task-container">
            {error && <div className="error-message">{error}</div>}

            <h3>{taskMessage}</h3>

            {user.status === 'authorized' && (
                <div>
                    <button onClick={generateRandomNumber}>Generate Random Number</button>
                    {randomNumber !== null && <p>Random Number: {randomNumber}</p>}
                </div>
            )}

            {user.status === 'unauthorized' && (
                <p>Last generated random number: {randomNumber !== null ? randomNumber : 'None'}</p>
            )}
        </div>
    );
}

export default Task;