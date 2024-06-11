from flask import Flask,Blueprint,render_template,request,redirect,url_for
from database import *

admin=Blueprint('admin',__name__)

@admin.route('/adminHome')
def adminHome():
    return render_template("adminhome.html")

@admin.route('/managefood',methods=['get','post'])  #Food Registration
def managefood():
    if 'btnadd' in request.form:
        foodcat=request.form['foodcat']
        r="insert into tblfood_category values(NULL,'%s')"%(foodcat)
        insert(r)
        return redirect(url_for('admin.managefood'))
    return render_template("addfoodcategory.html")

@admin.route('/viewcategory',methods=['get','post']) #view food category
def viewcategory():
    data={}
    q="select * from tblfood_category"
    res=select(q)
    data['c']=res
    if 'action' in request.args:  #fetching action & id for accepting/rejecting
        id=request.args['id']
        action=request.args['action']
    else:
        action=None
    if action=="delete":    #deleting food category
        d="delete from tblfood_category where category_id='%s'"%(id)
        delete(d)
        return redirect(url_for('admin.viewcategory'))
    if action=="edit":     #editing food category
        e="select * from tblfood_category where category_id='%s'"%(id)
        res=select(e)
        data['r']=res
    if 'update' in request.form:
        cat=request.form['foodcat']
        u="update tblfood_category set category_name='%s' where category_id='%s'"%(cat,id)
        update(u)
        return redirect(url_for('admin.viewcategory'))
    return render_template("viewcategory.html",data=data)
    

@admin.route('/viewuser',methods=['get','post'])  #view users registered 
def viewuser():
    data={}
    q="select * from tbluser"
    res=select(q)
    data['c']=res
    return render_template("viewuser.html",data=data)




@admin.route('/admin_view_menus_price',methods=['get','post'])  
def admin_view_menus_price():
    data={}
    q="SELECT * FROM `tblmenu`"
    res=select(q)
    data['menu']=res
    return render_template("admin_view_menus_price.html",data=data)




@admin.route('/edituser',methods=['get','post']) #edit users
def edituser():
    return render_template("edituser.html")


@admin.route('/viewhotels',methods=['get','post']) #view hotels/entrepreneurs
def viewhotels():

    data={}
    q="select * from tblentrepreneur inner join tbllogin using(login_id)"
    res=select(q)
    data['c']=res

    if 'action' in request.args:  #fetching action & id for accepting/rejecting
        id=request.args['id']
        action=request.args['action']
    else:
        action=None
    if action=="accept":    #Accepting hotels
        d="update tbllogin set user_type='entrepreneur' where login_id='%s'"%(id)
        update(d)
        u="update tblentrepreneur set status='accepted' where login_id='%s'"%(id)
        update(u)
        return redirect(url_for('admin.viewhotels'))
    if action=="reject":    #Rejecting hotels
        d="delete from tbllogin where login_id='%s'"%(id)
        delete(d)
        u="delete from tblentrepreneur where login_id='%s'"%(id)
        update(u)
        return redirect(url_for('admin.viewhotels'))
    
    return render_template("viewhotels.html",data=data)


@admin.route('/admin_view_hotel_rating') 
def admin_view_hotel_rating():
    data={}
    q="SELECT * FROM `tblrating` INNER JOIN `tbluser` USING(user_id) INNER JOIN `tblentrepreneur` USING(hotel_id)"
    res=select(q)
    data['rate']=res
    return render_template("admin_view_hotel_rating.html",data=data)



@admin.route('/admin_view_reported_hotels') 
def admin_view_reported_hotels():
    data={}
    q="SELECT * FROM `tblreported_hotel` INNER JOIN `tbluser` USING(user_id) INNER JOIN `tblentrepreneur` USING(hotel_id)"
    res=select(q)
    data['rate']=res
    return render_template("admin_view_reported_hotels.html",data=data)