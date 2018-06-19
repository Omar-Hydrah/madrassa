var sequelize = require("../config/sequelize-config.js").sequelize;
var User      = sequelize.import("../models/user.js");
var UserController = {};

UserController.createUser = function() {

}

UserController.findByUsername = async function(username) {

	var user = await User.find({
		where: {
			username : username
		}
	});
	// A promise that resolves immediately.
	return user;

};
// Updates a user role.
UserController.updateRole = function(userId, role) {
	console.log(userId);

	return new Promise((resolve, reject)=>{
		var user = User.findOne({
			where: {
				user_id: userId
			}
		}).then((user)=>{
			console.log("Updating: ", user.get("username"));

			if(!user){
				reject("User was not found in the database");
			}else{
				user.update({
					role: role
				}).then((updatedUser)=>{
					console.log("User has been updated", updatedUser.get("username"));

					// To update the user data in the session.
					resolve(updatedUser.get({plain: true}));

				}).catch((err)=>{
					console.log(err);
					reject(err);
				});
			}
		}).catch((err)=>{
			console.log(err);
			reject(err);
		});
		
	});
};

module.exports = UserController;