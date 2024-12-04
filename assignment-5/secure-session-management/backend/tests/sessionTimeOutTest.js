const chai = require('chai');
const chaiHttp = require('chai-http');
const app = require('../app');
const { expect } = chai;

chai.use(chaiHttp);

describe('Session Timeout Test', () => {
    it('should expire session after timeout', async () => {
        const agent = chai.request.agent(app);

        // Step 1: Log in to create a session
        await agent.post('/api/auth/login').send({
            username: 'testuser',
            password: 'testpassword',
        });

        // Step 2: Wait for the session to expire
        await new Promise((resolve) => setTimeout(resolve, 4000)); // Wait 4 seconds (longer than the 3s maxAge)

        // Step 3: Try to access a protected route
        const response = await agent.get('/api/auth/user');

        // Expect the session to have expired and return a 401 status
        expect(response).to.have.status(401);
    });
});
