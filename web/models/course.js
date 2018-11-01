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

	Course.createCourse = function(teacherId, title, description, plainData = true) {
		return new Promise((resolve, reject)=>{
			var result = {
				course : null,
				message: "",
				err    : null
			};
			Course.create({
				teacher_id: teacherId,
				title     : title,
				description: description
			}).then((course)=>{
				// if(plainData){
				// 	result.course = course.get({plain:true});
				// 	result.message = "success";

				// 	resolve(result);
				// }
				result.course = (plainData) ? course.get({plain:true}) : course;
				result.message = "success";
				resolve(result);
				return;
			}).catch((err)=>{
				// console.log(err);
				result.message = "fail";
				result.err     = err;
				resolve(result);
				return;
			});
		});
	}

	Course.getCourse = function(courseId) {
		return new Promise((resolve, reject)=>{
			var result = {
				course: null,
				message : "",
				err: null
			};

			var query = "select c.course_id as id, c.teacher_id as teacherId, ";
			query += " c.title, c.description, "; 
			query += " date_format(c.created_at, '%Y') as year,";
			query += " concat(users.first_name, ' ', users.last_name) as teacher";
			query += " from courses c left join users "; 
			query += " on c.teacher_id = users.user_id where course_id = ? limit 1";

			sequelize.query(query, {replacements: [courseId]})
				.spread((course)=>{
					console.log(course);
					if(!course || course.length == 0 ){
						result.message = "Course not found";
						resolve(result);
						return;
					}
					result.course = course[0]; // an array is returned.
					result.message = "success";
					resolve(result);
					return;
				});
		});
	};

	Course.getCourses = function() {
		return new Promise((resolve, reject)=>{
			var result = {
				courses : null,
				message : "",
				err     : null
			};

			var query = "select c.course_id as id, c.title, c.description,";
			query += " date_format(c.created_at, '%Y') as year, "; 
			query += " concat(users.first_name, ' ', users.last_name)"; 
			query += " as teacher ";
			query += " from courses c left join users on "; 
			query += " c.teacher_id = users.user_id";

			sequelize.query(query).spread((res)=>{
				result.courses = res;
				result.message = "success";
				resolve(result);
				return;
			});
		});
	};

	
	return Course;
}