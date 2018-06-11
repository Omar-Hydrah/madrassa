"use strict";
module.exports = function(sequelize, DataTypes) {

	var Course = sequelize.define("course", {
			course_id : {
				type : DataTypes.INTEGER(11),
				primaryKey : true,
				allowNull : false,
				autoIncrement : true
			},

			teacher_id :{
				type: DataTypes.INTEGER(11),
				allowNull: false,
				references: {
					model :"users",
					key: "user_id" 
				}
			},

			title : {
				type: DataTypes.STRING(50),
				allowNull: false,
			}, 
			description : {
				type: DataTypes.TEXT,
				allowNull : true
			},

			created_at : {
				type: DataTypes.DATE,
				allowNull : false,
				defaultValue : DataTypes.NOW			
			}
		}, {
			tableName  : "courses",
			timestamps :false,
		});
	
	return Course;
}