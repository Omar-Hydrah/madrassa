"use strict";
module.exports = function(sequelize, DataTypes) {
	var Student = sequelize.define("student", {
		student_id : {
			type: DataTypes.INTEGER(11),
			allowNull: false,
			primaryKey : true,
			autoIncrement : true,
		},

		user_id :{
			type: DataTypes.INTEGER(11),
			allowNull: false,
			references: {
				model: "users",
				key: "user_id"
			}
		},

		name : {
			type: DataTypes.STRING(100),
			allowNull: false,

		},
		email : { 
			type: DataTypes.STRING(100),

		},

		created_at : {
			type: DataTypes.DATE,
			allowNull: false,
			defaultValue: DataTypes.NOW
		}
	}, {
		tableName : "students",
		timestamps: false

		/*classMethods: {
			associate: function(models) {
				Student.belongsTo(models.User);
			}
		}*/
	});

	return Student;
}