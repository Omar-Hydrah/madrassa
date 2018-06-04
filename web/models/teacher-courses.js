"use strict";
module.exports = function(sequelize, DataTypes) {
	var TeacherCourse = sequelize.define("teacher_course", {
		teacher_id :{
			type: DataTypes.INTEGER(11),
			allowNull : false,
			primaryKey : true, 
			references : {
				model : "teachers",
				key   : "teacher_id"
			}
		},
		course_id :{
			type : DataTypes.INTEGER(11),
			allowNull : false,
			references : {
				model :"courses",
				key   : "course_id"
			}
		}
	}, {
		tableName : "teacher_courses",
		timestamps: false,

		// Relational columns
		classMethods: {
			associate: function(models) {
				TeacherCourse.hasMany(models.Teacher, {
					onDelete: "CASCADE",
					foreignKey : "teacher_id"
				});

				TeacherCourse.hasMany(models.Course, {
					onDelete: "CASCADE",
					foreignKey : "course_id"
				});
			},
		}
	});

	return TeacherCourse;
};