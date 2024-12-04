const chai = require('chai');
const chaiHttp = require('chai-http');
const app = require('../app');
const { expect } = chai;

chai.use(chaiHttp);

describe('Session Fixation Test', () => {
    it('should regenerate session ID after login', async () => {
        const agent = chai.request.agent(app);

        // Step 1: Perform the first login
        const loginResponse1 = await agent.post('/api/auth/login').send({
            username: 'testuser',
            password: 'testpassword',
        });
        const sessionIdBefore = agent.jar.getCookie('sessionId').value;

        // Step 2: Perform the second login (simulate session fixation)
        const loginResponse2 = await agent.post('/api/auth/login').send({
            username: 'testuser',
            password: 'testpassword',
        });
        const sessionIdAfter = agent.jar.getCookie('sessionId').value;

        // Expect the session ID to change after login (but it won't due to the breaker)
        expect(sessionIdBefore).to.not.equal(
            sessionIdAfter,
            'Session ID should regenerate after login.'
        );
    });
});
