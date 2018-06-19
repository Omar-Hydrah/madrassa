var assert           = require("assert");
var sequelize        = require("../config/sequelize-config.js").sequelize;
var User             = sequelize.import("../models/user.js");
var CourseController = require("../controllers/course-controller.js");

describe("Controlling the course", function(){

	var teacher = {};
	var student = {};
	var chemistry;
	var teacherObject = {
		username: "omar" ,
		password: User.hashPassword("1234"),
		first_name: "omar",
		last_name: "mohammad",
		role   : "teacher" 
	};

	var studentObject = {
		username: "ayman" ,
		password: User.hashPassword("1234"),
		first_name: "ayman",
		last_name: "mohammad",
		role   : "student" 
	};

	// Insert a new teacher, a new course and a new student into the database.
	before(function(done){
		// Creating a teacher.
		Promise.all([
			User.create( teacherObject, {
				fields: ["username", "password", 
					"first_name", "last_name", "role"
				]
			}),

			// }).then((user)=>{
				// teacher = user;
				// Creating a new course, by this teacher.
				// CourseController.createCourse(teacher.get("user_id"),
				// 	"Chemistry", "Introduction to the world of chemistry")
				// 	.then((course)=>{
				// 		chemistry = course;
				// 	}).catch((err)=>{
				// 		throw err;
				// 	});
			// .catch((err)=>{
			// 	throw err;
			// })

			// Creating a student
			User.create(studentObject, {
				fields: ["username", "password", 
					"first_name", "last_name", "role"]
			})
			// }).then((user)=>{
			// 	student = user;
			// 	// console.log(Object.keys(student));
			// }).catch((err)=>{
			// 	throw err;
			// })
		]).then((values)=>{
			// Teacher is first, student is second.
			// console.log(values);
			teacher = values[0];
			student = values[1];
			console.log(teacher.get("username"));
			done();
			// CourseController.createCourse(teacher.get("user_id"),
			// 	"Chemistry", "Introduction to the world of chemistry", false)
			// .then((course)=>{
			// 	chemistry = course;
			// 	done();
			// }).catch((err)=>{
			// 	console.log(err);
			// });

		}).catch((err)=>{
			console.log(err);
		});
		
		
	});
	describe("Creating a new course", ()=>{
		var title = "Physics";
		var description = "Introduction to Physics";
		console.log("teacher", teacher);
		console.log("student", student);

		// CourseController.createCourse(teacher.get("user_id"),
		// 		"Chemistry", "Introduction to the world of chemistry", false)
		// .then((course)=>{
		// 	chemistry = course;
		// 	done();
		// }).catch((err)=>{
		// 	console.log(err);
		// });

		// CourseController.createCourse(teacher.get("user_id"), title, description)
		// .then((course)=>{
		// 	course.destroy();
		// 	it("Contains a user id of " + teacherId, ()=>{
		// 		assert.equal(course.get("teacher_id"), teacherId);
		// 	});

		// }).catch((err)=>{
		// 	console.log(err);
		// });
		it("Success", ()=>{
			
		});
		// Undefined:
		// console.log(Object.keys(teacher));
		// console.log(Object.keys(student));
	});

	after(function(done){
		// Destroying database instances.
		Promise.all([
			User.findOne({where: {username: teacherObject.username}}),
			User.findOne({where: {username: studentObject.username}})	
		]).then((values)=>{
			Promise.all([
				values[0].destroy(),
				values[1].destroy()
			]).then(()=>{
				done();
			}).catch((err)=>{
				throw err;
			});

		}).catch((err)=>{
			throw err;
		});
		// if(teacher != null){
		// 	teacher.destroy();
		// }
		// if(student != null){
		// 	student.destroy();
		// }
		// if(chemistry != null){
		// 	console.log(chemistry);
		// 	chemistry.destroy();
		// }
	});
});