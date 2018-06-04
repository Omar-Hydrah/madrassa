"user strict";
module.exports = function(sequelize, DataTypes) {
	var User = sequelize.define("user", {
		user_id :{
			type: DataTypes.INTEGER(11),
			allowNull: false,
			autoIncrement : true,
			primaryKey :true
		}, 
		first_name : {
			type: DataTypes.STRING(15),
			allowNull: false,

		},

		last_name : {
			type :DataTypes.STRING(15),
			allowNull: false
		}, 

		email : {
			type: DataTypes.STRING(40),
			allowNull: false,
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
		timestamps: false
	});

	return User;
};