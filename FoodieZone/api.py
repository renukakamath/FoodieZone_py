from flask import Flask,Blueprint,request,flash
from database import* 
import smtplib
from email.mime.text import MIMEText
from flask_mail import Mail


api=Blueprint('api',__name__)

@api.route('/logins')
def logins():
	data={}
	u=request.args['username']
	p=request.args['password']
	q="select * from tbllogin where username='%s' and password='%s'"%(u,p)
	res=select(q)
	
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	return str(data)
	
@api.route('/Registration')	
def Registration():
	data={}
	f=request.args['fname']
	l=request.args['lname']
	h=request.args['housename']
	pl=request.args['place']
	pin=request.args['pincode']
	g=request.args['gender']
	a=request.args['age']
	ph=request.args['phone']
	e=request.args['email']
	u=request.args['username']
	p=request.args['password']
	q="select * from tbllogin where username='%s' and password='%s'"%(u,p)
	res=select(q)
	if res:
		data['status']='already'
	else:
		q="insert into tbllogin values(NULL,'%s','%s','User')"%(u,p)
		lid=insert(q)
		r="insert into tbluser values(NULL,'%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')"%(lid,f,l,h,pl,pin,g,a,ph,e)
		insert(r)
		data['status']="success"
	return str(data)
@api.route('/Viewassign')
def Viewassign():
	data={}
	lid=request.args['login_id']

	q="SELECT *,concat(tblassign.status) as stat FROM tblassign INNER JOIN tblentrepreneur USING (hotel_id) INNER JOIN tbldelivery_boys ON `tblassign`.`d_boy_id`=`tbldelivery_boys`.`delivery_boy_id` INNER JOIN tblbooking USING (booking_id)INNER JOIN `tblmenu` USING (`menu_id`) where delivery_boy_id=(select delivery_boy_id from tbldelivery_boys where login_id='%s')"%(lid)
	res=select(q)
	print(q)
	data['data']=res
	data['status']="success"
	return str(data)

@api.route('/Viewuser')
def Viewuser():
	data={}
	uid=request.args['uid']
	q="select * from tbluser where user_id='%s'"%(uid)
	res=select(q)
	
	data['status']="success"
	data['data']=res
	return str(data)

@api.route('/delivered')
def delivered():
	data={}
	aid=request.args['aid']
	bid=request.args['bid']
	q="update tblassign set status='delivered' where assign_id='%s' "%(aid)
	update(q)
	q="update tblbooking set status='delivered' where booking_id='%s'"%(bid)
	update(q)

	data['status']="success"
	return str(data)

@api.route('/Viewhotels')	
def Viewhotels():
	data={}
	
	q="select * from tblentrepreneur"
	res=select(q)
	if res:
		
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	data['method']="Viewhotels"
	
	return str(data)

@api.route('/search')
def search():
	data={}
	search='%'+request.args['search']+'%'
	q="select * from tblentrepreneur where hotel_name like '%s'"%(search)
	res=select(q)
	if res:
		
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"

	data['method']="Viewhotels"
		

	return str(data)
		

@api.route('/Viewmenu')
def Viewmenu():
	data={}
	hid=request.args['hid']
	q="select * from tblmenu inner join tblfood_category using (category_id) where hotel_id='%s'"%(hid)
	res=select(q)
	data['status']="success"
	data['data']=res
	
	return str(data)

@api.route('/Makeorder')
def Makeorder():
	data={}
	quantity=request.args['quantity']
	mid=request.args['mid']
	lid=request.args['login_id']
	total=request.args['total']

	q="insert into tblbooking values(null,(select user_id from tbluser where login_id='%s'),'%s','%s','%s',curdate(),'pending')"%(lid,mid,quantity,total)
	insert(q)
	print(q)
	data['status']="success"
	
	return str(data)
@api.route('/ViewOrder')
def ViewOrder():
	data={}
	lid=request.args['login_id']
	q="SELECT * FROM tblbooking INNER JOIN tblmenu USING (menu_id) INNER JOIN `tblentrepreneur` USING (hotel_id) WHERE user_id=(SELECT user_id FROM tbluser WHERE login_id='%s') and `tblbooking`.`status`='pending' "%(lid)
	res=select(q)


	data['status']="success"
	data['data']=res
	return str(data)

@api.route('/Makepayment')
def Makepayment():
	data={}
	bid=request.args['bid']
	lid=request.args['login_id']

	food=request.args['food']
	Quantity=request.args['Quantity']
	date=request.args['DateTime']
	total=request.args['Total']
	hotel=request.args['hotel']


	q="insert into tblpayment values(null,'%s',curdate())"%(bid)
	insert(q)
	q="update tblbooking set status='payed' where booking_id='%s'"%(bid)
	update(q)
	q="SELECT * FROM `tbluser` where login_id='%s'"%(lid)
	res=select(q)

	email=res[0]['email']
	email=email
	print("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee",email)
	pwd="YOUR Booking Details "+"\nFood Name : "+food+ "\nQuantity :" +Quantity +"\nDate :"+date+ "\nAmount :"+total+"\nHotel :" +hotel
	print(pwd)
	try:
		gmail = smtplib.SMTP('smtp.gmail.com', 587)
		gmail.ehlo()
		gmail.starttls()
		gmail.login('hariharan0987pp@gmail.com','rjcbcumvkpqynpep')
	except Exception as e:
		print("Couldn't setup email!!"+str(e))
	pwd = MIMEText(pwd)
	pwd['Subject'] = 'Booking details'
	pwd['To'] = email
	pwd['From'] = 'hariharan0987pp@gmail.com'
	try:
		gmail.send_message(pwd)
		print(pwd)
		flash("EMAIL SENED SUCCESFULLY")
	except Exception as e:
		print("COULDN'T SEND EMAIL", str(e))
	else:
		flash("Added successfully")
	data['status']="success"
	return str(data)

@api.route('/Reporthotel')
def Reporthotel():
	data={}
	hid=request.args['hid']
	lid=request.args['login_id']
	c=request.args['complaint']
	q="insert into tblreported_hotel values(null,'%s','%s','%s',curdate())"%(hid,lid,c)
	insert(q)
	data['status']="success"
	
	return str(data)

@api.route('/ViewmyOrder')	
def ViewmyOrder():
	data={}
	lid=request.args['login_id']
	q="SELECT * ,concat (tblbooking.status) as sta  FROM tblbooking INNER JOIN tblmenu USING (menu_id) INNER JOIN `tblentrepreneur` USING (hotel_id) INNER JOIN`tblassign` USING (`booking_id`) WHERE user_id=(select user_id from tbluser where login_id='%s') "%(lid)
	res=select(q)
	data['status']="success"
	data['data']=res
	return str(data)

@api.route('/rate')	
def rate():
	data={}
	lid=request.args['log_id']
	hid=request.args['hid']
	rating=request.args['rating']
	review=request.args['review']


	q="insert into tblrating values(null,(select user_id from tbluser where login_id='%s'),'%s','%s','%s',curdate())"%(lid,hid,rating,review)
	insert(q)
	data['status']="success"
	data['method']="rate"
	return str(data)

@api.route('/Collectfood')
def Collectfood():
	data={}
	bid=request.args['bid']
	q="update tblbooking set status='Collectfood' where booking_id='%s'"%(bid)
	update(q)
	data['status']="success"
	
	return str(data)

@api.route('/recchangepass')
def recchangepass():
	data={}
	curpas=request.args['curpas']
	newpas=request.args['newpas']
	confpas=request.args['confpas']
	lid=request.args['login_id']
	q="select * from tbllogin where login_id='%s' and password='%s'"%(lid,curpas)
	res=select(q)
	if res:
		if newpas == confpas:
			q="update tbllogin set password='%s' where login_id='%s'"%(newpas,lid)
			update(q)
			data['status']='success'
		else:
			data['status']='mismatch'
	else:
		data['status']='failed'
	return str(data)

@api.route('/sendcomplaint')
def sendcomplaint():
	data={}
	cid=request.args['compliant']
	d=request.args['did']
	lid=request.args['login_id']
	q="insert into complaint values(null,'%s','%s','%s','pending')"%(d,lid,cid)
	insert(q);
	data['status']="success"
	
	return str(data)




	


			
	




	
		
		
	







	

