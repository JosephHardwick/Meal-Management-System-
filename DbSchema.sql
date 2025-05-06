create table INGREDIENT
(
    INGREDIENTID NUMBER       not null
        primary key,
    NAME         VARCHAR2(50) not null,
    PROTEIN      VARCHAR2(50),
    SUGAR        VARCHAR2(50),
    SODIUM       VARCHAR2(50),
    FAT          VARCHAR2(50),
    CALORIES     VARCHAR2(50),
    HAS          NUMBER(1) default 0
)
/

--auto increment ID
create or replace trigger INGREDIENT_TRIGGER
    before insert
    on INGREDIENT
    for each row
BEGIN
    IF :NEW.ingredientID IS NULL THEN
        :NEW.ingredientID := ingredient_seq.NEXTVAL;
    END IF;
END;
/

create table MEAL
(
    MEALID      NUMBER       not null
        primary key,
    NAME        VARCHAR2(50) not null,
    CATEGORY    VARCHAR2(50) not null,
    INSTRUCTION VARCHAR2(500)
)
/
--auto increment ID
create or replace trigger MEAL_TRIGGER
    before insert
    on HARDWICKJ545.MEAL
    for each row
BEGIN
    IF :NEW.mealID IS NULL THEN
        :NEW.mealID := meal_seq.NEXTVAL;
    END IF;
END;
/

create table MEALINGREDIENT
(
    MEAL_ID       NUMBER not null
        references HARDWICKJ545.MEAL
            on delete cascade,
    INGREDIENT_ID NUMBER not null
        references HARDWICKJ545.INGREDIENT
            on delete cascade,
    primary key (MEAL_ID, INGREDIENT_ID)
)
/

--data insertion

INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Rice', '2.7g', '0.1g', '1mg', '0.3g', '130', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Egg', '6g', '0.6g', '62mg', '5g', '68', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Bread', '4g', '1.4g', '170mg', '1g', '79', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Cheerios', '3g', '1g', '140mg', '2g', '100', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Milk', '8g', '12g', '98mg', '5g', '103', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Celery', '0.7g', '1.4g', '32mg', '0.2g', '16', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Peanut Butter', '8g', '3g', '150mg', '16g', '190', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Butter', '0.1g', '0g', '2mg', '11g', '100', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Pineapple', '0.5g', '10g', '1mg', '0.1g', '50', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Apple', '0.3g', '19g', '1mg', '0.2g', '95', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Banana', '1.3g', '14g', '1mg', '0.3g', '105', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Raspberry', '1.2g', '4.4g', '1mg', '0.6g', '52', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Blueberry', '0.7g', '14g', '1mg', '0.3g', '57', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Chicken Breast', '31g', '0g', '74mg', '3.6g', '165', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('T-bone Steak', '19g', '0g', '71mg', '17g', '294', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Ground Beef', '26g', '0g', '75mg', '20g', '290', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Pasta Sauce', '2g', '6g', '480mg', '1g', '70', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Angel Hair Pasta', '7g', '1g', '0mg', '1g', '200', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Alfredo Sauce', '2g', '1g', '330mg', '10g', '100', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Onion', '1g', '4g', '4mg', '0.1g', '40', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Garlic', '0.6g', '1g', '1mg', '0g', '5', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Mustard', '0.3g', '0.9g', '57mg', '0.2g', '10', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Mayonnaise', '0.2g', '0g', '105mg', '10g', '94', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Turkey Slices', '8g', '1g', '500mg', '1g', '50', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Lettuce', '0.5g', '0.8g', '5mg', '0.1g', '5', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Chicken Nugget', '3g', '0g', '200mg', '6g', '50', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Hashbrowns', '2g', '0g', '300mg', '8g', '120', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Pizza Sauce', '1g', '3g', '200mg', '1g', '30', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Cinnamon', '0.1g', '0.1g', '0mg', '0g', '6', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Dough', '3g', '1g', '200mg', '2g', '120', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Mozzerella Cheese', '7g', '1g', '200mg', '5g', '85', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Cheddar Cheese', '7g', '0g', '180mg', '9g', '115', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Provolone Cheese', '7g', '0g', '200mg', '8g', '100', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Pepperoni', '5g', '0g', '500mg', '13g', '140', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Sausage', '6g', '0g', '400mg', '15g', '180', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Macaroni', '7g', '1g', '0mg', '1g', '200', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Carrot', '0.9g', '4.7g', '69mg', '0.2g', '25', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Potato', '2g', '1g', '6mg', '0.1g', '77', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Sour Cream', '1g', '1g', '20mg', '5g', '52', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Bacon', '3g', '0g', '194mg', '3g', '43', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Cane Sugar', '0g', '100g', '0mg', '0g', '383', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Flour', '3g', '0g', '0mg', '0g', '100', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Chocolate chips', '1g', '8g', '2mg', '5g', '70', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Blue Cheese', '6g', '0g', '400mg', '8g', '100', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Tobasco Sauce', '0g', '0g', '35mg', '0g', '0', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Tomato', '1g', '3g', '5mg', '0.2g', '18', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Squash', '1g', '2g', '1mg', '0.1g', '20', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('BBQ Sauce', '0g', '4g', '200mg', '0g', '30', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Ranch Dressing', '0g', '1g', '200mg', '0g', '30', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Peanuts', '7g', '4g', '5mg', '14g', '170', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Raisin', '0.5g', '59g', '2mg', '0.1g', '299', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Croutons', '0.5g', '0.2g', '200mg', '1g', '30', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Parmesan Cheese', '10g', '0g', '424mg', '7g', '111', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Caesar Dressing', '0.5g', '0.5g', '160mg', '15g', '80', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Cucumber', '0.7g', '1.7g', '2mg', '0.1g', '8', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Feta Cheese', '4g', '0g', '316mg', '6g', '75', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Olive Oil', '0g', '0g', '0mg', '14g', '120', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Whipped Cream', '0.3g', '1g', '5mg', '5g', '45', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Salt', '0g', '0g', '393mg', '0g', '0', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Pepper', '0g', '0g', '0mg', '0g', '0', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Pork Chops', '26g', '0g', '60mg', '10g', '250', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Asparagus', '2.2g', '1.9g', '2mg', '0.2g', '20', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Salmon', '25g', '0g', '59mg', '13g', '206', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Lemon', '0.3g', '1.5g', '1mg', '0g', '5', 1);
INSERT INTO Ingredient (name, protein, sugar, sodium, fat, calories, has) VALUES ('Shrimp', '24g', '0g', '111mg', '1g', '99', 1);



INSERT INTO MEAL (name, category, instruction) VALUES ('Garlic Bread', 'Appetizer', 'Spread Butter and Garlic on Bread. Toast until golden.');
INSERT INTO MEAL (name, category, instruction) VALUES ('Cheese Platter', 'Appetizer', 'Arrange Cheddar Cheese, Blue Cheese, and Provolone Cheese on a platter. Serve with crackers.');

INSERT INTO MEAL (name, category, instruction) VALUES ('Tomato Soup', 'Soup', 'Simmer Tomato, Garlic, and Onion in water. Blend until smooth. Serve hot.');
INSERT INTO MEAL (name, category, instruction) VALUES ('Chicken Soup', 'Soup', 'Boil Chicken Breast, Carrot, and Celery in chicken broth.');

INSERT INTO MEAL (name, category, instruction) VALUES ('Caesar Salad', 'Salad', 'Mix Lettuce, Parmesan Cheese, Croutons, and Caesar dressing.');
INSERT INTO MEAL (name, category, instruction) VALUES ('Greek Salad', 'Salad', 'Combine Tomato, Cucumber, Onion, and Feta Cheese. Drizzle with olive oil.');

INSERT INTO MEAL (name, category, instruction) VALUES ('Chocolate Chip Cookies', 'Dessert', 'Mix Flour, Cane Sugar, Butter, and Chocolate Chips. Bake until golden.');
INSERT INTO MEAL (name, category, instruction) VALUES ('Fruit Tart', 'Dessert', 'Fill a baked crust with Blueberry, Raspberry, and Pineapple. Top with whipped cream.');

INSERT INTO MEAL (name, category, instruction) VALUES ('Banana Bread', 'Bread', 'Mix Flour, Banana, and Cane Sugar. Bake until cooked through.');
INSERT INTO MEAL (name, category, instruction) VALUES ('Dinner Rolls', 'Bread', 'Mix Dough and let rise. Bake until golden.');

INSERT INTO MEAL (name, category, instruction) VALUES ('Beef Stroganoff', 'Main Dish - Beef', 'Cook Ground Beef with Onion and Garlic. Mix with sour cream.');
INSERT INTO MEAL (name, category, instruction) VALUES ('Grilled T-Bone Steak', 'Main Dish - Beef', 'Season T-Bone Steak with salt and pepper. Grill until desired doneness.');

INSERT INTO MEAL (name, category, instruction) VALUES ('Pork Chops', 'Main Dish - Pork', 'Season Pork Chops with salt and pepper. Pan-fry until golden.');
INSERT INTO MEAL (name, category, instruction) VALUES ('Bacon-Wrapped Asparagus', 'Main Dish - Pork', 'Wrap Asparagus with Bacon. Bake until crispy.');

INSERT INTO MEAL (name, category, instruction) VALUES ('Grilled Salmon', 'Main Dish - Seafood', 'Season Salmon with salt and lemon. Grill until flaky.');
INSERT INTO MEAL (name, category, instruction) VALUES ('Shrimp Alfredo', 'Main Dish - Seafood', 'Cook Shrimp and mix with Alfredo Sauce. Serve over Angel Hair Pasta.');

INSERT INTO MEAL (name, category, instruction) VALUES ('Trail Mix', 'Snack', 'Combine Chocolate Chips, Raisins, and Peanuts.');
INSERT INTO MEAL (name, category, instruction) VALUES ('Veggie Sticks', 'Snack', 'Slice Carrot, Celery, and Squash. Serve with Ranch dressing.');

INSERT INTO MEAL (name, category, instruction) VALUES ('Pancakes', 'Breakfast', 'Mix Flour, Cane Sugar, and Milk. Cook on a griddle until golden.');
INSERT INTO MEAL (name, category, instruction) VALUES ('Omelette', 'Breakfast', 'Whisk Eggs and pour into a hot skillet. Add Cheddar Cheese. Cook until set.');
INSERT INTO MEAL (name, category, instruction) VALUES ('Cereal', 'Breakfast', 'Pour Milk over Cheerios. Serve with sliced Banana.');
INSERT INTO MEAL (name, category, instruction) VALUES ('French Toast', 'Breakfast', 'Dip Bread in Egg and Milk mixture. Cook on a griddle until golden.');

INSERT INTO MEAL (name, category, instruction) VALUES ('Turkey Sandwich', 'Lunch', 'Layer Turkey Slices, Lettuce, and Tomato on Bread. Spread with Mayonnaise and Mustard.');
INSERT INTO MEAL (name, category, instruction) VALUES ('Chicken Salad', 'Lunch', 'Mix Chicken Breast, Celery, and Mayonnaise. Serve on a bed of Lettuce.');


-- Chicken and Broccoli
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (1, 1);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (1, 2);

-- Broccoli and Chicken
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (2, 1);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (2, 2);

-- Chicken Salad
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (27, 1);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (27, 76);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (1, 93);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (1, 95);

-- Garlic Bread
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (28, 78);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (28, 91);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (28, 73);


-- Cheese Platter
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (29, 114);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (29, 102);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (29, 103);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (29, 136);

-- Tomato Soup
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (30, 116);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (30, 90);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (30, 91);


-- Chicken Soup
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (31, 1);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (31, 107);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (31, 76);


-- Caesar Salad
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (32, 124);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (32, 95);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (32, 123);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (32, 122);



-- Greek Salad
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (33, 116);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (33, 127);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (33, 126);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (33, 125);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (33, 90);


-- Chocolate Chip Cookies
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (34, 111);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (34, 112);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (34, 113);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (34, 78);



-- Fruit Tart
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (35, 82);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (35, 83);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (35, 79);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (35, 128);



-- Banana Bread
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (36, 81);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (36, 112);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (36, 111);



-- Dinner Rolls
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (37, 100);



-- Beef Stroganoff
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (38, 86);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (38, 109);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (38, 90);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (38, 91);



-- Grilled T-Bone Steak
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (39, 85);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (39, 129);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (39, 130);



-- Pork Chops
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (40, 131);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (40, 129);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (40, 130);



-- Bacon-Wrapped Asparagus
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (41, 110);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (41, 132);



-- Grilled Salmon
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (42, 129);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (42, 133);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (42, 134);



-- Shrimp Alfredo
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (43, 135);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (43, 75);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (43, 89);


-- Trail Mix
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (44, 113);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (44, 120);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (44, 121);



-- Veggie Sticks
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (45, 107);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (45, 76);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (45, 117);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (45, 119);



-- Pancakes
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (46, 75);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (46, 111);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (46, 112);



-- Omelette
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (47, 72);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (47, 102);



-- Cereal
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (48, 75);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (48, 74);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (48, 81);


-- French Toast
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (49, 73);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (49, 72);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (49, 75);



-- Turkey Sandwich
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (50, 94);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (50, 92);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (50, 93);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (50, 73);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (50, 95);
INSERT INTO MealIngredient (meal_id, ingredient_id) VALUES (50, 116);










