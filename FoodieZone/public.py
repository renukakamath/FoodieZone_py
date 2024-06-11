from flask import Flask,Blueprint,render_template,request,redirect,url_for,session,flash
from database import *

public=Blueprint('public',__name__)

@public.route('/') #Home Page
def home():
    return render_template("home.html")

@public.route('/login',methods=['get','post']) #Login for all stakeholders + sessions for storing
def login():
    if 'submit' in request.form:
        un=request.form['username']
        pwd=request.form['password']
        q="select * from tbllogin where username='%s' and password='%s'"%(un,pwd)
        res=select(q)
        print(res)
        print(q)
        if res:
            session['logid']=res[0]['login_id']
            utype=res[0]['user_type']
            if utype=='admin':
                flash("Login Successfully")
                return redirect(url_for('admin.adminHome'))
            elif utype=='entrepreneur':
                q="select * from  tblentrepreneur where login_id='%s'"%(session['logid'])
                res=select(q)
                session['entid']=res[0]['hotel_id']
                flash("Login Successfully")
                return redirect(url_for('entrepreneur.entrepreneurhome'))
        else:
            flash("Invalid username/password")
            
    return render_template("login.html")

    
@public.route('/entrepreneurreg',methods=['get','post']) #Enterpreneurs Registration
def reg():
    if 'submit' in request.form:
        hname=request.form['hname']
        hphone=request.form['hphone']
        hemail=request.form['hemail']
        un=request.form['username']
        pwd=request.form['pass']
        hlicense=request.form['hlicense']
        q="insert into tbllogin values(NULL,'%s','%s','pending')"%(un,pwd)
        lid=insert(q)
        r="insert into tblentrepreneur values(NULL,'%s','%s','%s','%s','%s','pending')"%(lid,hname,hphone,hemail,hlicense)
        insert(r)
        flash("Added Successfully")
        return redirect(url_for('public.login'))
    return render_template("entrepreneurreg.html")
    


