package com.project.restaurant.utils;

public class Item {

    public static final String SAVE = "/item/save";

    public static final String FIND = "/item";

    public static final String FIND_ITEMS_OF_SELLER = "/item/seller/{id}";

    public static final String DELETE = "/item/delete/{id}";

    public static final String UPDATE = "/item/update/{id}";

    public static final String ADD_INGREDIENTS = "/item/ingredients/{id}";

    public static final String DELETE_INGREDIENT = "/item/{id}/ingredients/{ingredientId}";

    public static final String ADD_INGREDIENT = "/item/add-ingredient";

    public static final String GET_ITEMS_OF_SELLER_$LOGGED = "/item/items";

    public static final String ITEM_FILTER = "/item/filter";

}
