package com.betterandroid.restaurantscorner

enum class RestaurantType(val text: String, val drawableId: Int, val textColorId: Int) {
    TAKE_AWAY("Take away", R.drawable.take_away, R.color.orange),
    EAT_IN("Eat in", R.drawable.eat_in, R.color.brown),
    DRIVE_THROUGH("Drive T", R.drawable.drive_through, R.color.colorAccent);

    companion object {
        fun getType(type: String): RestaurantType {
            return when (type) {
                "TAKE_AWAY" -> TAKE_AWAY
                "EAT_IN" -> EAT_IN
                "DRIVE_THROUGH" -> DRIVE_THROUGH
                else -> throw IllegalArgumentException("type not found")
            }
        }
    }

}