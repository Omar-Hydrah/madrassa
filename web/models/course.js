"use strict";
module.exports = function(sequelize, DataTypes) {
	return sequelize.define("course", {

		course_id : {
			type: DataTypes.INTEGER(11),
			primaryKey : true,
			autoIncrement: true,
			allowNull: false,
		}, 

		teacher_id : {
			type: DataTypes.INTEGER(11),
			allowNull: false,
			references: {
				model: "teachers",
				key: "teacher_id"
			}
		},

		title : {
			type: DataTypes.STRING(50),
			allowNull: false,
		},

		description : {
			type: DataTypes.TEXT,
		},

		created_at : {
			type : DataTypes.DATE,
			allowNull: false,
			defaultValue: DataTypes.NOW
		}
	}, {
		tableName : "courses",
		timestamps: false,

		// Relational columns
		classMethods : {
			associate: function(models) {
				Course.hasOne(models.Teacher, {
					foreignKey: "teacher_id",
					onDelete : "CASCADE"
				});
				
			}
		}
	});

};
