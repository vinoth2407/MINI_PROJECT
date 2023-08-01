package Recipe;
class Recipe extends RecipeEntity{
    private String ingredients;
    private String instructions;
    private int id;
    public Recipe(String name, String ingredients, String instructions) {
    	super(name);
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

   

    public String getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
    
}
