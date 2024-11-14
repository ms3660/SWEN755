from flask import Flask
from flask_login import LoginManager
import os
from config import Config
from models import db, User
from routes import routes

def create_app():
    web_dir = os.path.abspath(os.path.join(os.path.dirname(__file__), '..', 'web'))
    
    app = Flask(__name__,
                template_folder=os.path.join(web_dir, 'templates'),
                static_folder=os.path.join(web_dir, 'static'))
    
    app.config.from_object(Config)
    
    # Initialize database
    db.init_app(app)
    
    # Setup Login Manager
    login_manager = LoginManager()
    login_manager.login_view = 'routes.login'
    login_manager.init_app(app)
    
    @login_manager.user_loader
    def load_user(user_id):
        return User.query.get(int(user_id))
    
    app.register_blueprint(routes)
    
    # Create database and sample users
    with app.app_context():
        db.create_all()
        
        # User 1: Authenticated and authorized
        if not User.query.filter_by(username='user1').first():
            user1 = User(username='user1', is_authorized=True)
            user1.set_password('pass1')
            db.session.add(user1)

        # User 2: Authenticated but not authorized
        if not User.query.filter_by(username='user2').first():
            user2 = User(username='user2', is_authorized=False)
            user2.set_password('pass2')
            db.session.add(user2)

        # User 3: For demonstration only
        if not User.query.filter_by(username='user3').first():
            user3 = User(username='user3', is_authorized=False)
            user3.set_password('pass3')
            db.session.add(user3)

        db.session.commit()
    
    return app

if __name__ == '__main__':
    app = create_app()
    app.run(debug=True)