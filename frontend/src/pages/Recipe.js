import React from 'react';
import '../style/Recipe.css';
import '@fortawesome/fontawesome-free/css/all.min.css';
import RecipeService from '../services/RecipeService';
import RatingService from '../services/RatingService';
import PictureService from '../services/PictureService';
import IngredientService from "../services/IngredientService";
import RecipeCard from "../components/RecipeCard";
import StepService from "../services/StepService";

class Recipe extends React.Component {
 constructor(props) {
        super(props);
        this.recipeCategoryName= new Map();
        this.recipePhoto = new Map();
        this.state = {
            recipe : Object(),
            pic : "",
            averageRating : Object(),
            calories : Object(),
            vitamins : Object(),
            fat : Object(),
            proteins : Object(),
            ratings : [],
            steps : [],
            ingredients: []
    };
 }

  componentDidMount() {
    RecipeService.getRecipeByID("04b03880-5b56-4464-a466-a150958c32f7").then((res) => {
        this.setState({recipe : res.data});
    });
    RatingService.getAverageRatingForRecipe("04b03880-5b56-4464-a466-a150958c32f7").then((res) => {
        this.setState({averageRating : res.data});
    });
    IngredientService.getTotalCalories("04b03880-5b56-4464-a466-a150958c32f7").then((res) => {
        this.setState({calories : res.data});
    });
    IngredientService.getTotalVitamins("04b03880-5b56-4464-a466-a150958c32f7").then((res) => {
        this.setState({vitamins : res.data});
    });
    IngredientService.getTotalFat("04b03880-5b56-4464-a466-a150958c32f7").then((res) => {
        this.setState({fat : res.data});
    });
    IngredientService.getTotalProteins("04b03880-5b56-4464-a466-a150958c32f7").then((res) => {
        this.setState({proteins : res.data});
    });
    IngredientService.getIngredientInfoForRecipe("04b03880-5b56-4464-a466-a150958c32f7").then((res) => {
        this.setState({ingredients : res.data});
    });
    StepService.getRecipeSteps("04b03880-5b56-4464-a466-a150958c32f7").then((res) => {
        this.setState({steps : res.data});
    });
    RatingService.getRatingForRecipe("04b03880-5b56-4464-a466-a150958c32f7").then((res) => {
        this.setState({ratings : res.data});
    });

  }

  render() {
      // PictureService.getRecipePictureById(this.state.recipe.recipePicture).then((res) => {
      //     this.setState({pic : res.data})
      // });
        return (
            <div>
               <a href='./Home'><h2 style={{marginLeft:"5%"}} className='h2-style'>FoodHaven</h2></a>
               <div style={{marginLeft:"5%"}}>
                    <div className="column1-recipe" >
                        <h1>{this.state.recipe.name}</h1>
                        <h4>Average rating: <i className='fas fa-star recipe-i'></i> {this.state.averageRating.averageRating}</h4>
                        <p>{this.state.pic.picByte}</p>
                        <img className='recipe-img' src={'data:image/jpeg;base64,' + "R0lGODlhAQABAIAAAAUEBAAAACwAAAAAAQABAAACAkQBADs="} alt='' />
                        <p>{this.state.recipe.description}</p>
                        <div className="numberCircle">calories: <b>{this.state.calories.totalCalories}</b></div>
                        <div className="numberCircle">vitamins: <b>{this.state.vitamins.totalVitamins}</b></div>
                        <div className="numberCircle">fat: <b>{this.state.fat.totalFat}</b></div>
                        <div className="numberCircle">proteins: <b>{this.state.proteins.totalProteins}</b></div>
                    </div>
                        <div className="column2-recipe"> 
                        <h1>Ingredients</h1>
                            <ul>
                                {this.state.ingredients.map(ingredient => {
                                    return (
                                        <li style={{fontSize:"20px", padding:"10px", borderBottom: "1px solid #444444",
                                            width:"70%"}} key={ingredient[0]}>
                                            {ingredient[2]} {ingredient[1]} {ingredient[0]}</li>
                                    );
                                })}
                            </ul>
                        <h1>Instructions</h1>
                            <ol>
                                {this.state.steps.map(step => {
                                    return (
                                        <li style={{fontSize:"20px", padding:"10px", borderBottom: "1px solid #444444",
                                            width:"70%"}} key={step.id}>
                                            {step.description}</li>
                                    );
                                })}
                            </ol>
                    </div>
                    <div >
                        <h1>Reviews:</h1>
                        <ul>
                            {this.state.ratings.map(rating => {
                                return (
                                    <li style={{fontSize:"20px", padding:"10px", borderBottom: "1px solid #444444",
                                        width:"70%"}} key={rating.id}>
                                        {rating.comment}</li>
                                );
                            })}
                        </ul>
                        <button style={{fontSize:"30px"}} className="add-button" type='button' ><i className='fas fa-user-circle'></i></button>     
                        <input style={{fontSize:"20px", border:"none", padding:"10px", width:"40%"}} type="text" placeholder='Write your review or comment here'/>   
                    </div>
               </div>
            </div>
        );
    }
}
export default Recipe;