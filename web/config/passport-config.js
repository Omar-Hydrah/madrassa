var passport      = require("passport");
var LocalStrategy = require("passport-local").Strategy;

var sequelize = require("./sequelize-config.js").sequelize;
var User      = sequelize.import("../models/user.js");

// Database queries must send the "raw" option, to have user.user_id
// Otherwise, the user_id can be obtained using: user.get("user_id")
passport.serializeUser(function(user, done) {
	// console.log("Serialization");
	// console.log(user.get({plain: true}));
	// console.log("user_id", user.get("user_id"));
	return done(null, user.get("user_id"));
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

	// Registeration/Validation errors. To be flashed using req.flash().
	var errors = [];


	if(username.length < 2){
		errors.push("Username must be more than 2 characters.");
	}

	if(password.length < 4){
		errors.push("Password must be at least 4 characters.");
	}

	var allowedRoles = ["teacher", "student"];
	if(allowedRoles.indexOf(req.body.role) == -1){
		/*return req.flash("registerMessage", 
			"");*/
		errors.push("You must be a teacher or a student to join");
		// console.log(req.body.role);
	}

	// If validations fail, return before trying to save to database. 
	if(errors.length != 0){
		// console.log(errors);
		// console.log(errors.length);
		return done(null, false, req.flash("registerMessage", errors));
	}

	User.create({	
		username  : username, 
		password  : User.hashPassword(password),
		first_name: req.body.firstName,
		last_name : req.body.lastName,
		role    : req.body.role

	}, {
		fields: ["username", "password", "first_name", "last_name", "role"]
	}).then((user)=>{
			
		if(!user){
			// console.log("No user created");
			return done(
				null,  false, 
				req.flash("registerMessage", "Unknown user creation error"));

		}else{
			// console.log("Saved user");
			// console.log(user.get({plain: true}));
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
			/*return done(
				null, false, 
				req.flash("registerMessage", 
				"Username already registered in the database"));*/
			// console.log(err.name);
			errors.push("Username already registered in the database");

			return done(null, false, req.flash("registerMessage", errors));

	    }
		
		// throw err;
		return done(null, false, 
			req.flash("registerMessage", "Unknown Registeration Error"));
	});
});

passport.use("register", registerStrategy);