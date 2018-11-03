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

	CourseStudent.getCourseStudents = function(courseId) {
		return new Promise((resolve, reject)=>{
			var result = {
				students: null,
				message: "",
				err: null
			};
			var query = "select cs.student_id as id, cs.course_id as courseId,";
			query += " u.username, u.first_name as firstName, "; 
			query += " u.last_name as lastName, u.role,";
			query += " concat(u.first_name, ' ', u.last_name) as name";
			query += " from course_students cs";
			query += " left join courses c on c.course_id = cs.course_id";
			query += " left join users u on cs.student_id = u.user_id";
			query += " where cs.course_id = ?";

			sequelize.query(query, {replacements: [courseId]})
				.spread((data)=>{
					console.log(data);
					if(!data || data.length == 0 ){
						result.message = "fail";
						resolve(result);
						return;
					}
					result.students = data;
					result.message  = "success";
					resolve(result);
				});
		});
	};

	CourseStudent.joinCourse = function(studentId, courseId, plainData= true) {
		return new Promise((resolve, reject)=>{
			var result = {
				courseStudent: null,
				message : "",
				err: null
			};

			CourseStudent.create({
				student_id: studentId,
				course_id : courseId
			}, {
				raw : plainData
			}).then((courseStudent)=>{
				console.log(courseStudent);
				result.message = "success";
				result.courseStudent = courseStudent.courseStudent.dataValues;
				resolve(result);
			}).catch((err)=>{
				// throw err;
				console.log(err);
				result.message = "fail";
				result.err = err;
				resolve(err);
			});
		});
	};

	CourseStudent.leaveCourse = function(studentId, courseId, plainData =true){
		return new Promise((resolve, reject)=>{
			var result = {
				resultSetHeader: null,
				affectedRows   : 0,
				message        : "",
				err            : null
			};

			var query = "delete from course_students"; 
			query += " where course_id = ? and student_id = ?";
			sequelize.query(query, {replacements: [courseId, studentId]})
			.spread((res)=>{
				// ResultSetHeader {
				// fieldCount: 0,
				// affectedRows: 1,
				// insertId: 0,
				// info: '',
				// serverStatus: 2,
				// warningStatus: 0 }
				console.log(res);
				result.resultSetHeader = res;
				result.affetedRows     = res.affectedRows;

				if(res != null && res.affetedRows != null && 
					res.affectedRows == 1)
				{
					result.message = "success";
					resolve(result);
					return;
				}else{
					result.message = "fail";
					resolve(result);
					return;
				}
			});

		});
	};


	return CourseStudent;
};
