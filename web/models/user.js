"user strict";
var bcrypt = require("bcrypt-nodejs");

module.exports = function(sequelize, DataTypes) {
	var User = sequelize.define("user", {
		user_id :{
			type: DataTypes.INTEGER(11),
			allowNull: false,
			autoIncrement : true,
			primaryKey :true
		}, 
		username : {
			type: DataTypes.STRING(30),
			allowNull: false,
			unique: true,
		},

		password : {
			type :DataTypes.STRING(255),
			allowNull: false
		}, 

		first_name : {
			type: DataTypes.STRING(20),
			allowNull: true
		},

		last_name: {
			type: DataTypes.STRING(20),
			allowNull: true
		},

		role : {
			type: DataTypes.ENUM("admin", "teacher", "student"),
			allowNull: false
		}, 

		created_at : {
			type: DataTypes.DATE,
			defaultValue: DataTypes.NOW,
			allowNull: false
		}
	}, {
		tableName: "users",
		// omitNull: true,
		timestamps: false
	});

	User.hashPassword = function(password) {
		return bcrypt.hashSync(password, bcrypt.genSaltSync(8), null);
	};

	User.createUser = function(body, username, password) {
		return new Promise((resolve, reject)=>{
			var result = {
				user: null,
				errors: [],
				// flash message
				flash : {
					title: "",
					content: ""
				}
			};

			if(username.length < 2){
				result.errors.push("Username must be more than 2 characters.");
			}

			if(password.length < 4){
				result.errors.push("Password must be at least 4 characters.");
			}

			var allowedRoles = ["teacher", "student"];
			if(allowedRoles.indexOf(body.role) == -1){
				result.errors.push("You must be a teacher or a student to join");
			}

			// If validations fail, return before trying to save to database. 
			if(result.errors.length != 0){
				// return done(null, false, req.flash("registerMessage", errors));
				resolve(result);
			}

			User.create({	
				username  : username, 
				password  : User.hashPassword(password),
				first_name: body.firstName,
				last_name : body.lastName,
				role    : body.role

			}, {
				fields: ["username", "password", "first_name", "last_name", "role"]
			}).then((user)=>{
			
				if(!user){
					// 	req.flash("registerMessage", "Unknown user creation error"));
					result.flash.title = "registerMessage";
					result.flash.content = "Failed to create the user";
					resolve(result);
				}else{
					// return done(null, user);
					result.user = user;
					resolve(result);
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
					result.errors.push("Username already registered in the database");

					// return done(null, false, req.flash("registerMessage", errors));
					result.flash.title = "registerMessage";
					result.flash.content = "Errors array"
					resolve(result);
			    }
				
				// return done(null, false, 
					// req.flash("registerMessage", "Unknown Registeration Error"));
				result.flash.title = "registerMessage";
				result.flash.content = "Unknown registeration error";
				resolve(result);
			});
		});
	};

	User.prototype.verifyPassword = function(password){
		return bcrypt.compareSync(password, this.password);
	}

	return User;
};