"use strict";
module.exports = function(sequelize, DataTypes) {
	return sequelize.define("subject", {
		subject_id : {
			type: DataTypes.INTEGER(11),
			allowNull: false,
			autoIncrement: true,
			primaryKey : true
		},

		subject: {
			type: DataTypes.STRING(50),
			allowNull: false
		}
	}, {
		tableName : "subjects",
		timestamps: false
	});
};
