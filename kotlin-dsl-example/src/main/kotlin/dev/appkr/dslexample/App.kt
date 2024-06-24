package dev.appkr.dslexample

fun main() {
    val document = html {
        // Html 클래스의 확장 함수 본문 안이다
        // 따라서, this.head() 함수를 호출할 수 있다
        head {
            // Head 클래스의 확장 함수 본문 안이다
            // 따라서, this.title() 함수를 호출할 수 있다
            title {
                // Title 클래스의 확장 함수 본문 안이다
                // 따라서, this.text 속성에 접근할 수 있다
                text = "My Page"
            }
        }
        body {
            // Body 클래스의 확장 함수 본문 안이다
            // 따라서, this.h1() 함수를 호출할 수 있다
            h1 {
                // H1 클래스의 확장 함수 본문 안이다
                // 따라서, this.text 속성에 접근할 수 있다
                text = "Welcome to My Page"
            }
            p {
                // P 클래스의 확장 함수 본문 안이다
                // 따라서, this.text 속성에 접근할 수 있다
                text = "This is an example of a DSL built with Kotlin."
            }
        }
    }

    println(document.render())
    /*
    <html>
    <head>
    <title>My Page</title>
    </head>
    <body>
    <h1>Welcome to My Page</h1>
    <p>This is an example of a DSL built with Kotlin.</p>
    </body>
    </html>
    */
}
