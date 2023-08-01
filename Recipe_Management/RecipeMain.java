package Recipe;
import java.sql.*;
import java.util.List;
import java.util.Scanner;


public class RecipeMain {
	 private static final String DB_URL = "jdbc:mysql://localhost:3306/Recipe?createDatabaseIfNotExist=true";
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            createInstructionTableIfNotExists();
            createIngredientTableIfNotExists();

            Scanner scanner = new Scanner(System.in);
            RecipeManager recipeManager = new RecipeManager();

            while (true) {
            	System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println("Recipe Management System:");
                System.out.println();
                System.out.println("1 - ADD RECIPE");
                System.out.println("2 - VIEW ALL THE RECIPES");
                System.out.println("3 - SEARCH RECIPIES BY THE INGREDIENT");
                System.out.println("4 - SEARCH RECIPE BY THE NAME");
                System.out.println("5 - DELETE RECIPIES BY THE NAME");
                System.out.println("6 - UPDATE RECIPIES BY THE NAME");
                System.out.println("7 - EXIT");
                System.out.println();
                System.out.println("Enter your choice [ 1 | 2 | 3 | 4 | 5 | 6 | 7 ] :");
                System.out.println();
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        addNewRecipe(scanner, recipeManager);
                        break;
                    case 2:
                        viewRecipes(recipeManager);
                        break;
                    case 3:
                        searchRecipes(scanner, recipeManager);
                        break;
                    case 4:
                    	searchRecipesByName(scanner, recipeManager);
                    	break;
                    case 5:
                    	deleteRecipeByName(scanner, recipeManager);
                    	break;
                    case 6:
                    	updateRecipeByName(scanner, recipeManager);
                    	break;
                    case 7:
                        System.out.println("Exiting the Recipe Management System.");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createInstructionTableIfNotExists() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL  , "root" , "Vinothkanna@10149");
             Statement statement = connection.createStatement()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS instruction (" +
                    "id INTEGER PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(100) NOT NULL," +
                    "instructions VARCHAR(1000) NOT NULL)";
            statement.executeUpdate(createTableSQL);
        }
    }

    
    
    private static void createIngredientTableIfNotExists() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL  , "root" , "Vinothkanna@10149");
             Statement statement = connection.createStatement()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS ingredient (" +
                    "id INTEGER PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(100) NOT NULL," +
                    "ingredients VARCHAR(100) NOT NULL)";
            statement.executeUpdate(createTableSQL);
        }
    }
    
    
    private static void addNewRecipe(Scanner scanner, RecipeManager recipeManager) throws SQLException {
        System.out.print("Enter the Name of the recipe: ");
        String name = scanner.nextLine();

        System.out.print("Enter the Ingredients (comma-separated): ");
        String ingredients = scanner.nextLine();

        System.out.print("Enter the Instructions (separated by lines, end with 'done'): ");
        StringBuilder instructionsBuilder = new StringBuilder();
        String line;
        while (!(line = scanner.nextLine()).equalsIgnoreCase("done")) {
            instructionsBuilder.append(line).append("\n");
        }
        String instructions = instructionsBuilder.toString();

        Recipe recipe = new Recipe(name, ingredients, instructions);
        recipeManager.addRecipe(recipe);
    }

    private static void viewRecipes(RecipeManager recipeManager) throws SQLException {
        List<Recipe> recipes = recipeManager.getAllRecipes();
        if (recipes.isEmpty()) {
            System.out.println("No recipes found. Please add some recipes first.\n");
        } else {
            System.out.println("Existing Recipes:");
            System.out.println();
            for (Recipe recipe : recipes) {
                System.out.println("Name:            " + recipe.getName());
                System.out.println();
                System.out.println();
                System.out.println("Ingredients:     " + recipe.getIngredients());
                System.out.println();
                System.out.println();
                System.out.println("Instructions:");
                System.out.println();
                System.out.println();
                System.out.println(recipe.getInstructions());
            }
        }
    }
    
    private static void searchRecipes(Scanner scanner, RecipeManager recipeManager) throws SQLException {
        System.out.print("Enter the ingredient to search for: ");
        System.out.println();
        String ingredient = scanner.nextLine();

        List<Recipe> foundRecipes = recipeManager.searchRecipes(ingredient);
        if (foundRecipes.isEmpty()) {
            System.out.println("No recipes found containing '" + ingredient + "'.\n");
            System.out.println();
            System.out.println();
        } else {
            System.out.println("Recipes containing : '" + ingredient + "' -->");
            System.out.println();
            for (Recipe recipe : foundRecipes) {
                System.out.println("Name: " + recipe.getName());
                System.out.println();
                System.out.println("Ingredients: " + recipe.getIngredients());
                System.out.println();
                System.out.println("Instructions:" );
                System.out.println();
                System.out.println(recipe.getInstructions());
            }
        }
    }
    private static void searchRecipesByName(Scanner scanner, RecipeManager recipeManager) throws SQLException {
        System.out.print("Enter the recipe Name  to search for: ");
        System.out.println();
        System.out.println();
        String recipe_name = scanner.nextLine();

        List<Recipe> foundRecipes = recipeManager.searchRecipes(recipe_name , 1);
        if (foundRecipes.isEmpty()) {
            System.out.println("No recipes found containing '" + recipe_name + "'.\n");
        } else {
        	System.out.println();
        	System.out.println();
            System.out.println("Recipes containing '" + recipe_name + "':");
            System.out.println();
           
            System.out.println();
//            System.out.println
//            System.out.println(arr.length);
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("|  id   |      name         |                                ingredients                                |               instruction                        ");
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------");

            for (Recipe recipe : foundRecipes) {
            	int id = recipe.getId();
            	System.out.print("|  "+id+"    |   ");
            	String[] arr = recipe.getInstructions().split("\n");
            	String name = recipe.getName();
            	String instruction = recipe.getIngredients();
            	int len = name.length();
            	System.out.print(name);

            	for(int i=1; i<17-len; i++) {
            		System.out.print(" ");
            	}
            	System.out.print("|   ");
            	System.out.print(instruction);

            	len = instruction.length();
            	for(int i=1; i<58+15-len; i++) {
            		System.out.print(" ");
            	}
            	System.out.print("|   ");
            	System.out.println(arr[0]);
            	for(int i=1; i<arr.length; i++) {
            		System.out.println("|       |                   |                                                                           |  " + arr[i]);
            	}
                
//                System.out.println("Ingredients: " + recipe.getIngredients());
            	id++;
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------");
            }
        }
        
        
}
    
    public static void deleteRecipeByName(Scanner scanner , RecipeManager recipeManager) throws SQLException {
    	System.out.print("Enter the recipe name to delete for :  ");
    	String name = scanner.nextLine();
    	recipeManager.deleteRecipes(name);
    	
    }
    
    public static void updateRecipeByName(Scanner scanner , RecipeManager recipeManager) throws SQLException {
    	boolean flag = true;
    while(flag) {
    	System.out.println(" -----------------CHOICE----------------");
        System.out.println("|     1. Edit ingredient                |");
        System.out.println("|     2. Edit instruction               |");
        System.out.println("|     3. Exit                           |");
    	System.out.println(" ---------------------------------------");
    	System.out.print("Enter your choice: ");
    	
    	int choice = Integer.parseInt(scanner.nextLine());
    	
    	 switch (choice) {
         case 1:
        	 System.out.print("Enter the recipe name to update for :  ");
        	 String name = scanner.nextLine();
        	 System.out.print("Enter the ingredients (comma-separated): ");
             String ingredients = scanner.nextLine();
             recipeManager.updateingredient(name , ingredients);
             break;
         case 2:
        	 System.out.print("Enter the recipe name to update for :  ");
        	 String name1 = scanner.nextLine();

        	 System.out.print("Enter the instructions (separated by lines, end with 'done'): ");
             StringBuilder instructionsBuilder = new StringBuilder();
             String line;
             while (!(line = scanner.nextLine()).equalsIgnoreCase("done")) {
                 instructionsBuilder.append(line).append("\n");
             }
             String instructions = instructionsBuilder.toString();
        	 recipeManager.updateinstruction(name1 , instructions);
             break;
         case 3:
        	 System.out.println("Exiting from the updataion process");
        	 flag = false;
        	 break;
         default:
        	 System.out.println("wrong choice. please try again");
    	 }
    	
    }
 }
}