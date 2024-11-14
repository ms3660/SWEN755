from flask import Blueprint, render_template, request, redirect, url_for, flash
from flask_login import login_user, login_required, logout_user, current_user
from models import User, db
import uuid

routes = Blueprint('routes', __name__)

@routes.route('/')
def index():
    return render_template('index.html')

@routes.route('/login', methods=['GET', 'POST'])
def login():
    if request.method == 'POST':
        username = request.form['username']
        password = request.form['password']
        user = User.query.filter_by(username=username).first()
        
        if user and user.check_password(password):
            # Generate and store unique session ID
            session_id = str(uuid.uuid4())
            user.session_id = session_id
            db.session.commit()
            
            login_user(user)
            return redirect(url_for('routes.dashboard'))
            
        flash('Invalid credentials')
    return render_template('login.html')

@routes.route('/logout')
@login_required
def logout():
    if current_user.is_authenticated:
        current_user.session_id = None
        db.session.commit()
    logout_user()
    return redirect(url_for('routes.index'))

@routes.route('/dashboard')
@login_required
def dashboard():
    return render_template('dashboard.html')

@routes.route('/task')
@login_required
def task():
    if not current_user.is_authorized:
        flash('You are not authorized to perform this task.')
        return redirect(url_for('routes.dashboard'))
    return render_template('task.html')