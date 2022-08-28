package com.example.punkapp.backend

enum class ParameterType(val value: String) {
    //(number) Returns all beers with ABV greater than the supplied number
    ABVGreater("abv_gt"),

    //(number) Returns all beers with ABV less than the supplied number
    ABVLess("abv_lt"),

    //(number) Returns all beers with IBU greater than the supplied number
    IBULGreater("ibu_gt"),

    //(number) Returns all beers with IBU less than the supplied number
    IBULess("ibu_lt"),

    //(number) Returns all beers with EBC greater than the supplied number
    EBCGreater("ebc_gt"),

    //(number) Returns all beers with EBC less than the supplied number
    EBCLess("ebc_lt"),

    //(string) Returns all beers matching the supplied name (this will match partial strings as well so e.g punk will return Punk IPA), if you need to add spaces just add an underscore (_).
    BeerName("beer_name"),

    //(string) Returns all beers matching the supplied yeast name, this performs a fuzzy match, if you need to add spaces just add an underscore (_).
    Yeast("beer_name"),

    //(string) Returns all beers brewed before this date, the date format is mm-yyyy e.g 10-2011
    BrewedBefore("brewed_before"),

    //(string) Returns all beers brewed after this date, the date format is mm-yyyy e.g 10-2011
    BrewedAfter("brewed_after"),

    //(string) Returns all beers matching the supplied hops name, this performs a fuzzy match, if you need to add spaces just add an underscore (_).
    Hops("hops"),

    //(string) Returns all beers matching the supplied malt name, this performs a fuzzy match, if you need to add spaces just add an underscore (_).
    Malt("malt"),

    //(string) Returns all beers matching the supplied food string, this performs a fuzzy match, if you need to add spaces just add an underscore (_).
    Food("food"),

    //(string (id|id|...)) Returns all beers matching the supplied ID's. You can pass in multiple ID's by separating them with a | symbol.
    Ids("ids"),

    //(number) Requests that return multiple items will be limited to 25 results by default.
    PageSize("per_page"),

    //You can access other pages using the ?page paramater, you can also increase the amount of beers returned in each request by changing the ?per_page paramater.
    PageNumber("page")
}