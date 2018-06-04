"use strict";
module.exports = function(sequelize, DataTypes) {
	var Teacher = sequelize.define("teacher", {
		teacher_id : {
			type : DataTypes.INTEGER(11),
			primaryKey : true,
			allowNull : false,
			autoIncrement : true
		},

		user_id :{
			type: DataTypes.INTEGER(11),
			allowNull: false,
			references: {
				model :"users",
				key: "user_id" 
			}
		},

		name : {
			type: DataTypes.STRING(100),
			allowNull: false,
		}, 
		email : {
			type: DataTypes.STRING(100),
			allowNull : true
		},

		created_at : {
			type: DataTypes.DATE,
			allowNull : false,
			defaultValue : DataTypes.NOW			
		}
	}, {
		tableName  : "teachers",
		timestamps :false,

		/*classMethods: {
			associate: function(models) {
				Teacher.belongsTo(models.User)
			}
		}*/
	});

	return Teacher;
};