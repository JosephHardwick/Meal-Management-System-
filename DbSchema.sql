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

create table HARDWICKJ545.MEAL
(
    MEALID      NUMBER       not null
        primary key,
    NAME        VARCHAR2(50) not null,
    CATEGORY    VARCHAR2(50) not null,
    INSTRUCTION VARCHAR2(500)
)
/
--auto increment ID
create or replace trigger HARDWICKJ545.MEAL_TRIGGER
    before insert
    on HARDWICKJ545.MEAL
    for each row
BEGIN
    IF :NEW.mealID IS NULL THEN
        :NEW.mealID := meal_seq.NEXTVAL;
    END IF;
END;
/

create table HARDWICKJ545.MEALINGREDIENT
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





