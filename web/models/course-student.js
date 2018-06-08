"use strict";

module.exports = function(sequelize, DataTypes) {
	// var Course  = sequelize.import("course.js");
	// var Student = sequelize.import("student.js");

	var CourseStudent = sequelize.define("course_student", {
		course_id : {
			type: DataTypes.INTEGER(11),
			allowNull: false,
			primaryKey : true,
			references: {
				model : "course",
				key: "course_id"
			}
		}, 

		student_id :{
			type: DataTypes.INTEGER(11),
			allowNull: false,
			references : {
				model: "student",
				key : "student_id"
			}
		}

	}, {
		tableName: "course_students",
		timestamps: false,

		classMethods : {
			associate : function(models) {
				CourseStudent.hasMany(models.Course);
				CourseStudent.hasMany(models.Student);
			}
		}
	});


	return CourseStudent;
};
