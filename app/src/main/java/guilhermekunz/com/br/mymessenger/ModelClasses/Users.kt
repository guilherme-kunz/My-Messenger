package guilhermekunz.com.br.mymessenger.ModelClasses

class Users(
    val uid: String,
    val username: String,
    val profile: String,
    val cover: String,
    val status: String,
    val search: String,
    val facebook: String,
    val instagram: String
) {

    constructor(): this(
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        ""
    )

}

