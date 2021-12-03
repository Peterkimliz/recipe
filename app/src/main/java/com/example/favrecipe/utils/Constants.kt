package com.example.favrecipe.utils

class Constants {
    companion object {
        val CAMERA_CODE = 1
        val GALLERY_CODE = 2
        val IMAGE_DIRECTORY = "FavRecipeImages"

        val DISH_TYPE: String = "DishType"
        val DISH_CATEGORY: String = "DishCategory"
        val DISH_COOKING_TIME: String = "DishCookingTime"

        val IMAGE_SOURCE_LOCAL="Local"
        val IMAGE_SOURCE_ONLINE="Online"


        /**************** This function will return the Dish Type List items.**********************/
        fun dishTypes(): ArrayList<String> {
            val list = ArrayList<String>()
            list.add("breakfast")
            list.add("lunch")
            list.add("dinner")
            list.add("snacks")
            list.add("salad")
            list.add("side dish")
            list.add("dessert")
            list.add("other")
            return list
        }

        /*************This function will return the Dish Category list items.**********************/
        fun dishCategories(): ArrayList<String> {
            val list = ArrayList<String>()
            list.add("Pizza")
            list.add("BBQ")
            list.add("Bakery")
            list.add("Burger")
            list.add("Cafe")
            list.add("Chicken")
            list.add("Dessert")
            list.add("Drinks")
            list.add("Hot Dogs")
            list.add("Juices")
            list.add("Sandwich")
            list.add("Tea & Coffee")
            list.add("Wraps")
            list.add("Other")
            return list
        }


        /**This function will return the Dish Cooking Time list items. ****************************/
        fun dishCookTime(): ArrayList<String> {
            val list = ArrayList<String>()
            list.add("10")
            list.add("15")
            list.add("20")
            list.add("30")
            list.add("45")
            list.add("50")
            list.add("60")
            list.add("90")
            list.add("120")
            list.add("150")
            list.add("180")
            return list
        }
    }
    }