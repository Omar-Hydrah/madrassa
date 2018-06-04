var Teacher = sequelize.define("teacher", {
		teacher_id : {
			type : Sequelize.INTEGER(11),
			primaryKey : true,
			allowNull : false,
			autoIncrement : true
		},

		user_id :{
			type: Sequelize.INTEGER(11),
			allowNull: false,
			references: {
				model :"users",
				key: "user_id" 
			}
		},

		name : {
			type: Sequelize.STRING(100),
			allowNull: false,
		}, 
		email : {
			type: Sequelize.STRING(100),
			allowNull : true
		},

		created_at : {
			type: Sequelize.DATE,
			allowNull : false,
			defaultValue : Sequelize.NOW			
		}
	}, {
		tableName  : "teachers",
		timestamps :false,
	});