from flask import Flask,Blueprint,render_template,request,redirect,url_for
from database import *

deliveryboy=Blueprint('deliveryboy',__name__)

@deliveryboy.route("/deliveryboyhome")
def deliveryboyhome():
    return render_template("deliveryboyhome.html")
