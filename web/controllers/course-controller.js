var sequelize = require("../config/sequelize-config.js").sequelize;
var User      = sequelize.import("../models/user.js");
var Course    = sequelize.import("../models/course.js");
var CourseStudent = sequelize.import("../models/course-student.js");


var CourseController = {};

// The user must have a role of a teacher
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

// The user must have a role of a student.
// Should insert in `course_students` table.
CourseController.joinCourse = function(courseId, userId) {

	return new Promise((resolve, reject)=>{
		var query = "select user_id from users "; 
		query += " where user_id = ? and role = 'student'";
		query += " union all select course_id from courses where course_id = ?";
		sequelize.query(query, {replacements: [userId, courseId]})
			.spread((result)=>{
				console.log(result);
				if(result.length != 2){
					// Either course or user was not found.

					var error = "The user is not a student,"; 
					error += " or course cannot be found";
					reject(new Error(error));
					console.log(error);

				// }else if(result[0].user_id == userId 
				// 	&& result[1].course_id == courseId)
				// {

				}else{
					// reject(new Error("Unknown error"));
					// sequelize.query("insert int")
					CourseStudent.create({
						student_id: userId, 
						course_id: courseId
					}, {
						raw: true
					}).then((courseStudent)=>{
						console.log(courseStudent.get({plain:true}));
						resolve(courseStudent);
					}).catch((err)=>{
						console.log(err.name);
						reject(err);
					});
					// console.log("Registering a new user.");
				}
			});
		
	});

};

CourseController.leaveCourse = function(courseId, studentId) {
	// Using the Model.destroy(), will cost 2 queries.
	
	return new Promise((resolve , reject)=>{
		var query = "delete from course_students"; 
		query += " where course_id = ? and student_id = ?";

		sequelize.query(query, {replacements: [courseId, studentId]})
			.spread((result)=>{	
				console.log(result);
				// ResultSetHeader {
				// fieldCount: 0,
				// affectedRows: 1,
				// insertId: 0,
				// info: '',
				// serverStatus: 2,
				// warningStatus: 0 }
				if(result != null){

					resolve(result);
				}else{
					reject(new Error("Failed to leave course."));
				}
			});
		
	});
};

CourseController.allCourses = function() {
	return new Promise((resolve, reject)=>{

		Course.findAll({raw: true}).then((courses)=>{
			resolve(courses);
		}).catch((err)=>{
			reject(courses);
		});
	});
};

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
				if(!result || result.length == 0){
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

// select 
// cs.student_id, cs.course_id, 
// concat(u.first_name, ' ', u.last_name) as name, c.title 
// from course_students cs 
// 	left join courses c on c.course_id = cs.course_id 
// 	left join users u on cs.student_id = u.user_id 
// 	where cs.course_id = 5;

CourseController.getCourseStudents = function(courseId) {
	return new Promise((resolve, reject)=>{
		var query = "select cs.student_id, cs.course_id, ";
		query += " concat(u.first_name, ' ', u.last_name) as name, c.title";
		query += " from course_students cs";
		query += " left join courses c on c.course_id = cs.course_id";
		query += " left join users u on cs.student_id = u.user_id";
		query += " where cs.course_id = ?";

		// @Result:
		// TextRow {
	    // student_id: 24,
	    // course_id: 6,
	    // name: 'ayman mohammad',
	    // title: 'Philosophy' }
		sequelize.query(query, {replacements: [courseId]})
			.spread((result)=>{
				if(!result || result.length == 0){
					reject(new Error("Course not found"));
				}else{
					resolve(result);
				}
			});
		
	});
};


module.exports = CourseController;