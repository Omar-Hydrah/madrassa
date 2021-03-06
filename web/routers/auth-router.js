var router = require("express").Router();
var passport   = require("passport");
var sequelize  = require("../config/sequelize-config.js").sequelize;
var User       = sequelize.import("../models/user.js");
var middleware = require("../middleware/index.js");
var moment     = require("moment");

function createSession(req){

	// Storing user related data in the session.
	// The user is expected to be a full sequelize object.
	if(!req.user){
		return;
	}

	// TODO: Make sure that the user variable can access the database
	// values. Errors can arise if the user doesn't contain the $get() function
	var user = req.user.get({plain: true});

	if(user.user_id && user.username){
		req.session.user = {
			userId  : user.user_id,
			username: user.username,
			role    : user.role,
			createdAt: moment(user.created_at).format("DD-MM-YYYY"),
		}
		
	}
}

var loginMiddleware = passport.authenticate("login", {
	failureRedirect: "/auth/login",
	// successRedirect: "/home",
	failureFlash: true
});

var registerMiddleware = passport.authenticate("register", {
	failureRedirect: "/auth/register",
	// successRedirect: "/home",
	failureFlash: true
});

router.get("/login", middleware.isNotLoggedIn,  (req, res)=>{


	var loginMessage = req.flash("loginMessage");

	// If any errors occur during login:
	if(loginMessage.length){
		return res.render("auth/login", {err: loginMessage});
		// console.log("Login Error");
	}

	res.render("auth/login", {err: null});
});

router.post("/login", loginMiddleware, (req, res)=>{

	createSession(req);
	if(req.session.user.role == "admin"){
		res.redirect("/profile/admin");
	}else{
		res.redirect("/home");
	}
});

router.get("/register", middleware.isNotLoggedIn, (req, res)=>{
	console.log("User trying to register");

	var errors = req.flash("registerMessage"); 

	if(errors != null && errors.length != 0){
		return res.render("auth/register", {errors: errors});
		// console.log("Register Error");
	}

	
	res.render("auth/register", {errors: null});
});

router.post("/register", registerMiddleware, (req, res)=>{
	createSession(req);
	res.redirect("/home");

});

router.get("/logout", (req, res)=>{
	req.logout();
	res.redirect("/");
});

module.exports = router;