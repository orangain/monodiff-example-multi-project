/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package account

import greeter.Greeter

class AccountApp {
    val greeting: String
        get() {
            return "Hello world."
        }
}

fun main(args: Array<String>) {
    println("account-service: ${Greeter().getGreeting()}")
}
