var Sequelize = require("sequelize");
var sequelize = new Sequelize(
	"madrassa", 
	process.env.db_username, 
	process.env.db_password, 
	{
		host      : "localhost",
		dialect   : "mysql",
		logging   : true,
		operatorsAliases: false,
		logging: false
});


module.exports.init = function(){
	sequelize.sync({force: false});
}

module.exports.sequelize = sequelize;