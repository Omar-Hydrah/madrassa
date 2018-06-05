"user strict";
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

		},

		password : {
			type :DataTypes.STRING(20),
			allowNull: false
		}, 

		role : {
			type: DataTypes.ENUM("admin", "teacher", "student"),
		}, 

		created_at : {
			type: DataTypes.DATE,
			defaultValue: DataTypes.NOW,
			allowNull: false
		}
	}, {
		tableName: "users",
		timestamps: false
	});

	return User;
};