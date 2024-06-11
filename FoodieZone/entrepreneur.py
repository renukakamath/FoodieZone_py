from flask import Flask,Blueprint,render_template,redirect,url_for,session,request
from database import *
import uuid

import smtplib
from email.mime.text import MIMEText
from flask_mail import Mail

entrepreneur=Blueprint('entrepreneur',__name__)

@entrepreneur.route('/entrepreneurhome')      #Entrepreneurs Dashboard
def entrepreneurhome():
    return render_template("entrepreneurhome.html")

@entrepreneur.route('/managedeliveryboy',methods=['get','post']) #Registering delivery boy
def managedeliveryboy():
    if 'btnregister' in request.form:
        fname=request.form['fname']
        lname=request.form['lname']
        dhouse=request.form['dhouse']
        dplace=request.form['dplace']
        dphone=request.form['dphone']
        demail=request.form['demail']
        duname=request.form['duname']
        dpass=request.form['dpass']
        q="insert into tbllogin values(NULL,'%s','%s','deliveryboy')"%(duname,dpass)
        lid=insert(q)
        r="insert into tbldelivery_boys values(NULL,'%s','%s','%s','%s','%s','%s','%s','%s')"%(session['entid'],lid,fname,lname,dhouse,dplace,dphone,demail)
        insert(r)
        return redirect(url_for('entrepreneur.managedeliveryboy'))
    return render_template("deliveryboyreg.html")

@entrepreneur.route('/viewcategoryentre',methods=['get','post']) #view food category
def viewcategoryentre():
    data={}
    q="select * from tblfood_category"
    res=select(q)
    data['c']=res
    return render_template("viewcategoryentre.html",data=data)
@entrepreneur.route('/viewdeliveryboy',methods=['get','post'])
def viewdeliveryboy():
    data={}
    q="select * from tbldelivery_boys where hotel_id='%s'"%(session['entid'])
    res=select(q)
    data['d']=res
    if 'action' in request.args:  #fetching action & id for editing and updating
        id=request.args['id']
        action=request.args['action']
    else:
        action=None
    if action=="delete":    #deleting delivery boy details
        d="delete from tbllogin where login_id='%s'"%(id)
        delete(d)
        d="delete from tbldelivery_boys where login_id='%s'"%(id)
        delete(d)
        return redirect(url_for('entrepreneur.viewdeliveryboy'))
    if action=="edit":     #editing food category
        d="delete from tbllogin where login_id='%s'"%(id)
        delete(d)
        e="select * from tbldelivery_boys where login_id='%s'"%(id)
        res=select(e)
        data['r']=res
    if 'btnupdate' in request.form:
        fname=request.form['fname']
        lname=request.form['lname']
        dhouse=request.form['dhouse']
        dplace=request.form['dplace']
        dphone=request.form['dphone']
        demail=request.form['demail']
        u="update tbldelivery_boys set first_name='%s',last_name='%s',house_name='%s',place='%s',phone='%s',email='%s' where login_id='%s'"%(fname,lname,dhouse,dplace,dphone,demail,id)
        update(u)
        return redirect(url_for('entrepreneur.viewdeliveryboy'))
    return render_template("viewdeliveryboy.html",data=data)



@entrepreneur.route('/hotel_add_menu',methods=['get','post']) #view food category
def hotel_add_menu():
    data={}
    eid=session['entid']
    q="select * from tblfood_category"
    res=select(q)
    data['cat']=res
    if 'btnadd' in request.form:
        cat=request.form['cat']
        fname=request.form['fname']
        price=request.form['price']
        qty=request.form['qty']
        img=request.files['img']
        path='static/'+str(uuid.uuid4())+img.filename 
        img.save(path)
        q="insert into tblmenu values(null,'%s','%s','%s','%s','%s','%s','pending')"%(eid,cat,fname,path,price,qty)
        insert(q)
        return redirect(url_for('entrepreneur.hotel_add_menu'))
    return render_template("hotel_add_menu.html",data=data)


@entrepreneur.route('/hotel_view_menu',methods=['get','post'])
def hotel_view_menu():
    data={}

    q="select * from tblfood_category"
    res=select(q)
    data['cat']=res

    q="select * from tblmenu where hotel_id='%s'"%(session['entid'])
    res=select(q)
    data['d']=res

    if 'action' in request.args:  #fetching action & id for editing and updating
        id=request.args['id']
        action=request.args['action']
    else:
        action=None

    if action=="delete":    
        d="delete from tblmenu where menu_id='%s'"%(id)
        delete(d)
        return redirect(url_for('entrepreneur.hotel_view_menu'))
    if action=="edit":     #editing food category
        e="select * from tblmenu where menu_id='%s'"%(id)
        res=select(e)
        data['r']=res


    if 'btnupdate' in request.form:
        cat=request.form['cat']
        fname=request.form['fname']
        price=request.form['price']
        qty=request.form['qty']
        img=request.files['img']
        path='static/'+str(uuid.uuid4())+img.filename 
        img.save(path)
        u="update tblmenu set food_name='%s',image='%s',price='%s',quantity='%s' where menu_id='%s'"%(fname,path,price,qty,id)
        update(u)
        return redirect(url_for('entrepreneur.hotel_view_menu'))
    return render_template("hotel_view_menu.html",data=data)



@entrepreneur.route('/hotel_view_ratings',methods=['get','post']) #view food category
def hotel_view_ratings():
    data={}
    eid=session['entid']
    q="SELECT * FROM `tblrating` INNER JOIN `tbluser` USING(user_id) INNER JOIN `tblentrepreneur` USING(hotel_id) where hotel_id='%s'"%(eid)
    res=select(q)
    data['rate']=res
    return render_template("hotel_view_ratings.html",data=data)



@entrepreneur.route('/hotel_view_user_details',methods=['get','post']) #view food category
def hotel_view_user_details():
    data={}
    eid=session['entid']
    q="SELECT *,tblbooking.quantity as qty,tblbooking.status as st FROM `tblbooking` INNER JOIN `tbluser` USING(user_id) INNER JOIN `tblmenu` USING(menu_id) where hotel_id='%s'"%(eid)
    res=select(q)
    data['rate']=res
    return render_template("hotel_view_user_details.html",data=data)



@entrepreneur.route('/hotel_assign_deliveryboy',methods=['get','post']) #view food category
def hotel_assign_deliveryboy():
    data={}
    bid=request.args['bid']
    eid=session['entid']
    q="select * from tbldelivery_boys"
    res=select(q)
    data['db']=res

    if 'btnadd' in request.form:
        cat=request.form['cat']
        q="insert into tblassign values(null,'%s','%s','%s',curdate(),'pending')"%(eid,cat,bid)
        insert(q)
        q="update tblbooking set status='assign' where booking_id='%s'"%(bid)
        update(q)

        q="update tblassign set status='assign' where booking_id='%s'"%(bid)
        update(q)

        q="SELECT *,concat(first_name,' ',last_name) as name,tblbooking.total as tt,tblbooking.quantity as qty FROM `tblbooking` INNER JOIN `tbluser` USING(user_id) INNER JOIN `tblmenu` USING(menu_id)"
        r=select(q)
        if r:
            name=r[0]['name']
            phone=r[0]['phone']
            em=r[0]['email']
            menu=r[0]['food_name']
            total=r[0]['tt']
            quantity=r[0]['qty']

        q="select * from tbldelivery_boys where delivery_boy_id='%s'"%(cat)
        res=select(q)
        if res:
            emailid=res[0]['email']
            email=emailid
            print(email)



            pwd="Assigning Details.......!"+"\n\nUser Details\n"+"\nName : "+name+"\nPhone no : "+phone+"\nEmail id : "+em+"\n\nBooking Details\n"+"\nItem : "+menu+"\nQuantity : "+quantity+"\nAmount : "+total

            try:
                gmail = smtplib.SMTP('smtp.gmail.com', 587)
                gmail.ehlo()
                gmail.starttls()
                gmail.login('sngistoutpass@gmail.com','izgqjuqneorhokje')
            except Exception as e:
                print("Couldn't setup email!!"+str(e))

            pwd = MIMEText(pwd)

            pwd['Subject'] = 'Assign'

            pwd['To'] = email

            pwd['From'] = 'sngistoutpass@gmail.com'

            try:
                gmail.send_message(pwd)

                flash("EMAIL SENED SUCCESFULLY")



            except Exception as e:
                print("COULDN'T SEND EMAIL", str(e))
            else:
                flash('ADDED...')
        return redirect(url_for('entrepreneur.hotel_view_user_details'))
    return render_template("hotel_assign_deliveryboy.html",data=data)



@entrepreneur.route('/hotel_view_payments',methods=['get','post']) #view food category
def hotel_view_payments():
    data={}
    bid=request.args['bid']
    eid=session['entid']
    q="SELECT * FROM `tblpayment` INNER JOIN `tblbooking` USING(booking_id) inner join tblmenu using(menu_id) where hotel_id='%s' and booking_id='%s'"%(eid,bid)
    res=select(q)
    data['rate']=res
    return render_template("hotel_view_payments.html",data=data)