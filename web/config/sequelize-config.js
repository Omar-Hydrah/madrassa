var Sequelize = require("sequelize");
var sequelize = new Sequelize(
	"madrassa", 
	process.env.db_username, 
	process.env.db_password, 
	{
		operatorsAliases: false,
		logging: true
});


module.exports.init = function(){
	sequelize.sync({force: false});
}

module.exports.sequelize = sequelize;