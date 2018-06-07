var passport      = require("passport");
var LocalStrategy = require("passport-local").Strategy;

var sequelize = require("./sequelize-config.js").sequelize;
var User      = sequelize.import("../models/user.js");

// Database queries must send the "raw" option, to have user.user_id
passport.serializeUser(function(user, done) {
	
	return done(null, user.user_id);
});

passport.deserializeUser(function(id, done) {
	User.findOne({
		where: {
			user_id : id
		}
	}).then((user)=>{
		return done(null , user);

	}).catch((err)=>{
		return done(err, null);
	});
});

var loginStrategy = new LocalStrategy({
	usernameField: "username",
	passwordField: "password",
	passReqToCallback: true
}, function(req, username, password, done) {
	User.findOne({
		where: {
			username: username
		}, 
		// Setting 'raw : true', hides the verifyPassword() method.
		// raw: true

	}).then((user)=>{

		if(!user || !user.verifyPassword(password)){

			return done(
				null, false, 
				req.flash("loginMessage", "Wrong username or password"));

		}else if(user.verifyPassword(password)){
			return done(null, user);
		}else{
			return done(
				null, false, 
				req.flash("loginMessage", "Unknown Login Error"));
		}
	}).catch((err)=>{
		return done(err, null);
	});
});


passport.use("login", loginStrategy);


var registerStrategy = new LocalStrategy({
	usernameField: "username",
	passwordField: "password",
	passReqToCallback: true
}, function(req, username, password, done) {

	/*User.findOne({
		where: {
			username: username
		},
		raw: true*/
	User.create({	
		username: username, 
		password: User.hashPassword(password),

	}, {
		fields: ["username", "password"]
	}).then((user)=>{
			
		if(!user){
			console.log("No user created");
			return done(
				null,  false, 
				req.flash("registerMessage", "Unknown user creation error"));

		}else{

			return done(null, user);
			// To send plain user data:
			// return done(null, user.get({plain: true}));
		}

	}).catch((err)=>{
		// code: 'ER_DUP_ENTRY',
	    // errno: 1062,
		// if(err.errno == 1062){

	    
	    // name: SequelizeUniqueConstraintError
	    // Duplicate entry for username (Unique constraint fails).
	    if(err.name == "SequelizeUniqueConstraintError"){
			return done(
				null, false, 
				req.flash("registerMessage", 
				"Username already registered in the database"));
			// console.log(err.name);

	    }
		
		// throw err;
		return done(null, false, 
			req.flash("registerMessage", "Unknown Registeration Error"));
	});
});

passport.use("register", registerStrategy);