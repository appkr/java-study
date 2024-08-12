package dev.appkr.dataframe

import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.count
import org.jetbrains.kotlinx.dataframe.api.filter
import org.jetbrains.kotlinx.dataframe.api.getColumns
import org.jetbrains.kotlinx.dataframe.api.print
import org.jetbrains.kotlinx.dataframe.api.sortBy
import org.jetbrains.kotlinx.dataframe.io.read
import org.junit.jupiter.api.Test

class GettingStarted {
    @Test
    fun jetbrainsRepositories() {
        val df = DataFrame.read("https://raw.githubusercontent.com/Kotlin/dataframe/master/data/jetbrains_repositories.csv")
        df["full_name"][0]
        df.filter { "stargazers_count"<Int>() > 50 }.print()
    }

    @Test
    fun movies() {
        val df = DataFrame.read("https://raw.githubusercontent.com/Kotlin/dataframe/master/data/movies.csv")
        df.filter { "genres"<String>().contains("Comedy") }.print()
    }
}
