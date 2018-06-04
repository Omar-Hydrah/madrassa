"use strict";
module.exports = function(sequelize, DataTypes) {
	return sequelize.define("teacher", {
		teacher_id : {
			type : DataTypes.INTEGER(11),
			primaryKey : true,
			allowNull : false,
			autoIncrement : true
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
		timestamps :false
	});
};