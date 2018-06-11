var sequelize = require("../config/sequelize-config.js").sequelize;
var User      = sequelize.import("../models/user.js");
var Course    = sequelize.import("../models/course.js");


var CourseController = {};

CourseController.createCourse = function(userId, title, description) {

	return new Promise((resolve, reject)=>{

		User.findById(userId).then((user)=>{

			if(!user){
				reject(new Error("Teacher not found"));
			}else{
				// If the user is a teacher indeed, create the course.
				if(user.role == "teacher" || user.role == "admin"){
					Course.create({
						teacher_id: user.user_id,
						title: title,
						description : description
					}).then((course)=>{

						// Only one success case.
						resolve(course.get({plain: true}));

					}).catch((err)=>{
						console.log(err);
						reject(err);
					});
				}else{
				// Otherwise, prevent the course creation.	
					reject(new Error("A student can not create a new course"));
				}
			}
			
		}).catch((err)=>{
			console.log(err);
			reject(err);
		});
		
	});
}

CourseController.allCourses = function() {
	return new Promise((resolve, reject)=>{

		Course.findAll({raw: true}).then((courses)=>{
			resolve(courses);
		}).catch((err)=>{
			reject(courses);
		});
	});
}

CourseController.getCoursesDetails = function() {
	/*
	select 
	c.course_id, c.title, c.description, 
		date_format(c.created_at, "%Y") as year, 
	concat(users.first_name, " ", users.last_name) as name
	from courses c 
		left join users
			on c.teacher_id = users.user_id
	*/
	return new Promise((resolve, reject)=>{
		var query = "select c.course_id, c.title, c.description,";
		query += " date_format(c.created_at, '%Y') as year, "; 
		query += " concat(users.first_name, ' ', users.last_name) as teacher ";
		query += " from courses c left join users on c.teacher_id = users.user_id";

		sequelize.query(query).spread((result)=>{
			// TODO: it would raise an error if the sql command is misformatted. 
			resolve(result);
		});
	});
}

CourseController.getCourse = function(courseId) {
	return new Promise((resolve, reject)=>{
		var query = "select c.course_id, c.teacher_id, c.title, c.description, "; 
		query += " date_format(c.created_at, '%Y') as year,";
		query += " concat(users.first_name, ' ', users.last_name) as teacher";
		query += " from courses c left join users "; 
		query += " on c.teacher_id = users.user_id where course_id = ? limit 1";

		sequelize.query(query, {replacements: [courseId]})
			.spread((result)=>{
				if(!result){
					reject(new Error("Course not found"));
				}
				resolve(result);
			});

		/*Course.findById(courseId, {raw: true})
			.then((course)=>{
				if(!course){
					reject(new Error("Course not found"));
				}else{
					resolve(course);
				}

			}).catch((err)=>{
				// throw err;
				reject(course);
			});*/
	});
};

module.exports = CourseController;