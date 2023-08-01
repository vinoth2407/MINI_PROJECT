package Recipe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

class RecipeManager implements RecipeInterface{
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Recipe?createDatabaseIfNotExist=true";
    
    @Override
    public void addRecipe(Recipe recipe) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL , "root" , "Vinothkanna@10149");
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO instruction (name,instructions) VALUES (?, ?)")) {
            preparedStatement.setString(1, recipe.getName());
            preparedStatement.setString(2, recipe.getInstructions());
            preparedStatement.executeUpdate();
            
//            System.out.println("Recipe '" + recipe.getName() + "' added successfully!\n");
        }
        try (Connection connection = DriverManager.getConnection(DB_URL , "root" , "Vinothkanna@10149");
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO ingredient (name,ingredients) VALUES (?, ?)")) {
               preparedStatement.setString(1, recipe.getName());
               preparedStatement.setString(2, recipe.getIngredients());
               preparedStatement.executeUpdate();
               
               System.out.println("Recipe '" + recipe.getName() + "' added successfully!\n");
           }
    }
    @Override
    public List<Recipe> getAllRecipes() throws SQLException {
        List<Recipe> recipes = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL , "root" , "Vinothkanna@10149");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM instruction i join ingredient j on i.name = j.name");
//            ResultSet resultSet1 = statement.executeQuery("SELECT ingredients FROM ingredient")
             ) {
            while (resultSet.next()) {
                Recipe recipe = new Recipe(
                        resultSet.getString("name"),
                        resultSet.getString("ingredients"),
                        resultSet.getString("instructions")
                );
                recipes.add(recipe);
            }
        }
        return recipes;
        }	
    @Override
    public List<Recipe> searchRecipes(String ingredient) throws SQLException {
        List<Recipe> foundRecipes = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL  , "root" , "Vinothkanna@10149");
             PreparedStatement preparedStatement = connection.prepareStatement(
            		 
            		 "SELECT * FROM instruction i join ingredient j on i.name = j.name WHERE ingredients LIKE ?"
            		 )) {
            preparedStatement.setString(1, "%" + ingredient + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Recipe recipe = new Recipe(
                            resultSet.getString("name"),
                            resultSet.getString("ingredients"),
                            resultSet.getString("instructions")
                    );
                    foundRecipes.add(recipe);
                }
            }
        }
        return foundRecipes;
    }
    @Override
    public List<Recipe> searchRecipes(String name , int id) throws SQLException {
        List<Recipe> foundRecipes = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL  , "root" , "Vinothkanna@10149");
             PreparedStatement preparedStatement = connection.prepareStatement(
            		 
            		 "SELECT * FROM instruction i join ingredient j on i.name = j.name WHERE i.name LIKE ?"
            		 )) {
            preparedStatement.setString(1, "%" + name + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Recipe recipe = new Recipe(
                            resultSet.getString("name"),
                            resultSet.getString("ingredients"),
                            resultSet.getString("instructions")
                    );
                    recipe.setId(resultSet.getInt("id"));
                    
                    foundRecipes.add(recipe);
                }
                
            }
        }
        return foundRecipes;
    }
    @Override
    public void deleteRecipes(String name) throws SQLException {
 
        try (Connection connection = DriverManager.getConnection(DB_URL  , "root" , "Vinothkanna@10149");
             PreparedStatement statement = connection.prepareStatement(
            		 
            		 "DELETE FROM instruction WHERE name = ?"
            		 )) {
            statement.setString(1,name);
            
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Row with name '" + name + "' deleted successfully.");
            } else {
                System.out.println("No rows were deleted for name '" + name + "'.");
            }
            }
     
    }
    @Override
	public void updateingredient(String name , String ingredients) throws SQLException {
		
		  try (Connection connection = DriverManager.getConnection(DB_URL  , "root" , "Vinothkanna@10149");
		             PreparedStatement statement = connection.prepareStatement(
		            		 
		            		 "UPDATE ingredient SET ingredients = ? WHERE name = ?"
		            		 )) {
	            	statement.setString(1,ingredients);
		            statement.setString(2,name);
		            
		            int rowsAffected = statement.executeUpdate();

		            if (rowsAffected > 0) {
		                System.out.println("Row with name '" + name + "' updated successfully.");
		            } else {
		                System.out.println("No rows were update for name '" + name + "'.");
		            }
		            }
		     
	}
    @Override
	public void updateinstruction(String name , String instruction) throws SQLException {
		
		  try (Connection connection = DriverManager.getConnection(DB_URL  , "root" , "Vinothkanna@10149");
		             PreparedStatement statement = connection.prepareStatement(
		            		 
		            		 "UPDATE instruction SET instructions = ? WHERE name = ?"
		            		 )) {
	            	statement.setString(1,instruction);
		            statement.setString(2,name);
		            
		            int rowsAffected = statement.executeUpdate();

		            if (rowsAffected > 0) {
		                System.out.println("Row with name '" + name + "' updated successfully.");
		            } else {
		                System.out.println("No rows were update for name '" + name + "'.");
		            }
		            }
	}
}
