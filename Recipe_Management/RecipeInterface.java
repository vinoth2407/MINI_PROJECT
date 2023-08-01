package Recipe;

import java.sql.SQLException;
import java.util.List;

public interface RecipeInterface {
	
	public void addRecipe(Recipe recipe) throws SQLException;
	public List<Recipe> getAllRecipes() throws SQLException;
	public List<Recipe> searchRecipes(String ingredient) throws SQLException;
	public List<Recipe> searchRecipes(String name , int id) throws SQLException;
	public void deleteRecipes(String name) throws SQLException;
	public void updateingredient(String name , String ingredients) throws SQLException;
	public void updateinstruction(String name , String instruction) throws SQLException;

}
