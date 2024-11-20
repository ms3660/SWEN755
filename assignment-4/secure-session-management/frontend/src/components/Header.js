import React from 'react';
import Logout from './Logout';

function Header({ user, setUser }) {
    return (
        <div className="header">
            <h1>Secure Session Management</h1>
            {user && (
                <>
                    <span>Welcome, {user.username}!</span>
                    <span>Identifier: {user.identifier}</span>
                    <Logout setUser={setUser} />
                </>
            )}
        </div>
    );
}

export default Header;