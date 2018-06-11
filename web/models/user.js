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
	}

	User.prototype.verifyPassword = function(password){
		return bcrypt.compareSync(password, this.password);
	}

	return User;
};