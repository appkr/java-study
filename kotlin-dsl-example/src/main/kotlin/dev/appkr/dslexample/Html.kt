package dev.appkr.dslexample

fun html(init: Html.() -> Unit): Html {
    val html = Html()
    // Html 클래스에 init()이란 확장 함수를 추가한다
    // 함수의 본문은 파라미터로 받은 Html.() -> Unit 이다
    html.init()
    return html
}

class Html {
    private val elements = mutableListOf<HtmlElement>()

    fun head(init: Head.() -> Unit) {
        val head = Head()
        // Head 클래스에 init()이란 확장 함수를 추가한다
        // 함수의 본문은 파라미터로 받은 Head.() -> Unit 이다
        head.init()
        elements.add(head)
    }

    fun body(init: Body.() -> Unit) {
        val body = Body()
        // Body 클래스에 init()이란 확장 함수를 추가한다
        // 함수의 본문은 파라미터로 받은 Body.() -> Unit 이다
        body.init()
        elements.add(body)
    }

    fun render(): String {
        return "<html>\n${elements.joinToString(separator = "\n") { it.render() }}\n</html>"
    }
}

abstract class HtmlElement {
    protected val elements = mutableListOf<HtmlElement>()
    abstract fun render(): String
}

class Head : HtmlElement() {
    fun title(init: Title.() -> Unit) {
        val title = Title()
        // Title 클래스에 init()이란 확장 함수를 추가한다
        // 함수의 본문은 파라미터로 받은 Title.() -> Unit 이다
        title.init()
        elements.add(title)
    }

    override fun render(): String {
        return "<head>\n${elements.joinToString(separator = "\n") { it.render() }}\n</head>"
    }
}

class Body : HtmlElement() {
    fun h1(init: H1.() -> Unit) {
        val h1 = H1()
        // H1 클래스에 init()이란 확장 함수를 추가한다
        // 함수의 본문은 파라미터로 받은 H1.() -> Unit 이다
        h1.init()
        elements.add(h1)
    }

    fun p(init: P.() -> Unit) {
        val p = P()
        // P 클래스에 init()이란 확장 함수를 추가한다
        // 함수의 본문은 파라미터로 받은 P.() -> Unit 이다
        p.init()
        elements.add(p)
    }

    override fun render(): String {
        return "<body>\n${elements.joinToString(separator = "\n") { it.render() }}\n</body>"
    }
}

class Title : HtmlElement() {
    var text = ""

    override fun render(): String {
        return "<title>$text</title>"
    }
}

class H1 : HtmlElement() {
    var text = ""

    override fun render(): String {
        return "<h1>$text</h1>"
    }
}

class P : HtmlElement() {
    var text = ""

    override fun render(): String {
        return "<p>$text</p>"
    }
}
