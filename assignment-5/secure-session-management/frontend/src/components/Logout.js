import React from 'react';
import axios from 'axios';

function Logout({ setUser }) {
    const handleLogout = async () => {
        try {
            await axios.post('http://localhost:5000/api/auth/logout');
            setUser(null);
            localStorage.removeItem('user');
        } catch (error) {
            console.error('Logout failed:', error);
            alert('An error occurred while logging out. Please try again.');
        }
    };

    return <button onClick={handleLogout}>Logout</button>;
}

export default Logout;
