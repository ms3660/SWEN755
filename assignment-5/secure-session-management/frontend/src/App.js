import React, { useState, useEffect } from 'react';
import './styles/App.css';
import Login from './components/Login';
import Task from './components/Task';
import Header from './components/Header';

function App() {
    const [user, setUser] = useState(null);

    useEffect(() => {
        const loggedInUser = localStorage.getItem('user');
        if (loggedInUser) setUser(JSON.parse(loggedInUser));
    }, []);

    return (
        <div className="app-container">
            <Header user={user} setUser={setUser} />
            {user ? (
                <Task user={user} />
            ) : (
                <Login setUser={setUser} />
            )}
        </div>
    );
}

export default App;