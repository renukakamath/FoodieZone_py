from flask import Flask,Blueprint,render_template
from database import *

user=Blueprint('user',__name__)

@user.route('/userhome')  #Users Dashboard
def userhome():
    return render_template('userhome.html')